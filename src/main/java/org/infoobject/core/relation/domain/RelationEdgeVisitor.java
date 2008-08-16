package org.infoobject.core.relation.domain;

/**
 * <p>
 * Class RelationVisitor ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 23:31:36
 */
public interface RelationEdgeVisitor {
    void visit(PositionRelationEdge relation);
    void visit(InformationRelationEdge relation);
}
