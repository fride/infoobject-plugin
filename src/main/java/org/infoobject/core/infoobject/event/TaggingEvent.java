package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.domain.TaggingPost;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObjectModel;

import java.util.EventObject;

/**
 * <p>
 * Class TaggingEvent ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 01:38:26
 */
public class TaggingEvent extends EventObject {
    private final TaggingPost tagging;

    public TaggingEvent(DefaultInformationObjectModel o, TaggingPost tagging) {
        super(o);
        this.tagging = tagging;
    }

    public TaggingPost getTagging() {
        return tagging;
    }

    @Override
    public DefaultInformationObjectModel getSource() {
        return (DefaultInformationObjectModel) super.getSource();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
