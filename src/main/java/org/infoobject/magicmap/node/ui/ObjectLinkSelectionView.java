package org.infoobject.magicmap.node.ui;

import ca.odell.glazedlists.swing.EventComboBoxModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.infoobject.domain.InformationObject;
import org.infoobject.magicmap.infoobject.ui.util.NodeMatcherEditor;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectMatcherEditor;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectNodeListFactory;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectCellRenderer;
import org.infoobject.magicmap.node.ui.util.NodeCellRenderer;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

/**
 * <p>
 * Class ObjectLinkUI ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 12.08.2008
 *         Time: 22:49:00
 */
public class ObjectLinkSelectionView extends AbstractModel {

    private final InformationObjectNodeListFactory listFactory;
    private Node selectedNode = Node.EMPTY_NODE;
    private InformationObject selectedInformationObject;

    private EventComboBoxModel<Node> nodeModel;
    private EventComboBoxModel<InformationObject> informationObjectModel;

    private NodeMatcherEditor nodeMatcherEditor = new NodeMatcherEditor();
    private InformationObjectMatcherEditor informationObjectMatcherEditor = new InformationObjectMatcherEditor();
    private JComponent view;
    private JComboBox informationUri;
    private JComboBox nodeName;
    private JButton loadButton;

    /**
     *
     * @param listFactory needed for the lists.
     */
    public ObjectLinkSelectionView(InformationObjectNodeListFactory listFactory) {
        this.listFactory = listFactory;
        nodeModel = new EventComboBoxModel<Node>(listFactory.getNodeList(informationObjectMatcherEditor));
        informationObjectModel = new EventComboBoxModel<InformationObject>(listFactory.getInformationObjectList(nodeMatcherEditor));
        informationUri = new JComboBox(informationObjectModel);
        informationUri.setRenderer(new InformationObjectCellRenderer());
        informationUri.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                setSelectedInformationObject((InformationObject) informationUri.getSelectedItem());
            }
        });
        nodeName = new JComboBox(nodeModel);
        nodeName.setRenderer(new NodeCellRenderer());
        nodeName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                setSelectedNode((Node) nodeName.getSelectedItem());
            }
        });
        loadButton = new JButton();
    }


    public Node getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(Node newSelectedNode) {
        if (this.selectedNode == null || (newSelectedNode != null && !this.selectedNode.equals(newSelectedNode))) {
            Node oldselectedNode = this.selectedNode;
            this.selectedNode = newSelectedNode;
            firePropertyChange("selectedNode", oldselectedNode, this.selectedNode);
            this.nodeModel.setSelectedItem(newSelectedNode);
            if (!nodeName.isEnabled()) {
                informationObjectMatcherEditor.setInformationObject(null);
                nodeMatcherEditor.setFilterNode(newSelectedNode);
            }
        }
    }
    public void enableNodeSelection(boolean node) {
        nodeName.setEnabled(node);
    }

    public void enableInformationSelection(boolean node) {
        informationUri.setEnabled(node);
    }

    public InformationObject getSelectedInformationObject() {
        return selectedInformationObject;
    }

    public void setSelectedInformationObject(InformationObject selectedInformationObject) {
        if (this.selectedInformationObject == null || (selectedInformationObject != null && !this.selectedInformationObject.equals(selectedInformationObject))) {
            InformationObject old = this.selectedInformationObject;

            this.selectedInformationObject = selectedInformationObject;
            firePropertyChange("selectedInformationObject", old, selectedInformationObject);
            this.informationObjectModel.setSelectedItem(selectedInformationObject);
            if (!informationUri.isEnabled()) {
                nodeMatcherEditor.setFilterNode(null);
                informationObjectMatcherEditor.setInformationObject(selectedInformationObject);
            }
        }
    }

    /**
     *
     * @param attribute null or "select"
     * @return
     * @throws IllegalArgumentException
     */
    public JComponent visualProxy(String attribute, Map<String, Action> actions) throws IllegalArgumentException {
        JComponent view = null;
        if (null == attribute) {
            view = getCreateView();
            if (actions.containsKey("load")){
                loadButton.setAction(actions.get("load"));
                loadButton.setVisible(true);
            } else {
                loadButton.setVisible(false);
            }
        } else {
            throw new IllegalArgumentException("Attribute " + attribute);
        }
        return view;
    }

    /**
     * 
     * @return
     */
    private JComponent getCreateView() {
        if (view == null) {
            FormLayout layout = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(layout);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("fill:p:grow");
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p");

            createCombox();

            b.appendRow("p");
            b.add(new JLabel("Knoten"), cc.xy(1, b.getRowCount()));
            b.add(nodeName, cc.xyw(3,b.getRowCount(),3));

            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(new JLabel("Information"), cc.xy(1, b.getRowCount()));
            b.add(informationUri, cc.xy(3, b.getRowCount()));

            b.add(loadButton, cc.xy(5, b.getRowCount()));
            view = b.getPanel();
        }
        return view;
    }

    private void createCombox() {


    }

}
