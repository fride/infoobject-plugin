package org.infoobject.magicmap.infoobject.ui.action;

import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

import org.infoobject.core.infoobject.InformationNodeManager;

/**
 * <p>
 * Class CreateInformationObjectAction ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 20:11:18
 */
public class CreateInformationObjectAction extends AbstractNodeAction{

    /**
     * 
     * @param nodeManager
     */
    public CreateInformationObjectAction(InformationNodeManager nodeManager) {
        super("Informaion hinzufügen",nodeManager);
    }

    protected boolean checkSelection() {
        return getSelectedNode() != Node.EMPTY_NODE && !getSelectedNode().isPhysical();
    }

    public void actionPerformed(ActionEvent event) {
        MainFrame frame = MainGUI.getInstance().getMainFrame();
        JDialog dlg = new JDialog(frame);
    }
}
