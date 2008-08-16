package org.infoobject.core.infoobject.ui.forms;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.infoobject.core.infoobject.ui.util.InformationObjectCellRenderer;

import javax.swing.*;


/**
 * <p>
 * Class ObjectLinkForm ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 20:49:03
 */
public class ObjectLinkForm {
    private JComboBox informationUri;
    private JComboBox nodeName;
    private JLabel nodeType = new JLabel();

    private JComponent form;
    private FormLayout layout;
    private JButton load = new JButton("Suchen");



    /**
     * 
     * @param typeModel
     */
    public ObjectLinkForm(ComboBoxModel nodeModel, ComboBoxModel uriModel) {
        informationUri = new JComboBox(uriModel);
        nodeName = new JComboBox(nodeModel);
        informationUri.setRenderer(new InformationObjectCellRenderer());
    }

    public JComboBox getInformationUri() {
        return informationUri;
    }

    public JComboBox getNodeName() {
        return nodeName;
    }

    public JLabel getNodeType() {
        return nodeType;
    }


    public void setLoadAction(Action loadAction) {
        this.load.setAction(loadAction);
    }
    public void showNodeSelection(boolean show){
        if (show) {

        }
    }
    public void setShowUrlSelection(boolean show){

    }
    public JComponent getForm(){


        if (form == null) {
            layout = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(layout);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p:grow");
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p");

            b.appendRow("p");
            b.add(new JLabel("Knoten"), cc.xy(1, b.getRowCount()));
            b.add(nodeName, cc.xyw(3,b.getRowCount(),3));

            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(new JLabel("Information"), cc.xy(1, b.getRowCount()));
            b.add(informationUri, cc.xy(3, b.getRowCount()));
            b.add(load, cc.xy(5, b.getRowCount()));


            form = b.getPanel();
        }

        return form;

    }

}
