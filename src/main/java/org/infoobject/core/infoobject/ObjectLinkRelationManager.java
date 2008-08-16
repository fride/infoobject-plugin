package org.infoobject.core.infoobject;

import org.infoobject.core.infoobject.model.*;
import org.infoobject.core.infoobject.event.InformationObjectListenerAdapter;
import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import net.sf.magicmap.client.model.node.Node;

/**
 * <p>
 * 
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 13:30:08
 */
public class ObjectLinkRelationManager {

    private final InformationObjectNodeModel objectNodeModel;
    private final InformationObjectNodeGraph informationNodeGraph;
    private final InformationObjectModel informationObjectModel;

    static class ObjectRelationFactory implements ObjectRelation.Factory{
        private final ObjectLink link;

        public ObjectRelationFactory(ObjectLink link) {
            this.link = link;
        }

        public ObjectRelation create(ObjectRelationEdge edge) {
            return new ObjectRelation(link, edge);
        }
    }

    public ObjectLinkRelationManager(InformationObjectNodeModel objectNodeModel, InformationObjectNodeGraph informationNodeGraph, InformationObjectModel informationObjectModel) {
        this.objectNodeModel = objectNodeModel;
        this.informationNodeGraph = informationNodeGraph;
        this.informationObjectModel = informationObjectModel;

        InformationObjectListenerAdapter adapter = new InformationObjectListenerAdapter(){
            @Override
            public void onObjectLinking(ObjectLinkingEvent linEvent) {
                addObjectRelation(linEvent.getLinking());
            }



            @Override
            public void onObjectLinkingRemoved(ObjectLinkingEvent linEvent) {
                super.onObjectLinkingRemoved(linEvent); 
            }
        };
        informationObjectModel.addInformationObjectListener(adapter);

    }
     /**
     *
     * @param linking
     */
    private void addObjectRelation(ObjectLinkPost linking) {
        Node node = objectNodeModel.findNode(linking.getObjectLink().getObjectName().getName());
        System.out.println("linking.getObjectLink().getObjectName().getName() = " + linking.getObjectLink().getObjectName().getName());
        System.out.println(" node ? = " + node);
        InformationObject informationObject = linking.getObjectLink().getInformationObject();
        InformationObjectNode informationNode = objectNodeModel.findInformationNode(informationObject.getUri());
        if (informationNode == null){
            informationNode = new InformationObjectNode(objectNodeModel, informationObject);
            objectNodeModel.addNode(informationNode);
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
