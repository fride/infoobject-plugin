package org.infoobject.core.infoobject.model;

import org.infoobject.core.agent.Agent;

import java.lang.ref.WeakReference;

/**
 * <p>
 * n Object linking is a post made by an agent.
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 14:48:23
 */
public class ObjectLinkPost {
    private final WeakReference<ObjectLink> objectLink;
    private final WeakReference<Agent> agent;
    private final String type;

    /**
     * 
     * @param link
     * @param agent
     * @param type
     */
    ObjectLinkPost(ObjectLink link, Agent agent, String type) {
        this.objectLink = new WeakReference<ObjectLink>(link);
        this.agent = new WeakReference<Agent>(agent);
        this.type = type;
    }

    public ObjectLink getObjectLink() {
        return objectLink.get();
    }

    public Agent getAgent() {
        return agent.get();
    }

    public String getType() {
        return type;
    }
}
