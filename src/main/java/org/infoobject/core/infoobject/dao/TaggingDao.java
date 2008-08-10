package org.infoobject.core.infoobject.dao;

import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.model.Tag;

import java.util.List;

/**
 * <p>
 * Class TaggingDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:11:56
 */
public interface TaggingDao {

    void save (TaggingTo data);
    
    
    /**
     * 
     * @return
     */
    List<TaggingTo> findAll();

    /**
     * 
     */
    void commit();

    /**
     * 
     * @return
     */
    List<Tag> findTags();
}
