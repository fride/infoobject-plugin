package org.infoobject.core.infoobject.domain;

import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObject;

import java.lang.ref.WeakReference;

/**
 * <p>
 * Class TaggingPost ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 15:07:15
 */
public class TaggingPost {
    private WeakReference<DefaultInformationObject> informationObject;
    private WeakReference<Agent> agent;
    private final WeakReference<Tag> tag;
    private final boolean positive;

    public TaggingPost(DefaultInformationObject object, Agent agent, Tag tag, boolean positive) {
        this.positive = positive;
        this.informationObject = new WeakReference<DefaultInformationObject>(object);
        this.agent = new WeakReference<Agent>(agent);
        this.tag = new WeakReference<Tag>(tag);
    }

    public org.infoobject.core.infoobject.domain.InformationObject getInformationObject() {
        return informationObject.get();
    }

    public Agent getAgent() {
        return agent.get();
    }

    public Tag getTag() {
        return tag.get();
    }

    public boolean isPositive() {
        return positive;
    }
}
