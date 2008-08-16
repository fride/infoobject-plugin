package org.infoobject.core.infoobject.model;

import org.bushe.swing.event.EventBus;
import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.infoobject.event.Events;
import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import org.infoobject.core.infoobject.event.TaggingEvent;
import org.infoobject.core.infoobject.event.InformationObjectListener;
import org.infoobject.core.agent.Agent;

import java.util.*;

/**
 * <p>
 * Class InformationObjectModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:31:50
 */
public class InformationObjectModel {
    private Map<String, InformationObject> informations = new HashMap<String, InformationObject>();
    private List<InformationObjectListener> listeners = Collections.synchronizedList(new LinkedList<InformationObjectListener>());

    public InformationObject getInformationObject(String uri) {
        return informations.get(uri);
    }


    /**
     *  Adds a tagging to the model. if a tagging with the same agent, tag and uri
     * exists it will be overwritten.
     * 
     * @param uri
     * @param tag
     * @param agent
     * @param positive
     */
    public TaggingPost addTagging(final String uri, Tag tag, Agent agent, boolean positive){
        InformationObject informationObject = informations.get(uri);
        if (informationObject == null){
            informationObject = new InformationObject(uri);
            informations.put(uri, informationObject);
        }
        TaggingPost post = informationObject.addTaggPost(tag, agent, positive);
        fireTagging(post);
        return post;

    }

    /**
     * 
     * @param uri
     * @param name
     * @param agent
     * @param type
     */
    public ObjectLinkPost addObjectLink(final String uri, ObjectName name, Agent agent, String type) {
        InformationObject informationObject = informations.get(uri);
        if (informationObject == null){
            informationObject = new InformationObject(uri);
            informations.put(uri, informationObject);
        }
        ObjectLinkPost objectLinkPost = informationObject.addObjectLink(name, agent, type);
        fireObjectLinking(objectLinkPost);
        return objectLinkPost;
    }

    public void add(InformationMetadataTo meta) {
        final String uri = meta.getUri();
        if (!informations.containsKey(uri)){
            informations.put(uri, new InformationObject(uri));
        }
        if (informations.get(uri).setMetadata(meta)) {
            fireMeta(meta);
        }
    }

    public void addInformationObjectListener(InformationObjectListener l){
        this.listeners.add(l);
    }

    public void removeInformationObjectListener(InformationObjectListener l) {
        this.listeners.remove(l);
    }
    public InformationObjectListener[] getInformationObjectListener() {
        return this.listeners.toArray(new InformationObjectListener[listeners.size()]);
    }
    
    protected void fireMeta(InformationMetadataTo meta) {
        EventBus.publish(Events.META_INFORMAION_ADDED,meta);
    }

    protected void fireTagging(TaggingPost tagging) {
        TaggingEvent event = new TaggingEvent(this, tagging);
        for (InformationObjectListener l : getInformationObjectListener()) {
            l.onTagging(event);
        }
    }

    /**
     * 
     * @param linkingTo
     */
    protected void fireObjectLinking(ObjectLinkPost linkingTo) {
        ObjectLinkingEvent event = new ObjectLinkingEvent(this, linkingTo);
        for (InformationObjectListener l:getInformationObjectListener()) {
            l.onObjectLinking(event);
        }
    }

    /**
     * 
     * @param uri
     * @param name
     */
    public void remove(String uri, ObjectName name) {
        final InformationObject informationObject = informations.get(uri);
        if (informationObject != null && informationObject.remove(name)){
            fireObjectLinkingRemoved(informationObject, name);
        }
    }

    protected void fireObjectLinkingRemoved(InformationObject informationObject, ObjectName name) {
        ObjectLinkingEvent event = new ObjectLinkingEvent(this, new ObjectLinkPost(new ObjectLink(informationObject, name),null,null));
        for (InformationObjectListener l:getInformationObjectListener()) {
            l.onObjectLinkingRemoved(event);
        }
    }
}
