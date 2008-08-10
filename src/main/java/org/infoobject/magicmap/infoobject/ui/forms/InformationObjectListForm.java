package org.infoobject.magicmap.infoobject.ui.forms;

import net.sf.magicmap.client.utils.AbstractModel;

import javax.swing.*;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;

/**
 * <p>
 * Class InformationObjectListForm ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 20:48:05
 */
public class InformationObjectListForm extends AbstractModel {

    private JList objectList = new JList();
    private JComponent objectDetailsForm;
    private JTextField userFilter = new JTextField(3);
    private JTextField tagFilter = new JTextField(3);
    private JComponent form;

    public InformationObjectListForm() {

    }

    public JComponent getForm() {
        if (form == null) {
            FormLayout l = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(l);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p:grow");
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p");
        }
        return form;
    }
}
