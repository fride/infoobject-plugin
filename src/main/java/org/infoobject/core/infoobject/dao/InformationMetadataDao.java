package org.infoobject.core.infoobject.dao;

import org.infoobject.core.infoobject.to.MetadataTo;

import java.util.List;

/**
 * <p>
 * Class InformationDataDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:31:10
 */
public interface InformationMetadataDao {

    void save (MetadataTo data);
    /**
     *
     * @param uri
     * @return
     */
    MetadataTo load(String uri);

    /**
     *
     * @return
     */
    List<MetadataTo> findAll();

    void commit();
}
