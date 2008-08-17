package org.infoobject.magicmap.node.model;

import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.infoobject.domain.*;
import org.infoobject.core.util.Digest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class InformationObjectNode ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 17:59:01
 */
public class InformationObjectNode extends Node implements InformationObject {

    public static final int NODE_TYPE =-6762;
    private final InformationObject information;

    public InformationObjectNode(InformationObjectNodeModel iNodeModel, InformationObject information) {
        super(iNodeModel);
        this.information = information;
        setName(createInformationObjectId(information.getUri()));

    }

    @Override
    public String getDisplayName() {
        if (information.getMetadata() != null && information.getMetadata().getTitle() != null) {
            return information.getMetadata().getTitle();
        } else {
            return information.getUri();
        }
    }

    /**
     * 
     * @return
     */
    public InformationObject getInformationObject() {
        return this;
    }

    public ArrayList<? extends Node> getNeighbors() {
        return new ArrayList<Node>();
    }

    @Override
    public boolean isPhysical() {
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformationObject)) return false;

        InformationObject that = (InformationObject) o;

        if (getUri() != null ? !getUri().equals(that.getUri()) : that.getUri() != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (information != null ? information.hashCode() : 0);
        return result;
    }

    /**
     * 
     * @return
     */
    public InformationObjectNodeModel getModel() {
        return (InformationObjectNodeModel) super.getModel();
    }

    public int getType() {
        return NODE_TYPE;
    }
    public static String createInformationObjectId(String uri) {
        return "nfo:" + Digest.sha1(uri);
    }

    public String getUri() {
        return information.getUri();
    }

    public Metadata getMetadata() {
        return information.getMetadata();
    }

    public boolean setMetadata(Metadata metadata) {
        return information.setMetadata(metadata);
    }

    public TaggingPost addTaggPost(Tag tag, Agent agent, boolean positive) {
        return information.addTaggPost(tag, agent, positive);
    }

    public List<ObjectLink> getObjectLinks() {
        return information.getObjectLinks();
    }

    public ObjectLinkPost addObjectLink(ObjectName name, Agent agent, String type) {
        return information.addObjectLink(name, agent, type);
    }

    public ArrayList<ObjectName> getObjectNames() {
        return information.getObjectNames();
    }

    public ArrayList<Tag> getTags() {
        return information.getTags();
    }

    public List<Tagging> getTaggings() {
        return information.getTaggings();
    }

    /**
     * 
     * @param agent
     * @return
     */
    public List<Tagging> getTaggings(Agent agent) {
        return information.getTaggings(agent);
    }
}
