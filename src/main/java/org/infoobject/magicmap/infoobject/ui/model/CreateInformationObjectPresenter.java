package org.infoobject.magicmap.infoobject.ui.model;

import org.infoobject.magicmap.infoobject.ui.forms.ObjectLinkForm;
import org.infoobject.magicmap.infoobject.ui.forms.InformationTaggingForm;
import org.infoobject.core.infoobject.model.InformationObject;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.gui.utils.GUIUtils;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.utils.AbstractModel;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.EventList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.*;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;

/**
 * <p>
 * Class CreateInformationObject ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 10:36:58
 */
public class CreateInformationObjectPresenter extends AbstractModel implements InformationObjectLoadCallback{

    private MagicAction loadAction = new MagicAction("Laden") {
        public void actionPerformed(ActionEvent event) {
            informationObjectPresenter.loadInformation(CreateInformationObjectPresenter.this);
        }
    };

    private EventComboBoxModel<Node> nodeCombo;


    private EventComboBoxModel<InformationObject> informationObjectCombo;

    private final AbstractInformationObjectPresenter informationObjectPresenter;
    private ObjectLinkForm objectLinkForm;
    private InformationObject selectedInformationObject;
    private Node selectedNode;
    private CardLayout cards;
    private JPanel cardPanel;
    private MagicAction nextAction;
    private MagicAction backAction;
    private JDialog dlg;
    private MagicAction createAction;
    private MagicAction cancelAction;
    private JComponent view;
    private InformationTaggingForm taggingForm;
    private boolean enabled;

    /**
     *
     * @param informationObjectPresenter
     */
    public CreateInformationObjectPresenter(AbstractInformationObjectPresenter informationObjectPresenter ) {
        this.informationObjectPresenter = informationObjectPresenter;
        setNodeList(informationObjectPresenter.getNodeList());
        setInformationObjectList(informationObjectPresenter.getInformationObjectList());
        objectLinkForm = new ObjectLinkForm(new DefaultComboBoxModel(), nodeCombo, informationObjectCombo);
        objectLinkForm.getInformationUri().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                setSelectedInformationObject((InformationObject)objectLinkForm.getInformationUri().getSelectedItem());
                checkActions();
            }
        });
        objectLinkForm.getNodeName().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                setSelectedNode((Node)objectLinkForm.getNodeName().getSelectedItem());
                checkActions();
            }
        });

        createActions();
        cards = new CardLayout();
        cardPanel = new JPanel(cards);


        objectLinkForm.setLoadAction(loadAction);
        objectLinkForm.showNodeSelection(false);

        taggingForm = new InformationTaggingForm();


    }



    private void createActions() {
        nextAction = new MagicAction("Weiter") {
            public void actionPerformed(ActionEvent event) {
                nextAction.setEnabled(false);
                backAction.setEnabled(true);
                cards.show(cardPanel, "2");
            }
        };

        backAction = new MagicAction("Zurück") {
            public void actionPerformed(ActionEvent event) {
                cards.show(cardPanel, "1");
                backAction.setEnabled(true);
                nextAction.setEnabled(false);
            }
        };
        createAction = new MagicAction("Informationsobjekt anlegen") {
            public void actionPerformed(ActionEvent event) {
                createInformation();
                dlg.setVisible(false);
            }
        };
        cancelAction = new MagicAction("Informationsobjekt Abbrechen") {
            public void actionPerformed(ActionEvent event) {
                // todo!
                dlg.setVisible(false);
            }
        };
    }

    private void setSelectedInformationObject(InformationObject informationObject) {
        if (this.selectedInformationObject == null || !this.selectedInformationObject.equals(informationObject)){
            InformationObject old = this.selectedInformationObject;
            this.selectedInformationObject = informationObject;
            firePropertyChange("selectedInformationObject", old, informationObject);
        }
    }

    public InformationObject getSelectedInformationObject() {
        return selectedInformationObject;
    }

    public void createInformationObject() {
        if (view == null) {
            cardPanel.add("1", objectLinkForm.getForm());
            cardPanel.add("2", taggingForm.getForm());
            dlg = new JDialog(MainGUI.getInstance().getMainFrame());

            final JPanel panel = new JPanel(new FormLayout("fill:p", "fill:p, 12dlu,p"));
            CellConstraints cc = new CellConstraints();
            panel.add(cardPanel, cc.xy(1,1));
            panel.add(buildButtonBar(), cc.xy(1,3));
            panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
            view = panel;
            dlg.getContentPane().add(view);
            dlg.pack();
            dlg.setModal(true);
        }
        GUIUtils.locateOnScreen(dlg);
        dlg.setVisible(true);
    }

    private void checkActions() {
        boolean enabled = this.selectedNode != null && Node.EMPTY_NODE != this.selectedNode;
        enabled &= this.selectedInformationObject != null;
        setEnabled(enabled);

    }

    private JPanel buildButtonBar() {
        PanelBuilder b = new DefaultFormBuilder(new FormLayout("p", "p"));
        CellConstraints cc = new CellConstraints();
        b.add(new JButton(backAction), cc.xy(b.getColumnCount(),1));

        b.appendRelatedComponentsGapColumn();
        b.appendColumn("p");
        b.add(new JButton(nextAction), cc.xy(b.getColumnCount(),1));

        b.appendGlueColumn();

        b.appendColumn("p");
        b.add(new JButton(cancelAction), cc.xy(b.getColumnCount(),1));
        b.appendRelatedComponentsGapColumn();
        b.appendColumn("p");
        b.add(new JButton(createAction), cc.xy(b.getColumnCount(),1));
        return b.getPanel();
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    private void createInformation() {
        informationObjectPresenter.createInformationObject(getSelectedNode(), getSelectedInformationObject());
    }

    /**
     *
     * @param nodeList
     */
    public void setNodeList(EventList<Node> nodeList) {
        this.nodeCombo = new EventComboBoxModel<Node>(nodeList);
    }

    /**
     *
     * @param informationObjects
     */
    public void setInformationObjectList(EventList<InformationObject> informationObjects) {
        this.informationObjectCombo = new EventComboBoxModel<InformationObject>(informationObjects);

    }

    public void onInformationObjectLoaded(InformationObject loaded) {
        informationObjectCombo.setSelectedItem(loaded);
    }

    public void setSelectedNode(Node selectedNode) {
        if (this.selectedNode == null || !this.selectedNode.equals(selectedNode)) {
            Node old = this.selectedNode;
            this.selectedNode = selectedNode;
            firePropertyChange("selectedNode", old,selectedNode);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    private void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            this.nextAction.setEnabled(enabled);
            this.createAction.setEnabled(enabled);
            firePropertyChange("enabled", !enabled, enabled);
        }
    }
}
