package org.infoobject.core.infoobject.model;

import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.agent.Agent;

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
public class InformationObject {
    private InformationMetadataTo metadata;
    private Map<ObjectName, ObjectLink> objectLinks = new HashMap<ObjectName, ObjectLink>();
    private Map<Tag, Tagging> taggings = new HashMap<Tag, Tagging>();
     
    private String uri;

    public InformationObject(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public InformationMetadataTo getMetadata() {
        return metadata;
    }

    public boolean setMetadata(InformationMetadataTo metadata) {
        if (this.metadata == null || this.metadata.getVersion() < metadata.getVersion()) {
            this.metadata = metadata;
            return true;
        }
        return false;
    }

    /**
     * 
     * @param tag the tag to use.
     * @param agent the agent who made the post
     * @param positive true if the agent made th tag positive.
     * @return a post.
     */
    public TaggingPost addTaggPost(Tag tag, Agent agent, boolean positive) {
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
        if (!(o instanceof InformationObject)) return false;

        InformationObject that = (InformationObject) o;

        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    public int hashCode() {
        return (uri != null ? uri.hashCode() : 0);
    }
}
