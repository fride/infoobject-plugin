package org.infoobject.core.infoobject.dao;

import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.Tag;
import org.infoobject.core.infoobject.to.MetadataTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.TaggingTo;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:53:28
 */
public abstract class AbstractInformationObjectRepository implements InformationObjectRepository {
    private TaggingDao taggingDao;
    private InformationMetadataDao metadataDao;
    private ObjectLinkDao objectLinkDao;

    /**
     * 
     * @param taggingDao
     * @param metadataDao
     * @param objectLinkDao
     */
    public AbstractInformationObjectRepository(TaggingDao taggingDao, InformationMetadataDao metadataDao, ObjectLinkDao objectLinkDao) {
        this.taggingDao = taggingDao;
        this.metadataDao = metadataDao;
        this.objectLinkDao = objectLinkDao;
    }

    public void saveTaggings(Iterable<TaggingTo> taggings) {
        int saved = 0;
        for (TaggingTo tagging : taggings) {
            this.getTaggingDao().save(tagging);
            saved++;
        }
        if (saved > 0) {
            getTaggingDao().commit();
        }
    }

    public List<TaggingTo> findTaggings(){
        return getTaggingDao().findAll();
    }

    public List<Tag> findTags() {
        return getTaggingDao().findTags();
    }

    /**
     *
     * @param objectLinkings
     */
    public void saveObjectLinkings(Iterable<ObjectLinkingTo> objectLinkings) {
        for (ObjectLinkingTo linkingTo : objectLinkings) {
            getObjectLinkDao().save(linkingTo);
        }
        getObjectLinkDao().commit();
    }

    /**
     * 
     * @param informationMetadatas
     */
    public void saveInformationMetadata(Iterable<MetadataTo> informationMetadatas) {
        for (MetadataTo meta : informationMetadatas){
            getInformationMetadataDao().save(meta);
        }
        getInformationMetadataDao().commit();
    }


    /**
     *
     * @return
     */
    protected TaggingDao getTaggingDao() {
        return taggingDao;
    }

    protected InformationMetadataDao getInformationMetadataDao() {
        return metadataDao;
    }

    protected ObjectLinkDao getObjectLinkDao() {
        return objectLinkDao;
    }

    public List<ObjectName> delete(Iterable<ObjectLinkingTo> objectLinkingTos) {
        List<ObjectName> deleted = new LinkedList<ObjectName>();
        for (ObjectLinkingTo to : objectLinkingTos) {
            if (getObjectLinkDao().delete(to)) {
                deleted.add(to.getObject());
            }
        }
        return deleted;
    }
}
