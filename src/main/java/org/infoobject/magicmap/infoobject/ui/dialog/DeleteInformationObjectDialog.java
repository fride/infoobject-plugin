package org.infoobject.magicmap.infoobject.ui.dialog;

import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.sf.magicmap.client.gui.utils.MagicAction;
import org.infoobject.magicmap.node.ui.ObjectLinkSelectionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * <p>
 * Class DeleteInformationObjectDialog ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 12:34:39
 */
public class DeleteInformationObjectDialog extends JDialog {

    private Action deleteAction;
    private Action cancelAction;
    private ObjectLinkSelectionView view;

    public DeleteInformationObjectDialog(Frame frame, ObjectLinkSelectionView view, final Action deleteAction, final Action cancelAction) {
        super(frame, "Informationsobjekt lšschen");
        this.view = view;
        this.deleteAction = new MagicAction("Information lšschen") {
            public void actionPerformed(ActionEvent event) {
                if (deleteAction != null ){
                    deleteAction.actionPerformed(event);
                }
                setVisible(false);
            }
        };
        this.cancelAction = new MagicAction("Abbrechen") {
            public void actionPerformed(ActionEvent event) {
                if (cancelAction != null) {
                    cancelAction.actionPerformed(event);
                }
                setVisible(false);
            }
        };
        buildComponents();
    }

    private void buildComponents() {
        JPanel panel = new JPanel(new FormLayout("p","p,12dlu,p"));
        CellConstraints cc = new CellConstraints();
        panel.add(view.visualProxy(null, new HashMap<String, Action>()), cc.xy(1,1));
        panel.add(ButtonBarFactory.buildRightAlignedBar(new JButton(cancelAction), new JButton(deleteAction)),cc.xy(1,3));
        getContentPane().add(panel);
    }


}
