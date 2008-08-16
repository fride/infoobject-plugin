package org.infoobject.core.relation.domain;

import java.lang.ref.WeakReference;

/**
 * <p>
 * Class InformationRelation ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 08.08.2008
 *         Time: 23:33:56
 */
public abstract class InformationRelation implements Relation{

    public static interface Factory{
        InformationRelation create(InformationRelationEdge edge);
    }

    private final WeakReference<InformationRelationEdge> relation;
    private double weight = 0;

    
    public InformationRelation(InformationRelationEdge relation) {
        this.relation = new WeakReference<InformationRelationEdge>(relation);
        update();
    }



    public InformationRelationEdge getEdge() {
        return relation.get();
    }

    public boolean isImplicit() {
        return true;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Recalulate
     */
    public final synchronized void update() {
        this.weight = doUpdate();
    }

    protected abstract double doUpdate();

}
