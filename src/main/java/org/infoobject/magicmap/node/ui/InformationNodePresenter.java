package org.infoobject.magicmap.node.ui;

import org.infoobject.magicmap.node.application.InformationNodeManager;
import org.infoobject.magicmap.node.ui.action.EnableAutoLoadAction;

/**
 * <p>
 * Class InformationNodeUI ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 18:48:00
 */
public class InformationNodePresenter {
    private final InformationNodeManager informationNodeManager;
    private EnableAutoLoadAction enableAutoLoadAction;
    /**
     * 
     * @param informationNodeManager
     */
    public InformationNodePresenter(InformationNodeManager informationNodeManager) {
        this.informationNodeManager = informationNodeManager;
    }
    

    public EnableAutoLoadAction getEnableAutoLoadAction() {
        if (enableAutoLoadAction == null) {
            enableAutoLoadAction = new EnableAutoLoadAction(informationNodeManager.getInformationNodeLoader());
        }
        return enableAutoLoadAction;
    }
}
