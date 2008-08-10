package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.to.InformationMetadataTo;

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

    void handleInformationMetadatAdded(InformationMetadataTo data);
    void handleInformationMetadatChanged(InformationMetadataTo data);
    void handleInformationMetadatRemoved(InformationMetadataTo data);
}
