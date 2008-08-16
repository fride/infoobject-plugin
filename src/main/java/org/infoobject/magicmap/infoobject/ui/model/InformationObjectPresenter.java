package org.infoobject.magicmap.infoobject.ui.model;

import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.model.node.INodeSelectionModel;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.infoobject.InformationNodeManager;
import org.infoobject.core.infoobject.InformationObjectManager;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.infoobject.model.InformationObjectNode;
import org.infoobject.magicmap.infoobject.ui.action.AbstractNodeAction;
import org.infoobject.magicmap.infoobject.ui.dialog.DeleteInformationObjectDialog;
import org.infoobject.magicmap.plugin.GuiComponentFactory;

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


    public InformationObjectPresenter(InformationNodeManager nodemanager, InformationObjectManager informationObjectManager, CrawlerManager crawler, GuiComponentFactory guiComponentFactory) {
        super(nodemanager, informationObjectManager, crawler, guiComponentFactory);
        objectLinkUI = new ObjectLinkUI(getInformationObjectNodeListFactory());
    }




    /**
     *
     * @return an action that shows a dialog to create and edit information objects.
     */
    public Action getShowCreateAnEditDialogAction() {
        if(showCreateDialogAction == null) {
            final INodeSelectionModel selectionModel = getGuiComponentFactory().getNodeSelectionModel();
            createInformationObjectPresenter = new CreateInformationObjectPresenter(this);
            showCreateDialogAction = new AbstractNodeAction("Information hinzufügen", getNodemanager()) {
                protected boolean checkSelection() {
                    return true;
                }
                public void actionPerformed(ActionEvent event) {
                    createInformationObjectPresenter.setSelectedNode(selectionModel.getSelectedNode());
                    createInformationObjectPresenter.createInformationObject();
                }
            };
            selectionModel.addNodeModelSelectionListener(showCreateDialogAction);
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
    public Action getDeleteInformationObjectAction() {
        if (showDeleteDialogAction == null) {
            final INodeSelectionModel selectionModel = getGuiComponentFactory().getNodeSelectionModel();
            final MagicAction deleteAction = new MagicAction("Löschen") {
                public void actionPerformed(ActionEvent event) {
                    delete(objectLinkUI.getSelectedInformationObject(), objectLinkUI.getSelectedNode());
                }
            };
            showDeleteDialogAction = new AbstractNodeAction("Information löschen",getNodemanager()) {
                protected boolean checkSelection() {
                    return getSelectedNode() != null && Node.EMPTY_NODE != getSelectedNode();
                }

                public void actionPerformed(ActionEvent event) {


                    if (getSelectedNode() instanceof InformationObjectNode){
                        objectLinkUI.enableNodeSelection(true);
                        objectLinkUI.enableInformationSelection(false);
                        objectLinkUI.setSelectedNode(null);
                        objectLinkUI.setSelectedInformationObject(((InformationObjectNode)getSelectedNode()).getInformationObject());
                    } else {
                        objectLinkUI.enableNodeSelection(false);
                        objectLinkUI.enableInformationSelection(true);
                        objectLinkUI.setSelectedNode(getSelectedNode());
                        objectLinkUI.setSelectedInformationObject(null);
                    }
                    final DeleteInformationObjectDialog dlg = new DeleteInformationObjectDialog(MainGUI.getInstance().getMainFrame(),
                            objectLinkUI,
                            deleteAction,
                            null);
                    dlg.pack();
                    dlg.setVisible(true);
                }
            };
            selectionModel.addNodeModelSelectionListener(showDeleteDialogAction);
        }
        return showDeleteDialogAction;

    }



}
