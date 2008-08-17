package org.infoobject.html.application;

import org.infoobject.core.infoobject.event.InformationObjectListenerAdapter;
import org.infoobject.core.infoobject.event.InformationMetadataEvent;

/**
 * <p>
 * Class HtmlRelationManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 16:23:35
 */
public class HtmlRelationHandler extends InformationObjectListenerAdapter {

    @Override
    public void onMetadata(InformationMetadataEvent mEvent) {
        mEvent.getMetadata();
    }

    @Override
    public void onMetadataRemoved(InformationMetadataEvent mEvent) {
        super.onMetadataRemoved(mEvent);
    }
}
