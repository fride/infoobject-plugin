package org.infoobject.openrdf.infoobject.dao;

import org.openrdf.query.BindingSet;
import org.openrdf.model.Literal;

import org.apache.commons.collections15.Transformer;
import org.infoobject.core.infoobject.to.TaggingTo;

/**
 * <p>
 * Class TaggingTransformer ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 23:29:03
 */
public class TaggingTransformer implements Transformer<BindingSet, TaggingTo> {


    public TaggingTo transform(BindingSet binding) {
        TaggingTo tagging = null;
        if (binding.hasBinding("tagged")){
            String tagged = binding.getBinding("tagged").getValue().stringValue();
            String agentid = binding.getBinding("tag_creator").getValue().stringValue();
            String tag = binding.getBinding("raw").getValue().stringValue();
            boolean positive = ((Literal)binding.getBinding("positive").getValue()).booleanValue();

            tagging = new TaggingTo(tagged, tag, positive, agentid);
        }
        return tagging;
    }
}
