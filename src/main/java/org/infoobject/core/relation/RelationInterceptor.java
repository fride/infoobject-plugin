package org.infoobject.core.relation;

import org.infoobject.core.infoobject.model.InformationRelation;

/**
 * <p>
 * Class RelationListener ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 14:04:18
 */
public interface RelationInterceptor<T extends InformationRelation> {

    void relationChanged(T relation);

    void relationAdded(T relation);

    void relationRemoved(T relation);
}
