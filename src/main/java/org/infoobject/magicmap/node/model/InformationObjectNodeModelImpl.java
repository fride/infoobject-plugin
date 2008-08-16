package org.infoobject.magicmap.node.model;

import net.sf.magicmap.client.model.node.INodeModel;
import net.sf.magicmap.client.model.node.NodeModelAdapter;
import org.infoobject.core.infoobject.domain.InformationObjectModel;

/**
 * <p>
 * Class InformationNodeModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:44:16
 */
public class InformationObjectNodeModelImpl extends NodeModelAdapter implements InformationObjectNodeModel {
    private InformationObjectNodeGraphImpl infoGraphImpl;
    private InformationObjectModel informationModel;
    
    public InformationObjectNodeModelImpl(INodeModel nodeModel) {
        super(nodeModel);
        infoGraphImpl = new InformationObjectNodeGraphImpl();
        nodeModel.addNodeGraph(infoGraphImpl);
    }

    public InformationObjectNode findInformationNode(String uri) {
        return (InformationObjectNode)super.findNode(InformationObjectNode.createInformationObjectId(uri));
    }

    

    public InformationObjectNodeGraph getInformationObjectNodeGraph(){
        return infoGraphImpl;
    }

}
