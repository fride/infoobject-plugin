package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.to.ObjectLinkingTo;

import java.util.EventListener;

/**
 * <p>
 * Class ObjectLinkingHandler ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:47:23
 */
public interface ObjectLinkingHandler extends EventListener {

    void handleObjectLinkingAdded(ObjectLinkingTo linkingTo);
    void handleObjectLinkinRemoved(ObjectLinkingTo linkingTo);
}
