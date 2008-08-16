package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.to.MetadataTo;

/**
 * <p>
 * Class InformationMetadataHandler ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:48:32
 */
public interface InformationMetadataHandler {

    void handleInformationMetadatAdded(MetadataTo data);
    void handleInformationMetadatChanged(MetadataTo data);
    void handleInformationMetadatRemoved(MetadataTo data);
}
