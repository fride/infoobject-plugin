package org.infoobject.magicmap.infoobject.ui;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <p>
 * Class ObjectLinkDetailsView ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 19:28:12
 */
public class ObjectLinkDetailsView {
    private JComponent form;

    private JTextField myType = new JTextField(10);
    private JList allTyes = new JList();

    public ObjectLinkDetailsView(ListModel typeModel) {
        allTyes.setModel(typeModel);
        allTyes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2 && allTyes.getSelectedValue() != null) {
                    myType.setText(allTyes.getSelectedValue().toString());
                }
            }
        });
    }

    public JComponent getView() {
        if (form == null) {
            FormLayout layout = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(layout);

            b.appendRelatedComponentsGapColumn();
            b.appendColumn("fill:p:grow");


            b.appendRow("p");
            b.add(new JLabel("Ihr Typ"), cc.xy(1, b.getRowCount()));
            b.add(myType, cc.xy(3, b.getRowCount()));
            b.appendRelatedComponentsGapRow();

            b.appendRow("fill:p:grow");
            b.add(new JLabel("Ale Typen"), cc.xy(1, b.getRowCount()));
            b.add(new JScrollPane(allTyes), cc.xy(3, b.getRowCount()));

            form = b.getPanel();
        }
        return form;
    }
    public JTextField getMyType() {
        return myType;
    }


    public JList getAllTyes() {
        return allTyes;
    }
}
