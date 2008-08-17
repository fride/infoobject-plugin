package org.infoobject.magicmap.visualization.ui.util;

import net.sf.magicmap.client.gui.utils.icon.INodeIcon;
import net.sf.magicmap.client.model.node.Node;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Class InformationObjectNodeIcon ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 17.08.2008
 *         Time: 10:54:31
 */
public class InformationObjectNodeIcon implements INodeIcon {

    public Icon getIcon(Node node) {
        final String attribute = node.getAttribute("depiction");

        return getIcon(node, attribute != null ? attribute : "classpath:NodeIcons/unknown.png");
    }

    /**
     * 
     * @param node
     * @param uri
     * @return
     */
    public Icon getIcon(Node node, String uri) {
        final ImageIcon imageIcon = InformationNodeIconCache.getInstance().getNodeIcon(uri);
        return imageIcon;
    }
    
    public Color getBgColor(Node node) {
        return Color.WHITE;
    }

    public Color getFgColor(Node node) {
        return Color.BLUE; 
    }
}
