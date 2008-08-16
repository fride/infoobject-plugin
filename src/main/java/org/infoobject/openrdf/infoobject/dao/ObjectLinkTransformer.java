package org.infoobject.openrdf.infoobject.dao;

import org.openrdf.query.BindingSet;
import org.apache.commons.collections15.Transformer;


import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;

/**
 * <p>
 * Class ObjectLinkTransformer ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 23:30:58
 */
public class ObjectLinkTransformer implements Transformer<BindingSet, ObjectLinkingTo> {

    public ObjectLinkingTo transform(BindingSet binding) {

        ObjectLinkingTo objectLinkTo = new ObjectLinkingTo(
                binding.getValue("info").stringValue(),
                ObjectName.fromUri(binding.getValue("linked_object").stringValue()),
                binding.getValue("link_type").stringValue(),
                binding.getValue("link_creator").stringValue()

        );

        return objectLinkTo;
    }


}
