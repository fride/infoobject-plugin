package org.infoobject.magicmap.node.ui.action;

import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.infoobject.ui.dialog.DeleteInformationObjectDialog;
import org.infoobject.magicmap.node.ui.ObjectLinkSelectionView;
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
    private final ObjectLinkSelectionView objectLinkView;
    private final Action deleteAction;

    public ShowDeleteInformationObjectAction(InformationNodeManager nodeManager, ObjectLinkSelectionView objectLinkView, Action deleteAction) {
        super("Information lšschen", nodeManager);
        this.objectLinkView = objectLinkView;
        this.deleteAction = deleteAction;
    }

    protected boolean checkSelection() {
        return getSelectedNode() != null && Node.EMPTY_NODE != getSelectedNode();
    }

    public void actionPerformed(ActionEvent event) {
        if (getSelectedNode() instanceof InformationObjectNode){
            objectLinkView.enableNodeSelection(true);
            objectLinkView.enableInformationSelection(false);
            objectLinkView.setSelectedNode(null);
            objectLinkView.setSelectedInformationObject(((InformationObjectNode)getSelectedNode()).getInformationObject());
        } else {
            objectLinkView.enableNodeSelection(false);
            objectLinkView.enableInformationSelection(true);
            objectLinkView.setSelectedNode(getSelectedNode());
            objectLinkView.setSelectedInformationObject(null);
        }
        final DeleteInformationObjectDialog dlg = new DeleteInformationObjectDialog(MainGUI.getInstance().getMainFrame(),
                objectLinkView,
                deleteAction,
                null);
        dlg.pack();
        dlg.setVisible(true);
    }
}
