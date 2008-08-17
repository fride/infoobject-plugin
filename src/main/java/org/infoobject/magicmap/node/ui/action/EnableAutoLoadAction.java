package org.infoobject.magicmap.node.ui.action;

import net.sf.magicmap.client.gui.utils.MagicAction;
import org.infoobject.magicmap.node.application.InformationNodeLoader;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * <p>
 * Class EnableAutoLoadAction ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 18:35:43
 */
public class EnableAutoLoadAction extends MagicAction {
    private final InformationNodeLoader loader;

    public EnableAutoLoadAction(InformationNodeLoader loader) {
        super ("Informationen auomatisch laden");
        this.loader = loader;
        this.loader.addPropertyChangeListener("autoLoad", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                setSelected(Boolean.TRUE.equals(event.getNewValue()));
            }
        });
        setSelected(loader.isAutoLoad());
    }

    public void actionPerformed(ActionEvent event) {
        this.loader.setAutoLoad(!this.loader.isAutoLoad());
    }
}
