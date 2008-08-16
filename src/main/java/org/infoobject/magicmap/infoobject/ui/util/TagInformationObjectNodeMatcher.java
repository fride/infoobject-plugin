package org.infoobject.magicmap.infoobject.ui.util;

import ca.odell.glazedlists.matchers.Matcher;
import org.apache.commons.collections15.CollectionUtils;
import org.infoobject.core.infoobject.model.InformationObjectNode;
import org.infoobject.core.infoobject.model.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Class TagInformationObjectNodeMatcher ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 12.08.2008
 *         Time: 22:38:41
 */
public class TagInformationObjectNodeMatcher implements Matcher<InformationObjectNode> {
    private final Set<Tag> tags = new HashSet<Tag>();

    public TagInformationObjectNodeMatcher(Iterable<Tag> tags) {
        for (Tag tag : tags) {
            this.tags.add(tag);
        }
    }

    public boolean matches(InformationObjectNode node) {
        return CollectionUtils.intersection(node.getInformationObject().getTags(), tags).size() > 0;
    }
}
