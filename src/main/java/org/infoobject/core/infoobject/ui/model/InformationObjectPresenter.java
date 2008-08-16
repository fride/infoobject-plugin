package org.infoobject.core.infoobject.ui.model;

import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.model.node.INodeSelectionModel;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.infoobject.ui.action.AbstractNodeAction;
import org.infoobject.core.infoobject.ui.action.ShowCreateInformationObjectAction;
import org.infoobject.core.infoobject.ui.action.ShowDeleteInformationObjectAction;
import org.infoobject.core.infoobject.ui.util.InformationObjectNodeListFactory;
import org.infoobject.magicmap.node.application.InformationNodeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <p>
 * Class InformationObjectPresenter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 20:45:39
 */
public class InformationObjectPresenter extends AbstractInformationObjectPresenter {



    private AbstractNodeAction showCreateDialogAction;
    private AbstractNodeAction showDeleteDialogAction;



    private ObjectLinkUI objectLinkUI;
    private CreateInformationObjectPresenter createInformationObjectPresenter;



    public InformationObjectPresenter(InformationNodeManager nodemanager, InformationObjectManager informationObjectManager, CrawlerManager crawler, InformationObjectNodeListFactory nodeListFactory) {
        super (nodemanager, informationObjectManager, crawler,nodeListFactory);
        objectLinkUI = new ObjectLinkUI(nodeListFactory);
    }

    

    /**
     *
     * @return an action that shows a dialog to create and edit information objects.
     */
    public AbstractNodeAction getShowCreateAnEditDialogAction() {
        if(showCreateDialogAction == null) {
            showCreateDialogAction = new ShowCreateInformationObjectAction(getNodemanager(), createInformationObjectPresenter);
        }
        return showCreateDialogAction;
    }
    public Action getEditInformationObjectAction() {
        return null;
    }

    /**
     *
     * @return
     */
    public AbstractNodeAction getDeleteInformationObjectAction() {
        if (showDeleteDialogAction == null) {
            final MagicAction deleteAction = new MagicAction("Lšschen") {
                public void actionPerformed(ActionEvent event) {
                    delete(objectLinkUI.getSelectedInformationObject(), objectLinkUI.getSelectedNode());
                }
            };
            showDeleteDialogAction = new ShowDeleteInformationObjectAction(getNodemanager(), objectLinkUI, deleteAction);
        }
        return showDeleteDialogAction;

    }



}
