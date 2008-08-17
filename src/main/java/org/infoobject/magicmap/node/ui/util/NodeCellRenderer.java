package org.infoobject.magicmap.node.ui.util;

import net.sf.magicmap.client.model.node.Node;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Class NodeCellRenderer ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 22:46:43
 */
public class NodeCellRenderer extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList jList, Object data, int row, boolean b, boolean b1) {
        JLabel label = (JLabel) super.getListCellRendererComponent(jList, data, row, b, b1);
        if (data != null) {
            Node node = (Node)data;
            label.setText(node.getDisplayName());
        }
        return label;
    }
}
