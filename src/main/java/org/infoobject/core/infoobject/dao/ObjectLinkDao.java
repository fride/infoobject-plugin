package org.infoobject.core.infoobject.dao;

import org.infoobject.core.infoobject.to.ObjectLinkingTo;

/**
 * <p>
 * Class ObjectLinkDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:23:37
 */
public interface ObjectLinkDao {
    
    void save(ObjectLinkingTo linkingTo);

    
    void commit();

    /**
     * 
     * @param to
     * @return
     */
    boolean delete(ObjectLinkingTo to);
}
