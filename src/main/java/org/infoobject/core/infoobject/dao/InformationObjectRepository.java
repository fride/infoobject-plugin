package org.infoobject.core.infoobject.dao;

import org.infoobject.core.infoobject.to.InformationObjectTo;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.MetadataTo;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.Tag;

import java.util.List;

/**
 * <p>
 * Class InformationObjectRepository ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 15:46:20
 */
public interface InformationObjectRepository {
    /**
     *
     * @param name
     * @return
     */
    List<InformationObjectTo> findInformationsByObject(ObjectName name);

    void saveTaggings(Iterable<TaggingTo> taggings);

    List<TaggingTo> findTaggings();

    List<Tag> findTags();

    void saveObjectLinkings(Iterable<ObjectLinkingTo> objectLinkings);

    void saveInformationMetadata(Iterable<MetadataTo> informationMetadatas);

    InformationObjectTo loadInformations(String uri);

    List<ObjectName> delete(Iterable<ObjectLinkingTo> objectLinkingTos);
}
