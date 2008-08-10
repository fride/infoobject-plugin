package org.infoobject.core.infoobject.model;

import org.infoobject.core.agent.Agent;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * An Objectlink connects an informationobject with a physical entity.
 * An Objectlink has at least one ObjectLinkPosts.
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 14:46:46
 */
public class ObjectLink {
    private WeakReference<InformationObject> informationObject;
    private final ObjectName objectName;
    private Set<ObjectLinkPost> linkPosts = new HashSet<ObjectLinkPost>();

    /**
     * 
     * @param informationObject
     * @param objetname
     */
    public ObjectLink(InformationObject informationObject, ObjectName objetname) {
        this.objectName = objetname;
        this.informationObject = new WeakReference<InformationObject>(informationObject);
    }

    public InformationObject getInformationObject() {
        return informationObject.get();
    }

    public ObjectName getObjectName() {
        return objectName;
    }

    public int size() {
        return this.linkPosts.size();
    }
    public List<ObjectLinkPost> getObjectLinkPostList() {
        return new ArrayList<ObjectLinkPost>(linkPosts);
    }
    
    public ObjectLinkPost add(Agent agent, String type) {
        ObjectLinkPost linkPost = new ObjectLinkPost(this, agent, type);
        if (this.linkPosts.remove(linkPost)) {
            // NEEW...
        }
        this.linkPosts.add(linkPost);
        return linkPost;
    }
}
