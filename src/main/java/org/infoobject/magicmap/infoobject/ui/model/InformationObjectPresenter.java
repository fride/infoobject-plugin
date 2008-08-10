package org.infoobject.magicmap.infoobject.ui.model;

import org.infoobject.core.infoobject.InformationNodeManager;
import org.infoobject.core.infoobject.InformationObjectManager;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.magicmap.infoobject.ui.action.AbstractNodeAction;
import org.infoobject.magicmap.plugin.GuiComponentFactory;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.utils.GUIUtils;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.utils.AbstractModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;

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



    private CreateInformationObjectPresenter createInformationObjectPresenter;


    public InformationObjectPresenter(InformationNodeManager nodemanager, InformationObjectManager informationObjectManager, CrawlerManager crawler) {
        super(nodemanager, informationObjectManager, crawler);
    }


    public Action getShowCreateDialogAction() {
        if(showCreateDialogAction == null) {
            createInformationObjectPresenter = new CreateInformationObjectPresenter(this);
            showCreateDialogAction = new AbstractNodeAction("Information hinzufügen", getNodemanager()) {
                protected boolean checkSelection() {
                    return true;
                }
                public void actionPerformed(ActionEvent event) {
                    createInformationObjectPresenter.createInformationObject();
                }
            };
            new GuiComponentFactory().getNodeSelectionModel().addNodeModelSelectionListener(showCreateDialogAction);
        }
        return showCreateDialogAction;
    }
    public Action getEditInformationObjectAction() {
        return null;
    }

}
