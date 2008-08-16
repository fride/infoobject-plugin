package org.infoobject.core.infoobject.ui.action;

import org.infoobject.core.infoobject.ui.model.CreateInformationObjectPresenter;
import org.infoobject.magicmap.node.application.InformationNodeManager;

import java.awt.event.ActionEvent;

/**
 * <p>
 * Class ShowCreateInformationObjectAction ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 18:04:55
 */
public class ShowCreateInformationObjectAction extends AbstractNodeAction{

    private CreateInformationObjectPresenter createInformationObjectPresenter;

    public ShowCreateInformationObjectAction(InformationNodeManager nodeManager, CreateInformationObjectPresenter createInformationObjectPresenter) {
        super("Informationsobjekt anlegen", nodeManager);
        this.createInformationObjectPresenter = createInformationObjectPresenter;
    }

    protected boolean checkSelection() {
        return true;
    }
    public void actionPerformed(ActionEvent event) {
        createInformationObjectPresenter.setSelectedNode(getSelectedNode());
        createInformationObjectPresenter.createInformationObject();
    }
}
