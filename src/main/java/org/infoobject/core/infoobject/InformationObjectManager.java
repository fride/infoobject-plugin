package org.infoobject.core.infoobject;

import org.infoobject.core.agent.AgentManager;
import org.infoobject.core.infoobject.model.*;
import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.infoobject.to.InformationObjectTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.TaggingTo;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * Class InformationObjectManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 10:39:28
 */
public class InformationObjectManager {
    private final InformationObjectModel model;
    private final InformationObjectRepository repository;
    private WeakHashMap<InformationObject, Timestamp> loadTimes = new WeakHashMap<InformationObject, Timestamp>();
    private WeakHashMap<InformationObject, Timestamp> explode = new WeakHashMap<InformationObject, Timestamp>();
    private AgentManager agentManager;

    /**
     * 
     * @param model the model to store things in
     * @param repository the repository for persistence.
     * @param agentManager used to create agents from ids.
     */
    public InformationObjectManager(InformationObjectModel model, InformationObjectRepository repository, AgentManager agentManager) {
        this.model = model;
        this.repository = repository;
        this.agentManager = agentManager;
    }

    /**
     * Load the informations for the given uri. The loaded information
     * wil be stored in the model.
     * @param uri the uri to load.
     */
    public void load(String uri) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp last = lastExplodeTime(uri);
        if (last == null) {
            final InformationObjectTo to = repository.loadInformations(uri);
            addInformation(now, to);
        }
    }

    /**
     * 
     * @param now
     * @param to
     */
    protected void addInformation(Timestamp now, InformationObjectTo to) {

        /**
         * Make shure an information exsists for the following tatggings on obect links.
         */
        model.add(to.getMetadata() != null ?to.getMetadata() : new InformationMetadataTo(to.getUri()));

        // Now the positions to prevent taggings to not existents information objects.
        if (to.getObjectLinkings() != null) {
            for (ObjectLinkingTo linkingTo : to.getObjectLinkings()) {
                add(linkingTo);
            }
        }
        if (to.getTaggings() != null) {
            for (TaggingTo tagging : to.getTaggings()) {
                add(tagging);
            }
        }

        loadTimes.put(model.getInformationObject(to.getUri()), now);
    }

    private void add(ObjectLinkingTo linkingTo) {
        model.addObjectLink(linkingTo.getUri(), linkingTo.getObject(),
                agentManager.get(linkingTo.getAgentId(), true), linkingTo.getLinkType());
    }

    private void add(TaggingTo tagging) {
        model.addTagging(
                        tagging.getTagged(),
                        Tag.create(tagging.getTag()),
                        agentManager.get(tagging.getTag(),true), tagging.isPositive());
    }

    /**
     *
     * @param uri
     */
    public void explode(String uri) {
        load(uri);
        final InformationObject info = model.getInformationObject(uri);

        if (info != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            final Collection<ObjectLink> objectLinkingTos = info.getObjectLinks();

        }
        //explode.put(info, now);
    }

    public void load(ObjectName name) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        final List<InformationObjectTo> list = repository.findInformationsByObject(name);
        for (InformationObjectTo to : list) {
            addInformation(now, to);
        }
    }

    public void saveTaggings(Iterable<TaggingTo> taggings) {
        repository.saveTaggings(taggings);
        for (TaggingTo tagging : taggings) {
            add(tagging);
        }
    }

    public void saveObjectLinkings(Iterable<ObjectLinkingTo> objectLinkings) {
        repository.saveObjectLinkings(objectLinkings);
        for (ObjectLinkingTo objectLinkingTo : objectLinkings) {
            add(objectLinkingTo);
        }
    }

    /**
     *
     * @param informationMetadatas
     */
    public void saveInformationMetadata(Iterable<InformationMetadataTo> informationMetadatas) {
        repository.saveInformationMetadata(informationMetadatas);
        for (InformationMetadataTo meta : informationMetadatas) {
            model.add(meta);
        }
    }

    public Timestamp lastLoadTime(String uri) {
        final InformationObject informationObject = model.getInformationObject(uri);
        return informationObject != null ? loadTimes.get(informationObject) : null;
    }

    public Timestamp lastExplodeTime(String uri) {
        final InformationObject informationObject = model.getInformationObject(uri);
        return informationObject != null ? explode.get(informationObject) : null;
    }

    public InformationObjectModel getModel() {
        return this.model;
    }

    public void delete(InformationObject informationObject, ObjectName objectName, String agentId) {
        ObjectLinkingTo link = new ObjectLinkingTo(informationObject.getUri(), objectName, "",agentId);
        List<ObjectName> removed = repository.delete(Collections.singleton(link));
        for (ObjectName name : removed ) {
            model.remove(informationObject.getUri(), name);
        }
    }


}
