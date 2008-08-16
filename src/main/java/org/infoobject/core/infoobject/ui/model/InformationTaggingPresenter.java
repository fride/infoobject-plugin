package org.infoobject.core.infoobject.ui.model;

import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.infoobject.ui.forms.InformationTaggingForm;

/**
 * <p>
 * Class InformationTagginPresenter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 10:39:46
 */
public class InformationTaggingPresenter extends AbstractModel {
    private InformationTaggingForm form;

    public InformationTaggingForm getForm() {
        if (form == null) {
            form = new InformationTaggingForm();
        }
        return form;
    }
}
