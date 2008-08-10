package org.infoobject.core.tag;

import java.util.*;

import org.infoobject.core.infoobject.model.Tag;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.relation.RelationSource;
import org.infoobject.core.relation.RelationInterceptor;
import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

/**
 * <p>
 * Class TagModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:51:00
 */
public class TagModel implements RelationSource {



    //private SortedSet<TaggingTo> taggings = new TreeSet<TaggingTo>(taggingComparator);
    
    private Set<Tag> tags = new HashSet<Tag>();
    private Map<Tag, Integer> tagCount = new HashMap<Tag, Integer>();
    private MultiMap<String, TaggingTo> tagsByUri = new MultiHashMap<String, TaggingTo>();
    
    private Set<TaggingRelation> taggingRelations = new HashSet<TaggingRelation>();
    private RelationInterceptor relationInterceptor;

    public void add(TaggingTo tagging) {

      
    }


    /**
     * @param interceptor
     */
    public void setRelationInterceptor(RelationInterceptor relationInterceptor) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.relationInterceptor = relationInterceptor;
    }

    public List getRelations() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List getRelation(String uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List getRelation(String uri1, String uri2) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
