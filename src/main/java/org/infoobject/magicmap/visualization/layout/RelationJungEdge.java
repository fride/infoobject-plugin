package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import org.infoobject.core.relation.domain.InformationRelationEdge;
import org.infoobject.core.relation.domain.RelationEdge;
import org.infoobject.core.relation.domain.PositionRelationEdge;

import java.lang.ref.WeakReference;

/**
 * <p>
 * Class RelationEdge ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 23:26:10
 */
public class RelationJungEdge extends UndirectedSparseEdge {

    final WeakReference<RelationEdge> edge;
    public RelationJungEdge(PhysicalVertex vertex, InformationVertex vertex1, PositionRelationEdge edge) {
        super(vertex, vertex1);
        this.edge = new WeakReference<RelationEdge>(edge);
    }
    
    public RelationJungEdge(InformationVertex vertex, InformationVertex vertex1, InformationRelationEdge edge) {
        super(vertex, vertex1);
        this.edge = new WeakReference<RelationEdge>(edge);
    }
    public RelationEdge getRelationEdge(){
        return edge.get();
    }
}
