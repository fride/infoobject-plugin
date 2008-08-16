package org.infoobject.core.infoobject.ui.action;

import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.ui.dialog.DeleteInformationObjectDialog;
import org.infoobject.core.infoobject.ui.model.ObjectLinkUI;
import org.infoobject.magicmap.node.application.InformationNodeManager;
import org.infoobject.magicmap.node.model.InformationObjectNode;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <p>
 * Class ShowDeleteInformationObjectAction ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 18:00:01
 */
public class ShowDeleteInformationObjectAction extends AbstractNodeAction{
    private final ObjectLinkUI objectLinkUI;
    private final Action deleteAction;

    public ShowDeleteInformationObjectAction(InformationNodeManager nodeManager, ObjectLinkUI objectLinkUI, Action deleteAction) {
        super("Information lšschen", nodeManager);
        this.objectLinkUI = objectLinkUI;
        this.deleteAction = deleteAction;
    }

    protected boolean checkSelection() {
        return getSelectedNode() != null && Node.EMPTY_NODE != getSelectedNode();
    }

    public void actionPerformed(ActionEvent event) {
        if (getSelectedNode() instanceof InformationObjectNode){
            objectLinkUI.enableNodeSelection(true);
            objectLinkUI.enableInformationSelection(false);
            objectLinkUI.setSelectedNode(null);
            objectLinkUI.setSelectedInformationObject(((InformationObjectNode)getSelectedNode()).getInformationObject());
        } else {
            objectLinkUI.enableNodeSelection(false);
            objectLinkUI.enableInformationSelection(true);
            objectLinkUI.setSelectedNode(getSelectedNode());
            objectLinkUI.setSelectedInformationObject(null);
        }
        final DeleteInformationObjectDialog dlg = new DeleteInformationObjectDialog(MainGUI.getInstance().getMainFrame(),
                objectLinkUI,
                deleteAction,
                null);
        dlg.pack();
        dlg.setVisible(true);
    }
}
