package org.infoobject.magicmap.infoobject.ui.util;

import ca.odell.glazedlists.matchers.Matcher;
import org.apache.commons.collections15.CollectionUtils;
import org.infoobject.core.infoobject.domain.Tag;
import org.infoobject.core.infoobject.domain.InformationObject;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Class TagMatcher ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 12.08.2008
 *         Time: 22:36:46
 */
public class TagInformationObjectMatcher implements Matcher<InformationObject> {
    private final Set<Tag> tags = new HashSet<Tag>();

    public TagInformationObjectMatcher(Iterable<Tag> tags) {
        for (Tag tag : tags) {
            this.tags.add(tag);
        }
    }

    public boolean matches(InformationObject informationObject) {
        return CollectionUtils.intersection(informationObject.getTags(), tags).size() > 0;
    }
}
