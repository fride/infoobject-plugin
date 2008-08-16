package org.infoobject.core.infoobject.event;

import org.infoobject.core.infoobject.model.TaggingPost;

/**
 * <p>
 * Class TaggingListener ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:46:20
 */
public interface TaggingHandler {

    void handleTaggingAdded(TaggingPost tagging);
    void handleTaggingRemoved(TaggingPost tagging);

}
