package org.infoobject.core.infoobject.application;

import net.sf.magicmap.client.utils.AbstractModel;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.Closure;
import org.infoobject.core.agent.application.AgentManager;
import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.infoobject.dao.InformationObjectRepository;
import org.infoobject.core.infoobject.domain.*;
import org.infoobject.core.infoobject.to.InformationObjectTo;
import org.infoobject.core.infoobject.to.MetadataTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.TaggingTo;

import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

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
public class InformationObjectManager extends AbstractModel {
    private final InformationObjectModel model;
    private final InformationObjectRepository repository;
    private WeakHashMap<InformationObject, Timestamp> loadTimes = new WeakHashMap<InformationObject, Timestamp>();
    private WeakHashMap<InformationObject, Timestamp> explode = new WeakHashMap<InformationObject, Timestamp>();
    private AgentManager agentManager;
    private Agent agent;

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
        this.agent = Agent.ANONYMOUS;
    }

    public void setAgent(Agent agent) {
        if (this.agent ==  null || this.agent.equals(agent)) {
            Agent old = this.agent;
            this.agent = agent;
            firePropertyChange("agent", old,agent);
        }
    }

    public Agent getAgent() {
        return agent;
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
        model.add(to.getMetadata() != null ?to.getMetadata() : new MetadataTo(to.getUri()));

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

    /**
     * 
     * @param tagging
     */
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

    /**
     * 
     * @param taggings
     * @param uri
     */
    public void saveTaggings(Map<String, Boolean> taggings, final String uri) {
        List<TaggingTo>tos = new ArrayList<TaggingTo>(taggings.size());
        CollectionUtils.collect(taggings.entrySet(), new Transformer<Entry<String, Boolean>, TaggingTo>() {
            public TaggingTo transform(Entry<String, Boolean> entry) {
                return new TaggingTo(uri, entry.getKey(), entry.getValue(), getAgent().getId());
            }
        },tos);
        repository.saveTaggings(tos);
        for (TaggingTo tagging : tos) {
            add(tagging);
        }
    }

    public void saveObjectLinkings(Map<ObjectName, String> objectLinkings, final String uri) {
        final List<ObjectLinkingTo>tos = new ArrayList<ObjectLinkingTo>(objectLinkings.size());
        CollectionUtils.forAllDo(objectLinkings.entrySet(), new Closure<Entry<ObjectName, String>>() {
            public void execute(Entry<ObjectName, String> entry) {
               tos.add(new ObjectLinkingTo(uri, entry.getKey(), entry.getValue(), getAgent().getId()));
            }
        });
        repository.saveObjectLinkings(tos);
        for (ObjectLinkingTo objectLinkingTo : tos) {
            add(objectLinkingTo);
        }
    }

    /**
     *
     * @param metadatas
     */
    public void saveInformationMetadata(List<Metadata> metadatas) {
        final List<MetadataTo> tos = new ArrayList<MetadataTo>(metadatas.size());
        CollectionUtils.forAllDo(metadatas, new Closure<Metadata>() {
            public void execute(Metadata metadata) {
                tos.add(new MetadataTo(metadata, getAgent().getId(), metadata.getCrawlDate()));
            }
        });

        repository.saveInformationMetadata(tos);
        for (MetadataTo meta : tos) {
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


    public void delete(InformationObject informationObject, ObjectName objectName, String agentId) {
        ObjectLinkingTo link = new ObjectLinkingTo(informationObject.getUri(), objectName, "",agentId);
        List<ObjectName> removed = repository.delete(Collections.singleton(link));
        for (ObjectName name : removed ) {
            model.remove(informationObject.getUri(), name);
        }
    }



}
