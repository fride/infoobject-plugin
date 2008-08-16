package org.infoobject.core.relation.domain;

import org.infoobject.core.infoobject.domain.*;

import java.util.Set;
import java.util.HashSet;
import java.lang.ref.WeakReference;

/**
 * <p>
 * Class ObjectRelation ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 08.08.2008
 *         Time: 23:33:10
 */
public class PositionRelation implements Relation{

    public static interface Factory{
        PositionRelation create(PositionRelationEdge edge);
    }
    
    private WeakReference<ObjectLink> link;
    private ObjectName object;
    private InformationObject information;
    private final WeakReference<RelationEdge> edge;
    private final static Set<String> ignoredUsers = new HashSet<String>();
    private final static Set<String> ignoredType = new HashSet<String>();
    private double weight = 1;
    
    /**
     * 
     */
    public PositionRelation(ObjectLink link, RelationEdge edge) {
        this.edge = new WeakReference<RelationEdge>(edge);
        this.link = new WeakReference<ObjectLink>(link);
        this.information = link.getInformationObject();
    }




    public RelationEdge getEdge() {
        return edge.get();
    }

    public boolean isImplicit() {
        return false;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Recalulate
     */
    public void update() {
        boolean ignored = false;
        for (ObjectLinkPost post : link.get().getObjectLinkPostList()){
            ignored |= ignore(post);

        }
        weight = ignored ? 0.0 : 1.0;
    }
    private boolean ignore(ObjectLinkPost post){
        return ignoredUsers.contains(post.getAgent().getId()) || ignoredType.contains(post.getType());
    }
}
