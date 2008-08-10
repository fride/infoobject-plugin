package org.infoobject.core.relation;

import org.infoobject.core.infoobject.model.InformationRelation;

import java.util.List;

/**
 * <p>
 * Class RelationModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:48:00
 */
public interface RelationSource<T extends InformationRelation> {

    /**
     * 
     * @param interceptor
     */
    void setRelationInterceptor(RelationInterceptor<T> interceptor);
    
    List<T> getRelations();

    List<T> getRelation(String uri);

    List<T> getRelation(String uri1, String uri2);
}
