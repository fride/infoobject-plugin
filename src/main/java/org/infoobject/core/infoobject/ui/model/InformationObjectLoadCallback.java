package org.infoobject.core.infoobject.ui.model;

import org.infoobject.core.infoobject.domain.InformationObject;

/**
 * <p>
 * Class InformationObjectLoadCallback ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 20:09:25
 */
public interface InformationObjectLoadCallback {
    
    void onInformationObjectLoaded(InformationObject loaded);
}
