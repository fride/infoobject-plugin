package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.domain.ObjectLinkPost;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObjectModel;

import java.util.EventObject;

/**
 * <p>
 * Class ObjectLinkingEvent ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 01:39:46
 */
public class ObjectLinkingEvent extends EventObject {
    private final ObjectLinkPost linkingTo;

    public ObjectLinkingEvent(DefaultInformationObjectModel objectModel, ObjectLinkPost linkingTo) {
        super(objectModel);
        this.linkingTo = linkingTo;
    }

    @Override
    public DefaultInformationObjectModel getSource() {
        return (DefaultInformationObjectModel) super.getSource();
    }

    public ObjectLinkPost getLinking() {
        return linkingTo;
    }
}
