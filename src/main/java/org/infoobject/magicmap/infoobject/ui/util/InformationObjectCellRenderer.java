package org.infoobject.magicmap.infoobject.ui.util;

import org.infoobject.core.infoobject.domain.InformationObject;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Class InformationObjectCellRenderer ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 00:55:12
 */
public class InformationObjectCellRenderer extends DefaultListCellRenderer {
    //InformationObject

    @Override
    public Component getListCellRendererComponent(JList jList, Object data, int row, boolean b, boolean b1) {
        JLabel label = (JLabel) super.getListCellRendererComponent(jList, data, row, b, b1);
        if (data != null) {
            InformationObject info = (InformationObject)data;
            if (info.getObjectLinks().size() == 0) {
                label.setText("* " + info.getUri());
            } else {
                label.setText(info.getUri());
            }
        }
        return label;
    }
}
