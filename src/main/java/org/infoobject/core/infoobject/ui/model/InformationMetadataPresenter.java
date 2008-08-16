package org.infoobject.core.infoobject.ui.model;

import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.infoobject.domain.InformationObject;
import org.infoobject.core.infoobject.ui.forms.InformationMetadataForm;

/**
 * <p>
 * Class InformationMetaPresenter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:11:06
 */
public class InformationMetadataPresenter extends AbstractModel {
    private InformationObject object;
    private InformationMetadataForm from;


    public InformationMetadataForm getFrom() {
        if (from == null) {
            from = new InformationMetadataForm();
        }
        return from;
    }

    public InformationObject getObject() {
        return object;
    }

    public void setObject(InformationObject object) {
        if (!this.object.equals(object)) {
            this.object = object;
            firePropertyChange("object", null,object);
            getFrom().setTitle(object.getMetadata().getTitle());
            getFrom().setMimeType(object.getMetadata().getMimeType());
        }
    }
}
