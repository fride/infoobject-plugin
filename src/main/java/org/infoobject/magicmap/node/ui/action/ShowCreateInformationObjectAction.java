package org.infoobject.magicmap.node.ui.action;

import com.jgoodies.forms.factories.ButtonBarFactory;
import net.sf.magicmap.client.gui.utils.GUIUtils;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.gui.utils.MagicDialog;
import net.sf.magicmap.client.gui.utils.OnSuccessCloseAction;
import net.sf.magicmap.client.model.node.INodeModelSelectionListener;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.model.node.NodeModelSelectionEvent;
import net.sf.magicmap.client.utils.ExceptionHandler;
import org.infoobject.magicmap.node.ui.CreateInformationObjectNodeView;

import javax.swing.*;
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
public class ShowCreateInformationObjectAction extends MagicAction implements INodeModelSelectionListener {

    private CreateInformationObjectNodeView view;
    private final JFrame frame;
    private Node selectedNode;

    /**
     * 
     * @param view for the dilog
     * @param frame
     */
    public ShowCreateInformationObjectAction(CreateInformationObjectNodeView view, JFrame frame) {
        super("Informationsobjekt anlegen");
        this.view = view;
        this.frame = frame;

    }

    protected boolean checkSelection() {
        return selectedNode != null && selectedNode != Node.EMPTY_NODE && selectedNode.isPhysical();
    }
    public void actionPerformed(ActionEvent event) {
        final MagicDialog dlg = new MagicDialog(this.frame);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(view.getView());
        panel.add(Box.createHorizontalGlue());



        OnSuccessCloseAction create = new OnSuccessCloseAction("Information anlegen",dlg) {
            protected void doAction(ActionEvent event) {
                dlg.lockDialog();
                try {
                    view.createInformation();
                } finally {
                    dlg.unlockDialog();
                }
            }
        };
        MagicAction close = new MagicAction("Abbrechen") {
            public void actionPerformed(ActionEvent event) {
                dlg.setVisible(false);
                dlg.dispose();
            }
        };
        
        create.attatch(view, "enabled");
        create.setExceptionHandler(new ExceptionHandler() {
            public void handle(Exception e) {
                dlg.showError("<html><h2>Error Creating Information</h2><br>" + e.getMessage()+ "<html>");
                e.printStackTrace();
            }
        });
        view.setSelectedNode(getSelectedNode());
        dlg.setComponents(view.getView(), new JLabel("Warten...."), ButtonBarFactory.buildCenteredBar(new JButton(close), new JButton(create)));
        //view.selectNode(getSelectedNode());
        dlg.pack();
        GUIUtils.locateOnScreen(dlg);
        dlg.setVisible(true);
    }


    public void selectionChanged(NodeModelSelectionEvent event) {
        this.selectedNode = event.getSelectedNode();
        setEnabled(checkSelection());
    }




    protected Node getSelectedNode() {
        return selectedNode;
    }
}
