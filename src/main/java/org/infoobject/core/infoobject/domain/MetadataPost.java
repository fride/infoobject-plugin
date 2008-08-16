package org.infoobject.core.infoobject.domain;

import org.infoobject.core.agent.domain.Agent;

import java.lang.ref.WeakReference;

/**
 * <p>
 * Class MetadataPost ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 15:05:21
 */
public class MetadataPost {
    private WeakReference<Agent> agent;
    private Metadata metadata;
    private final WeakReference<InformationObject> info;

    public MetadataPost(InformationObject info, Agent agent, Metadata metadata) {
        if (!info.getUri().equals(metadata.getSubject().stringValue())) {
            throw new IllegalArgumentException("Metadatas url and infos dont match");
        }
        this.agent = new WeakReference<Agent>(agent);
        this.metadata = metadata;
        this.info = new WeakReference<InformationObject>(info);
    }

    public Agent getAgent() {
        return agent.get();
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public InformationObject getInformationObject() {
        return info.get();
    }
}
