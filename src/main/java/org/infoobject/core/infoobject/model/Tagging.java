package org.infoobject.core.infoobject.model;

import org.infoobject.core.agent.Agent;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * <p>
 * Class Tagging ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 14:34:19
 */
public class Tagging implements Comparable<Tagging>{
    private final WeakReference<InformationObject> tagged;
    private final WeakReference<Tag> tag;
    private final WeakHashMap<Agent, TaggingPost> posts = new WeakHashMap<Agent, TaggingPost>();
    /**
     * 
     * @param object
     * @param tag
     */
    public Tagging(InformationObject object, Tag tag) {
        this.tag = new WeakReference<Tag>(tag);
        tagged = new WeakReference<InformationObject>(object);
        tag.addTagging(this);
    }

    public InformationObject getTagged() {
        return tagged.get();
    }

    public Tag getTag() {
        return tag.get();
    }

    public int size() {
        return posts.size();
    }
    public TaggingPost addPost(Agent agent, boolean positive) {
        TaggingPost post = new TaggingPost(getTagged(), agent, getTag(), positive);
        this.posts.put(agent, post);
        return post;
    }

    public int compareTo(Tagging tagging) {
        if (tagging == null) return 1;
        int i = getTagged().getUri().compareTo(tagging.getTagged().getUri());
        if (i == 0) {
            i = getTag().compareTo(tagging.getTag());
        }
        return i;
    }
}
