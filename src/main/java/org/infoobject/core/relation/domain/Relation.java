package org.infoobject.core.relation.domain;

/**
 * <p>
 * Class Relation ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 08.08.2008
 *         Time: 23:30:50
 */
public interface Relation {
    
    RelationEdge getEdge();

    boolean isImplicit();

    double getWeight();
    
    /**
     * Recalulate
     */
    void update();
}
