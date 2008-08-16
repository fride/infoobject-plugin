package org.infoobject.core.infoobject;

import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.node.INodeModel;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.model.*;

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

    private final InformationObjectNodeModelImpl objectNodeModel;
    private final InformationObjectNodeGraph informationNodeGraph;
    private final InformationObjectManager informationObjectManager;


    /**
     * 
     * @param nodeModel
     * @param informationObjectManager
     */
    public InformationNodeManager(INodeModel nodeModel, InformationObjectManager informationObjectManager) {
        this.informationObjectManager = informationObjectManager;
        this.objectNodeModel = new InformationObjectNodeModelImpl(nodeModel);
        this.informationNodeGraph = objectNodeModel.getInformationObjectNodeGraph();
        this.objectNodeModel.addNodeGraph(informationNodeGraph);
        this.objectNodeModel.addNodeModelListener(new NodeModelListener() {
            public void nodeAddedEvent(Node node) {
                if (node.isPhysical()){
                    informationNodeGraph.insertNode(node);
                }
            }

            public void nodeUpdatedEvent(Node node, int i, Object o) {
                
            }

            public void nodeRemovedEvent(Node node) {
                if (node.isPhysical()) {
                   
                }
            }
        });
       
    }

    public InformationObjectNodeModel getNodeModel() {
        return objectNodeModel;
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


}
