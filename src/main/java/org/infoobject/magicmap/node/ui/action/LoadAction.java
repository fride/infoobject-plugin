package org.infoobject.magicmap.node.ui.action;

import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.node.application.InformationNodeManager;

import java.awt.event.ActionEvent;

/**
 * <p>
 * Class LoadAction ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 17.08.2008
 *         Time: 00:16:10
 */
public class LoadAction extends AbstractNodeAction{

    public LoadAction(InformationNodeManager nodeManager) {
        super("Nach informationenn suchen", nodeManager);
        setEnabled(false);
    }

    protected boolean checkSelection() {
        return getSelectedNode() != null && getSelectedNode() != Node.EMPTY_NODE ;
    }

    /**
     * 
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        getNodeManager().expand(getSelectedNode());
    }
}
