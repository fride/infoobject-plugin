package org.infoobject.core.infoobject.ui.forms;

import net.sf.magicmap.client.utils.AbstractModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;

/**
 * <p>
 * Class InformationMetadataForm ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 22:44:09
 */
public class InformationMetadataForm extends AbstractModel {
    private JTextField title = new JTextField(10);
    private JCheckBox editTitle = new JCheckBox("€ndern");
    private JTextField mimeType = new JTextField();
    private JCheckBox editMime = new JCheckBox("€ndern");

    private boolean mimeEditable;
    private boolean titleEditable;
    private boolean uriEditable;

    private JComponent form;


    public InformationMetadataForm() {
        editMime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setMimeEditable(editMime.isSelected());
            }
        });
        editTitle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setTitleEditable(editTitle.isSelected());
            }
        });
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

            b.appendRow("p");
            b.add(new JLabel("Titel"), cc.xy(1, b.getRowCount()));
            b.add(title, cc.xy(3, b.getRowCount()));
            b.add(editTitle, cc.xy(5, b.getRowCount()));

            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(new JLabel("Mime"), cc.xy(1, b.getRowCount()));
            b.add(mimeType, cc.xy(3, b.getRowCount()));
            b.add(editMime, cc.xy(5, b.getRowCount()));
            
            b.appendRelatedComponentsGapRow();

            form = b.getPanel();
        }
        return form;
    }

    public void setTitle(String s) {
        String old = this.title.getText();
        if (!old.equals(s)) {
            title.setText(s);
            firePropertyChange("title", old, s);
        }

    }

    public String getTitle(){
        return this.title.getText();
    }

    public void setMimeType(String s) {
        String old = this.mimeType.getText();
        if (!old.equals(s)) {
            mimeType.setText(s);
            firePropertyChange("mimeType", old, s);
        }

    }

    public String getMimeType(){
        return this.mimeType.getText();
    }
    public boolean isUriEditable() {
        return uriEditable;
    }

    

    public boolean isTitleEditable() {
        return titleEditable;
    }

    public void setTitleEditable(boolean titleEditable) {
        if (this.titleEditable !=  titleEditable) {
            try {
                fireVetoableChange("titleEditable", !titleEditable, titleEditable);
            } catch (PropertyVetoException e) {
                editTitle.setSelected(false);
            }
            this.titleEditable = titleEditable;
            firePropertyChange("titleEditable", !this.titleEditable, this.titleEditable);
        }
        this.title.setEditable(titleEditable);
        editTitle.setSelected(titleEditable);
    }

    /**
     *
     * @param mimeEditable
     */
    public void setMimeEditable(boolean mimeEditable) {
        if (!this.mimeEditable != mimeEditable) {
            try {
                fireVetoableChange("mimeEditable", !this.mimeEditable, this.mimeEditable);
            } catch (PropertyVetoException e) {
                this.editMime.setSelected(false);
            }
            this.mimeEditable = mimeEditable;
            firePropertyChange("mimeEditable", !this.mimeEditable, this.mimeEditable);
        }
        this.mimeType.setEditable(mimeEditable);
        editMime.setSelected(mimeEditable);
    }
}
