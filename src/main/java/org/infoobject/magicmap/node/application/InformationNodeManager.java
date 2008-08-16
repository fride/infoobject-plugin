package org.infoobject.magicmap.node.application;

import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;
import org.infoobject.magicmap.node.model.InformationObjectNodeModel;

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

    private final InformationObjectNodeModel objectNodeModel;
    private final InformationObjectNodeGraph informationNodeGraph;
    private final InformationObjectManager informationObjectManager;
    


    /**
     * 
     * @param objectNodeModel
     * @param informationObjectManager
     */
    public InformationNodeManager(InformationObjectNodeModel objectNodeModel, InformationObjectManager informationObjectManager) {
        this.informationObjectManager = informationObjectManager;
        this.objectNodeModel = objectNodeModel;
        this.informationNodeGraph = this.objectNodeModel.getInformationObjectNodeGraph();
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
