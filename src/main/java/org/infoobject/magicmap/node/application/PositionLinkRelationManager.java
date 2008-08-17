package org.infoobject.magicmap.node.application;

import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.node.Node;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.infoobject.core.infoobject.domain.InformationObjectModel;
import org.infoobject.core.infoobject.domain.ObjectLink;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObject;
import org.infoobject.core.infoobject.event.InformationObjectListenerAdapter;
import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import org.infoobject.core.relation.domain.PositionRelation;
import org.infoobject.core.relation.domain.PositionRelationEdge;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;
import org.infoobject.magicmap.node.model.InformationObjectNodeModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Cares for infoobject to pyhsical nodes reltions.
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 13:30:08
 */
public class PositionLinkRelationManager extends InformationObjectListenerAdapter implements NodeModelListener {

    private final InformationObjectNodeModel objectNodeModel;
    private final InformationObjectNodeGraph informationNodeGraph;
    private final InformationObjectModel informationObjectModel;
    private final Set<String> ignordTypes = new HashSet<String>();
    private final Set<String> ignoredAgents = new HashSet<String>();
    private final Map<ObjectName, ObjectLink> orphanedLinks = new HashMap<ObjectName,ObjectLink>();

    public void nodeAddedEvent(Node node) {
        if (node.isPhysical()) {
            ObjectName name  = new ObjectName(node.getName(), node.getModel().getServerID());
            add(name);
        }
    }




    public void nodeUpdatedEvent(Node node, int i, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void nodeRemovedEvent(Node node) {
        if (node.isPhysical()) {

        }
    }

    static class ObjectRelationFactory implements PositionRelation.Factory{
        private final ObjectLink link;

        public ObjectRelationFactory(ObjectLink link) {
            this.link = link;
        }

        public PositionRelation create(PositionRelationEdge edge) {
            return new PositionRelation(link, edge);
        }
    }

    public void ignoreTypes(Iterable<String> typesToIgnore) {
        this.ignordTypes.clear();
        for (String type : typesToIgnore) {
            this.ignordTypes.add(type);
        }
        // Todo  update filter....

    }

    public void ignoreAgents(Iterable<String> agentsToIgnore) {
        this.ignoredAgents.clear();
        for (String type : agentsToIgnore) {
            this.ignoredAgents.add(type);
        }
        // Todo

    }

    public PositionLinkRelationManager(InformationObjectNodeModel objectNodeModel, InformationObjectNodeGraph informationNodeGraph, InformationObjectModel informationObjectModel) {
        this.objectNodeModel = objectNodeModel;
        this.informationNodeGraph = informationNodeGraph;
        this.informationObjectModel = informationObjectModel;

    }

    @Override
    public void onObjectLinking(ObjectLinkingEvent linEvent) {
        addObjectRelation(linEvent.getLinking().getObjectLink());
    }



    @Override
    public void onObjectLinkingRemoved(ObjectLinkingEvent linEvent) {
        super.onObjectLinkingRemoved(linEvent);
    }
    /**
     *
     * @param linking
     */
    private void addObjectRelation (ObjectLink linking) {
        Node node = objectNodeModel.findNode(linking.getObjectName().getName());

        System.out.println("nodes = " + CollectionUtils.collect(objectNodeModel.getNodes(), new Transformer<Node, Object>() {
            public Object transform(Node node) {
                return node.getName();
            }
        }));
        DefaultInformationObject informationObject = linking.getInformationObject();
        System.out.print("Linking from: " + linking.getInformationObject().getUri());
        System.out.println(" => Linking to: " + linking.getObjectName().getName());

        InformationObjectNode informationNode = objectNodeModel.findInformationNode(informationObject.getUri());
        if (informationNode == null){
            informationNode = new InformationObjectNode(objectNodeModel, informationObject);
            objectNodeModel.addNode(informationNode);
        }
        if (node != null) {
            PositionRelationEdge edge = informationNodeGraph.getObjectEdge(informationNode, node);
            if (edge == null) {
                edge = informationNodeGraph.addRelation(informationNode, node, new ObjectRelationFactory(linking));
                System.out.println("Adding edge = " + edge);
            } else {
                System.out.println("No need to add edge");
            }
            // edge knows about posts... no update needed
            edge.update();
        }else {
            orphanedLinks.put(linking.getObjectName(), linking);
        }
    }
    private void add(ObjectName name) {
        final ObjectLink objectLink = orphanedLinks.remove(name);
        if (objectLink != null) {
            addObjectRelation(objectLink);
        } else {
            orphanedLinks.put(name, objectLink);
        }
    }

}
