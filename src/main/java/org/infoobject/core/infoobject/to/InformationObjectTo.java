package org.infoobject.core.infoobject.to;

import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.MetadataTo;

import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 * Class InformationObjectTo ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 01:10:31
 */
public class InformationObjectTo {
    private String uri;
    private MetadataTo metadata;
    private Set<TaggingTo> taggings = new HashSet<TaggingTo>();
    private Set<ObjectLinkingTo> objectLinkingTos = new HashSet<ObjectLinkingTo>();

    public InformationObjectTo(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public Set<TaggingTo> getTaggings() {
        return taggings;
    }

    public Set<ObjectLinkingTo> getObjectLinkings() {
        return objectLinkingTos;
    }

    public MetadataTo getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataTo metadata) {
        this.metadata = metadata;
    }
}
