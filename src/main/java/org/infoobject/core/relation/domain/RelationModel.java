package org.infoobject.core.relation.domain;

import org.infoobject.core.infoobject.domain.InformationObject;

import java.util.Set;

/**
 * <p>
 * 
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 14:13:29
 */
public interface RelationModel {

     /**
     *
     * @param information
     * @return
     */
    Set<? extends RelationEdge> findRelations(InformationObject information);
}
