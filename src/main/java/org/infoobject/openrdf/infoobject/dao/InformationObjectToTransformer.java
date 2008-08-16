package org.infoobject.openrdf.infoobject.dao;

import org.openrdf.query.BindingSet;
import org.openrdf.model.Literal;
import org.apache.commons.collections15.Transformer;

import java.util.Map;
import java.util.HashMap;

import org.infoobject.core.infoobject.to.InformationObjectTo;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.to.MetadataTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;

/**
 * <p>
 * Class InformationObject2Transformer ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 23:33:19
 */
public class InformationObjectToTransformer implements Transformer<BindingSet, InformationObjectTo> {
    final TaggingTransformer taggingTransformer = new TaggingTransformer();
    final ObjectLinkTransformer objectLinkTransformer = new ObjectLinkTransformer();
    final Map<String, InformationObjectTo> infoMap = new HashMap<String, InformationObjectTo>();


    public InformationObjectToTransformer() {
    }

    public InformationObjectTo transform(BindingSet bindingSet) {
        final String infoUri = bindingSet.getValue("info").stringValue();
        InformationObjectTo info = infoMap.get(infoUri);
        boolean newInfo = info == null;
        if (newInfo){
            info = new InformationObjectTo(infoUri);
            info.setMetadata(new MetadataTo(infoUri));
            if (bindingSet.hasBinding("info_title")){
                info.getMetadata().setTitle(bindingSet.getValue("info_title").stringValue());
            }
            if (bindingSet.hasBinding("info_size")){
                info.getMetadata().setSize(((Literal)bindingSet.getValue("info_size")).longValue());
            }
            if (bindingSet.hasBinding("mime")){
                info.getMetadata().setMimeType(bindingSet.getValue("mime").stringValue());
            }


            infoMap.put(infoUri,info);

        }

        final TaggingTo tagging = taggingTransformer.transform(bindingSet);
        if (tagging != null){
            info.getTaggings().add(tagging);
        }
        final ObjectLinkingTo objectLinkTo = objectLinkTransformer.transform(bindingSet);
        if (objectLinkTo != null) {
            info.getObjectLinkings().add(objectLinkTo);
        }
        return newInfo ? info : null;
    }

}
