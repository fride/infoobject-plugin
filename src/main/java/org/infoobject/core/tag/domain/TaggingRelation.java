package org.infoobject.core.tag.domain;

import org.infoobject.core.infoobject.domain.*;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.core.relation.domain.InformationRelationEdge;
import org.infoobject.core.relation.domain.InformationRelation;
import org.apache.commons.collections15.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * Class TaggingRelation ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:50:41
 */
public class TaggingRelation extends InformationRelation {


    public TaggingRelation(InformationRelationEdge relation) {
        super(relation);
    }

    /**
     * Recalulate
     */
    protected  double doUpdate() {

        double  w = 0;
        InformationObjectNode source = getEdge().getSourceNode();
        InformationObjectNode target = getEdge().getTargetNode();

        ArrayList<Tag> st = source.getInformationObject().getTags();
        ArrayList<Tag> tt = target.getInformationObject().getTags();
        Collection<Tag> both = CollectionUtils.intersection(st, tt);
        if (st.size() == 0 && tt.size() == 0) {
            w = 0;
        }
        else {
            w = (double)both.size() / (st.size() + tt.size() - tt.size());
        }
        return w;
    }
}
