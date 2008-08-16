package org.infoobject.core.infoobject.domain.support;

import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.infoobject.domain.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * Class InformationObject ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:07:39
 */
public class DefaultInformationObject implements org.infoobject.core.infoobject.domain.InformationObject {
    private Map<ObjectName, ObjectLink> objectLinks = new HashMap<ObjectName, ObjectLink>();
    private Map<Tag, Tagging> taggings = new HashMap<Tag, Tagging>();
    private Metadata metadata;

    private String uri;

    public DefaultInformationObject(String uri) {
        this.uri = uri;
        this.metadata =  new Metadata(uri);
    }

    public String getUri() {
        return uri;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public boolean setMetadata(Metadata metadata) {
        final Timestamp date = metadata.getCrawlDate();
        final Timestamp myDate = this.metadata.getCrawlDate();
        if (myDate.before(date)) {
            this.metadata.clear();
            this.metadata.addAll(metadata);
        }
        return myDate.before(date);
    }

    /**
     * 
     * @param tag the tag to use.
     * @param agent the agent who made the post
     * @param positive true if the agent made th tag positive.
     * @return a post.
     */
    public TaggingPost addTaggPost(Tag tag, Agent
        agent, boolean positive) {
        Tagging tagging = taggings.get(tag);
        if (tagging == null) {
            taggings.put(tag, new Tagging(this, tag));
        }
        return this.taggings.get(tag).addPost(agent, positive);
    }

    /**
     * 
     * @return a list of links ....
     */
    public List<ObjectLink> getObjectLinks(){      
        return (objectLinks.size() > 0 ) ?new ArrayList<ObjectLink>(objectLinks.values()) : Collections.<ObjectLink>emptyList();

    }
    /**
     * 
     * @param name
     * @param agent
     * @param type
     * @return
     */
    public ObjectLinkPost addObjectLink(ObjectName name, Agent agent, String type) {
        ObjectLink link = objectLinks.get(name);
        if (link == null) {
            link = new ObjectLink(this, name);
            this.objectLinks.put(name, link);
        }
        return link.add(agent, type);
    }


    public ArrayList<ObjectName> getObjectNames() {
        return new ArrayList<ObjectName>(objectLinks.keySet());
    }
    public ArrayList<Tag> getTags() {
        return new ArrayList<Tag>(taggings.keySet());
    }
    public List<Tagging> getTaggings() {
        return new ArrayList<Tagging>(taggings.values());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof org.infoobject.core.infoobject.domain.InformationObject)) return false;

        DefaultInformationObject that = (DefaultInformationObject) o;

        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    public int hashCode() {
        return (uri != null ? uri.hashCode() : 0);
    }


    boolean remove(ObjectName name) {
        return null != this.objectLinks.remove(name);
    }
}
