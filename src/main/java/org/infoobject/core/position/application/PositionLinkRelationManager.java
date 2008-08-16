package org.infoobject.core.position.application;

import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.domain.InformationObjectModel;
import org.infoobject.core.infoobject.domain.ObjectLink;
import org.infoobject.core.infoobject.domain.ObjectLinkPost;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObject;
import org.infoobject.core.infoobject.event.InformationObjectListenerAdapter;
import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import org.infoobject.core.relation.domain.PositionRelation;
import org.infoobject.core.relation.domain.PositionRelationEdge;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;
import org.infoobject.magicmap.node.model.InformationObjectNodeModel;

import java.util.HashSet;
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
public class PositionLinkRelationManager extends InformationObjectListenerAdapter{

    private final InformationObjectNodeModel objectNodeModel;
    private final InformationObjectNodeGraph informationNodeGraph;
    private final InformationObjectModel informationObjectModel;
    private final Set<String> ignordTypes = new HashSet<String>();
    private final Set<String> ignoredAgents = new HashSet<String>();

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
        addObjectRelation(linEvent.getLinking());
    }



    @Override
    public void onObjectLinkingRemoved(ObjectLinkingEvent linEvent) {
        super.onObjectLinkingRemoved(linEvent);
    }
    /**
     *
     * @param linking
     */
    private void addObjectRelation(ObjectLinkPost linking) {
        Node node = objectNodeModel.findNode(linking.getObjectLink().getObjectName().getName());
        System.out.println("linking.getObjectLink().getObjectName().getName() = " + linking.getObjectLink().getObjectName().getName());
        System.out.println(" node ? = " + node);
        DefaultInformationObject informationObject = linking.getObjectLink().getInformationObject();
        InformationObjectNode informationNode = objectNodeModel.findInformationNode(informationObject.getUri());
        if (informationNode == null){
            informationNode = new InformationObjectNode(objectNodeModel, informationObject);
            objectNodeModel.addNode(informationNode);
        }
        if (node != null) {
            PositionRelationEdge edge = informationNodeGraph.getObjectEdge(informationNode, node);
            if (edge == null) {
                edge = informationNodeGraph.addRelation(informationNode, node, new ObjectRelationFactory(linking.getObjectLink()));
            }
            // edge knows about posts... no update needed
            edge.update();
        } else {
            //System.out.println("linking.getObjectLink().getObjectName().getName() = " + linking.getObjectLink().getObjectName().getName());
        }

    }
}
