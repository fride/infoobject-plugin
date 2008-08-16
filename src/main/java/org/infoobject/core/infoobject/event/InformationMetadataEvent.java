package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.domain.support.DefaultInformationObjectModel;
import org.infoobject.core.infoobject.domain.InformationObject;

import java.util.EventObject;

/**
 * <p>
 * Class InformationMetadataEvent ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 01:40:45
 */
public class InformationMetadataEvent extends EventObject {
    private final InformationObject metadata;

    public InformationMetadataEvent(DefaultInformationObjectModel objectModel, InformationObject metadata) {
        super(objectModel);
        this.metadata = metadata;
    }

    public InformationObject getMetadata() {
        return metadata;
    }

    @Override
    public DefaultInformationObjectModel getSource() {
        return (DefaultInformationObjectModel) super.getSource();
    }
}
