package org.infoobject.magicmap.node;

import org.infoobject.core.infoobject.InformationObjectManager;
import org.infoobject.core.infoobject.model.ObjectName;
import org.infoobject.magicmap.plugin.GuiComponentFactory;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.gui.utils.GUIBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
public class InformationNodeLoader implements net.sf.magicmap.client.interfaces.NodeModelListener {

    private final InformationObjectManager manager;
    private boolean enabled;
    private ConsoleView view;

    public InformationNodeLoader(InformationObjectManager manager, GuiComponentFactory factory) {
        this.manager = manager;
        view = factory.getConsoleView();
        final JMenu menu = factory.getInfoObjectMenu();
        MagicAction magicAction = new MagicAction("Informationen automatisch laden") {
            public void actionPerformed(ActionEvent event) {

            }
        };
        final JCheckBoxMenuItem item = GUIBuilder.createCheckBoxMenuItem(magicAction, true);
        menu.add(item);
        item.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent event) {
                enabled = item.isSelected();
            }
        });
        this.enabled  = true;

    }


    public void nodeAddedEvent(Node node) {
        if (node.isPhysical() && enabled){
            view.append("Loading infos for node " + node.getDisplayName());
            try {
                manager.load(ObjectName.positionName(node.getName(), node.getModel().getServerID()));
            } catch (Exception ex) {
                view.append("Loading infos for node " + node.getDisplayName() + " failed! " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            view.append("Ignored node, not enabled " + node.getDisplayName() + " " + enabled + " " + node.isPhysical());  
        }
    }

    public void nodeUpdatedEvent(Node node, int i, Object o) {

    }

    public void nodeRemovedEvent(Node node) {
        
    }


    public void setLoad(boolean enabled) {
        this.enabled = enabled;
    }
}
