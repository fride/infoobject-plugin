package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import org.infoobject.core.infoobject.event.TaggingEvent;
import org.infoobject.core.infoobject.event.InformationMetadataEvent;

import java.util.EventListener;

/**
 * <p>
 * Class InformationObjectListener ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:43:48
 */
public interface InformationObjectListener extends EventListener {


    void onTagging(TaggingEvent tagging);
    void onTaggingRemoved(TaggingEvent tagging);

    void onObjectLinking(ObjectLinkingEvent linEvent);
    void onObjectLinkingRemoved(ObjectLinkingEvent linEvent);

    void onMetadata(InformationMetadataEvent mEvent);
    void onMetadataRemoved(InformationMetadataEvent mEvent);
}
