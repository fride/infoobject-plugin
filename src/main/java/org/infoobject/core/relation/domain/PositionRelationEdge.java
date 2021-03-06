package org.infoobject.core.relation.domain;

import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;

/**
 * <p>
 * Class ObjectRelationEdge ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 12:49:41
 */
public class PositionRelationEdge extends RelationEdge<Node, InformationObjectNode, PositionRelation> {
    
    public PositionRelationEdge(Node source, InformationObjectNode target, InformationObjectNodeGraph graph) {
        super(source, target, graph);
        if (!source.isPhysical()) throw new IllegalArgumentException("Source must be physical!");
        if (target.isPhysical()) throw new IllegalArgumentException("Target must not be physical!");
    }

    /**
     * 
     * @return
     */
    public double getEstimatedLength() {
        return 10.0;
    }

    public void accept(RelationEdgeVisitor v) {
        v.visit(this);
    }
}
