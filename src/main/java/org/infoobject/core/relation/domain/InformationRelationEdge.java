package org.infoobject.core.relation.domain;

import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;

/**
 * <p>
 * Class InformationRelationEdge ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 12:49:33
 */
public class InformationRelationEdge extends RelationEdge<InformationObjectNode, InformationObjectNode, InformationRelation> {

    public InformationRelationEdge(InformationObjectNode source, InformationObjectNode target, InformationObjectNodeGraph graph) {
        super(source, target, graph);
    }

    public double getEstimatedLength() {
        double weight  = 0;
        int count = super.size();
        if (count == 0) return 0;
        for (InformationRelation relation : this) {
            weight += relation.getWeight();
        }
        return weight / count;
    }

    public void accept(RelationEdgeVisitor v) {
        v.visit(this);
    }

}
