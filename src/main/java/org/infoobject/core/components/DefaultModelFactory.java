package org.infoobject.core.components;

import net.sf.magicmap.client.controller.IController;
import net.sf.magicmap.client.model.node.INodeModel;
import org.infoobject.core.infoobject.domain.InformationObjectModel;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObjectModel;
import org.infoobject.magicmap.node.model.InformationObjectNodeModel;
import org.infoobject.magicmap.node.model.InformationObjectNodeModelImpl;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;

/**
 * <p>
 * Class ModelFactory ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 17:12:40
 */
public class DefaultModelFactory implements ModelFactory {
    private InformationObjectModel informationObjectModel;
    private InformationObjectNodeModel informationObjectNodeModel;
    private INodeModel nodeModel;
    private final IController controller;

    public DefaultModelFactory(IController controller) {
        this.controller = controller;
    }


    public InformationObjectModel getInformationObjectModel() {
        if (informationObjectModel == null) {
            informationObjectModel = new DefaultInformationObjectModel();
        }
        return informationObjectModel;
    }

    public InformationObjectNodeModel getInformationObjectNodeModel() {
        if (informationObjectNodeModel == null) {
            informationObjectNodeModel = new InformationObjectNodeModelImpl(getNodeModel());
        }
        return informationObjectNodeModel;
    }

    public INodeModel getNodeModel() {
        if (nodeModel == null) {
            nodeModel = controller.getNodeModel();
        }
        return nodeModel;
    }

    /**
     * 
     * @return
     */
    public InformationObjectNodeGraph getInformationObjectNodeGraph() {
        return getInformationObjectNodeModel().getInformationObjectNodeGraph();
    }

    public void start() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
