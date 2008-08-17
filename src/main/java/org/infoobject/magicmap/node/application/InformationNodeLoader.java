package org.infoobject.magicmap.node.application;

import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.infoobject.domain.ObjectName;

/**
 * <p>
 * Class InformationNodeLoader ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 11.08.2008
 *         Time: 22:28:53
 */
public class InformationNodeLoader extends AbstractModel implements NodeModelListener {

    private final InformationObjectManager manager;
    private boolean enabled;
    private ConsoleView view;

    public InformationNodeLoader(InformationObjectManager manager) {
        this.manager = manager;
        this.enabled  = true;
    }


    public void nodeAddedEvent(Node node) {
        if (node.isPhysical() && enabled){
            manager.load(ObjectName.positionName(node.getName(), node.getModel().getServerID()));
        }

    }

    public void nodeUpdatedEvent(Node node, int i, Object o) {

    }

    public void nodeRemovedEvent(Node node) {
        
    }


    public void setAutoLoad(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            firePropertyChange("autoLoad", !enabled, enabled);
        }
    }

    public boolean isAutoLoad() {
        return enabled;
    }
}
