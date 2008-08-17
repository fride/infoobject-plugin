package org.infoobject.magicmap.visualization.ui;

import net.sf.magicmap.client.gui.utils.icon.NodeIconContainer;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.magicmap.visualization.application.VisualizationManager;
import org.infoobject.magicmap.visualization.ui.util.InformationObjectNodeIcon;

/**
 * <p>
 * Class VisualizationView ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 17.08.2008
 *         Time: 10:56:05
 */
public class VisualizationView {
    private VisualizationManager manager;

    public VisualizationView(VisualizationManager manager) {
        this.manager = manager;
        final NodeIconContainer iconContainer = NodeIconContainer.getInstance();
        iconContainer.registerNodeIcon(InformationObjectNode.NODE_TYPE, new InformationObjectNodeIcon());
    }
}
