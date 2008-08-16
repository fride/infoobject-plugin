package org.infoobject.core.infoobject.ui.action;

import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.model.node.INodeModelSelectionListener;
import net.sf.magicmap.client.model.node.NodeModelSelectionEvent;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.node.application.InformationNodeManager;

/**
 * <p>
 * Class AbstractNodeAction ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 20:20:10
 */
public abstract class AbstractNodeAction extends MagicAction implements INodeModelSelectionListener {
    private Node selectedNode;
    private final InformationNodeManager nodeManager;

    public AbstractNodeAction(String s, InformationNodeManager nodeManager) {
        super(s);
        this.nodeManager = nodeManager;
    }

    public AbstractNodeAction(String s, String s1,InformationNodeManager nodeManager) {
        super(s, s1);
        this.nodeManager = nodeManager;
    }

    public AbstractNodeAction(String s, String s1, String s2, InformationNodeManager nodeManager) {
        super(s, s1, s2);
        this.nodeManager = nodeManager;
    }

    public void selectionChanged(NodeModelSelectionEvent event) {
        this.selectedNode = event.getSelectedNode();
        setEnabled(checkSelection());
    }

    protected abstract boolean checkSelection();

    protected InformationNodeManager getNodeManager() {
        return nodeManager;
    }

    protected Node getSelectedNode() {
        return selectedNode;
    }
}
