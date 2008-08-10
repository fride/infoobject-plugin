package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.model.ObjectLinkPost;
import org.infoobject.core.infoobject.model.InformationObjectModel;

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

    public ObjectLinkingEvent(InformationObjectModel objectModel, ObjectLinkPost linkingTo) {
        super(objectModel);
        this.linkingTo = linkingTo;
    }

    @Override
    public InformationObjectModel getSource() {
        return (InformationObjectModel) super.getSource();
    }

    public ObjectLinkPost getLinking() {
        return linkingTo;
    }
}
