package org.infoobject.core.infoobject;

import org.infoobject.core.infoobject.model.InformationObjectNodeGraphImpl;
import org.infoobject.core.infoobject.model.InformationNodeModelImpl;
import org.infoobject.core.infoobject.model.*;
import org.infoobject.core.infoobject.event.InformationObjectListenerAdapter;
import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.model.node.INodeModel;
import net.sf.magicmap.client.interfaces.NodeModelListener;

/**
 * <p>
 * Class InformationNodeManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 10:35:17
 */
public class InformationNodeManager {

    private final InformationNodeModelImpl nodeModel;
    private final InformationObjectNodeGraphImpl informationNodeGraph;
    private final InformationObjectManager informationObjectManager;

    static class ObjectRelationFactory implements ObjectRelation.Factory{
        private final ObjectLink link;

        public ObjectRelationFactory(ObjectLink link) {
            this.link = link;
        }

        public ObjectRelation create(ObjectRelationEdge edge) {
            return new ObjectRelation(link, edge);
        }
    }
    /**
     * 
     * @param nodeModel
     * @param informationObjectManager
     */
    public InformationNodeManager(INodeModel nodeModel, InformationObjectManager informationObjectManager) {
        this.informationObjectManager = informationObjectManager;
        this.nodeModel = new InformationNodeModelImpl(nodeModel);
        this.informationNodeGraph = new InformationObjectNodeGraphImpl();
        this.nodeModel.addNodeGraph(informationNodeGraph);
        this.nodeModel.addNodeModelListener(new NodeModelListener() {
            public void nodeAddedEvent(Node node) {
                if (node.isPhysical()){
                    informationNodeGraph.insertNode(node);
                }
            }

            public void nodeUpdatedEvent(Node node, int i, Object o) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void nodeRemovedEvent(Node node) {
                if (node.isPhysical()) {
                   
                }
            }
        });
        informationObjectManager.getModel().addInformationObjectListener(new InformationObjectListenerAdapter(){
            @Override
            public void onObjectLinking(ObjectLinkingEvent linEvent) {
                addObjectRelation(linEvent.getLinking());                
            }



            @Override
            public void onObjectLinkingRemoved(ObjectLinkingEvent linEvent) {
                super.onObjectLinkingRemoved(linEvent);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
    }

    public InformationObjectNodeModel getNodeModel() {
        return nodeModel;
    }

    public InformationObjectNodeGraph getInformationNodeGraph() {
        return informationNodeGraph;
    }

    /**
     * 
     * @param node
     */
    public void expand(InformationObjectNode node){
        informationObjectManager.explode(node.getInformationObject().getUri());
    }

    /**
     * 
     * @param node
     */
    public void expand(Node node){
        if (node.isPhysical()) {
            informationObjectManager.load(ObjectName.positionName(node.getName(), node.getModel().getServerID()));
        }
    }
    private void addObjectRelation(ObjectLinkPost linking) {
        Node node = nodeModel.findNode(linking.getObjectLink().getObjectName().getName());
        System.out.println("linking.getObjectLink().getObjectName().getName() = " + linking.getObjectLink().getObjectName().getName());
        System.out.println(" node ? = " + node);
        InformationObject informationObject = linking.getObjectLink().getInformationObject();
        InformationObjectNode informationNode = nodeModel.findInformationNode(informationObject.getUri());
        if (informationNode == null){
            informationNode = new InformationObjectNode(nodeModel, informationObject);
            nodeModel.addNode(informationNode);
        }
        if (node != null) {
            ObjectRelationEdge edge = informationNodeGraph.getObjectEdge(informationNode, node);
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
