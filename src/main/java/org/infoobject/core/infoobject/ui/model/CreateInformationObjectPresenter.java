package org.infoobject.core.infoobject.ui.model;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.util.concurrent.Lock;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.utils.GUIUtils;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.infoobject.domain.InformationObject;
import org.infoobject.core.infoobject.domain.Tagging;
import org.infoobject.core.infoobject.ui.forms.InformationObjectForm;
import org.infoobject.core.infoobject.ui.forms.InformationTaggingForm;
import org.infoobject.core.infoobject.ui.forms.ObjectLinkDetailsForm;
import org.infoobject.core.infoobject.ui.forms.ObjectLinkForm;
import org.infoobject.core.infoobject.ui.util.InformationObjectNodeListFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

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
    private ObjectLinkDetailsForm objectLinkDetailsForm;
    private InformationObject selectedInformationObject;
    private Node selectedNode;
    private JDialog dlg;
    private MagicAction createAction;
    private MagicAction cancelAction;
    private JComponent view;
    private InformationTaggingForm taggingForm;
    private boolean enabled;

    private EventList<Tagging> currentTags = new BasicEventList<Tagging>();
    private EventComboBoxModel<Tagging> currentTagsModel = new EventComboBoxModel<Tagging>(currentTags);

    private InformationObjectForm informationObjectForm = new InformationObjectForm();
    private InformationObjectNodeListFactory informationObjectNodeListFactory;

    /**
     *
     * @param informationObjectPresenter
     */
    public CreateInformationObjectPresenter(AbstractInformationObjectPresenter informationObjectPresenter ) {
        this.informationObjectPresenter = informationObjectPresenter;
        informationObjectNodeListFactory = informationObjectPresenter.getInformationObjectNodeListFactory();
        setNodeList(informationObjectNodeListFactory.getNodeList());

        //
        setInformationObjectList(informationObjectNodeListFactory.getInformationObjectList());

        objectLinkForm = new ObjectLinkForm(nodeCombo, informationObjectCombo);
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


        objectLinkDetailsForm = new ObjectLinkDetailsForm(new DefaultComboBoxModel());
        objectLinkForm.setLoadAction(loadAction);
        objectLinkForm.showNodeSelection(false);

        taggingForm = new InformationTaggingForm();
        taggingForm.setTagModel(currentTagsModel);

    }



    private void createActions() {
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
            //currentTags;
            InformationObject old = this.selectedInformationObject;
            this.selectedInformationObject = informationObject;
            final Lock lock = this.currentTags.getReadWriteLock().writeLock();
            
            try {
                lock.lock();
                this.currentTags.clear();
                this.currentTags.addAll(selectedInformationObject.getTaggings());
            }finally {
                lock.unlock();
            }
            if (selectedInformationObject.getMetadata() != null) {
                informationObjectForm.setMime(selectedInformationObject.getMetadata().getMimeType());
                informationObjectForm.setMime(selectedInformationObject.getMetadata().getTitle());
            }
            firePropertyChange("selectedInformationObject", old, informationObject);
        }
    }

    public InformationObject getSelectedInformationObject() {
        return selectedInformationObject;
    }

    public void editInformationObject() {
        buildView();
    }

    /**
     * Shows a dialog to enter a new information object.
     */
    public void createInformationObject() {
        buildView();
        GUIUtils.locateOnScreen(dlg);
        dlg.setVisible(true);
    }

    private void buildView() {
        if (view == null) {

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Verbindung", objectLinkDetailsForm.getForm());
            tabs.addTab("Tags", taggingForm.getForm());
            dlg = new JDialog(MainGUI.getInstance().getMainFrame());


            PanelBuilder b = new DefaultFormBuilder(new FormLayout("fill:p"));
            CellConstraints cc = new CellConstraints();

            b.appendRow("p");
            b.addSeparator("Verknüfpung festlegen", cc.xy(1,b.getRowCount()));
            b.appendRelatedComponentsGapRow();

            b.appendRow("p");
            b.add(objectLinkForm.getForm(), cc.xy(1,b.getRowCount()));
            b.appendUnrelatedComponentsGapRow();


            b.appendRow("p");
            b.addSeparator("Informationsdaten", cc.xy(1,b.getRowCount()));
            b.appendRelatedComponentsGapRow();

            b.appendRow("p");
            b.add(informationObjectForm.getForm(), cc.xy(1,b.getRowCount()));


            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(tabs, cc.xy(1,b.getRowCount()));
            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(buildButtonBar(), cc.xy(1,b.getRowCount()));

            b.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
            view = b.getPanel();
            dlg.getContentPane().add(view);
            dlg.pack();
            dlg.setModal(true);
        }
    }

    private void checkActions() {
        boolean enabled = this.selectedNode != null && Node.EMPTY_NODE != this.selectedNode;
        enabled &= this.selectedInformationObject != null;
        setEnabled(enabled);

    }

    private JPanel buildButtonBar() {
        PanelBuilder b = new DefaultFormBuilder(new FormLayout("p", "p"));
        CellConstraints cc = new CellConstraints();
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

    /**
     * 
     */
    private void createInformation() {
        String type = objectLinkForm.getNodeType().getName();
        final java.util.List<String> tags = taggingForm.getTags();
        Map<String, Boolean> taggings = new HashMap<String, Boolean>();
        for (String tag:tags) {
            if (tag.startsWith("--")) {
                taggings.put(tag.substring(2), false);
            } else {
                taggings.put(tag, true);
            }
        }
        /**
         * 
         */
        informationObjectPresenter.createInformationObject(
                getSelectedNode(),
                getSelectedInformationObject(),
                type, taggings);

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
            nodeCombo.setSelectedItem(selectedNode);
            firePropertyChange("selectedNode", old,selectedNode);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    private void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            this.createAction.setEnabled(enabled);
            firePropertyChange("enabled", !enabled, enabled);
        }
    }
}
