package org.infoobject.core.infoobject.to;

import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.rdf.RdfContainer;
import org.infoobject.core.rdf.vocabulary.DC;
import org.infoobject.core.rdf.vocabulary.InformationObjectVoc;
import org.openrdf.model.impl.GraphImpl;

import java.sql.Timestamp;

/**
 * <p>
 * Class InformationData ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 01:23:14
 */
public class MetadataTo {
    private final RdfContainer metadata;
    private final String agentId;
    private final Timestamp crawlDate;

    public MetadataTo(String uri) {
        this(new RdfContainer(new GraphImpl(), uri), Agent.ANONYMOUS.getId(), new Timestamp(System.currentTimeMillis()));
    }
    public MetadataTo(RdfContainer metadata, String agentId, Timestamp crawlDate) {
        this.metadata = metadata;
        this.agentId = agentId;
        this.crawlDate = crawlDate;
    }

    public RdfContainer getMetadata() {
        return metadata;
    }

    public String getAgentId() {
        return agentId;
    }

    public Timestamp getCrawlDate() {
        return crawlDate;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetadataTo)) return false;

        MetadataTo that = (MetadataTo) o;

        if (agentId != null ? !agentId.equals(that.agentId) : that.agentId != null) return false;
        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        return result;
    }

    public void setTitle(String title) {
        metadata.setUniqueProperty(DC.Title, title);
    }

    public void setSize(long size) {
        metadata.setUniqueProperty(InformationObjectVoc.size, size);
    }

    public void setMimeType(String type) {
        metadata.setUniqueProperty(DC.Format, type);
    }

    public void setEncoding(String encoding) {
        // TODO Needed?
    }

    public String getUri() {
        return metadata.getSubject().toString();
    }
}
