package org.infoobject.core.infoobject.model;

import org.infoobject.core.infoobject.dao.TaggingDao;
import org.infoobject.core.infoobject.dao.InformationMetadataDao;
import org.infoobject.core.infoobject.dao.ObjectLinkDao;
import org.infoobject.core.infoobject.to.InformationObjectTo;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.InformationMetadataTo;

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
public abstract class InformationObjectRepository {
    private TaggingDao taggingDao;
    private InformationMetadataDao metadataDao;
    private ObjectLinkDao objectLinkDao;

    /**
     * 
     * @param taggingDao
     * @param metadataDao
     * @param objectLinkDao
     */
    public InformationObjectRepository(TaggingDao taggingDao, InformationMetadataDao metadataDao, ObjectLinkDao objectLinkDao) {
        this.taggingDao = taggingDao;
        this.metadataDao = metadataDao;
        this.objectLinkDao = objectLinkDao;
    }

    /**
     *
     * @param name
     * @return
     */
    public abstract List<InformationObjectTo> findInformationsByObject(ObjectName name);

    public void saveTaggings(Iterable<TaggingTo> taggings) {
        for (TaggingTo tagging : taggings) {
            this.getTaggingDao().save(tagging);
        }
        getTaggingDao().commit();
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
    public void saveInformationMetadata(Iterable<InformationMetadataTo> informationMetadatas) {
        for (InformationMetadataTo meta : informationMetadatas){
            getInformationMetadataDao().save(meta);
        }
        getInformationMetadataDao().commit();
    }

    public abstract InformationObjectTo loadInformations(final String uri);
  


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
}
