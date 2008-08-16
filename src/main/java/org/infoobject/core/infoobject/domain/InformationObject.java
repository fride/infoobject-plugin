package org.infoobject.core.infoobject.domain;

import org.infoobject.core.agent.domain.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class InformationObject ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 15:45:06
 */
public interface InformationObject {

    String getUri();

    Metadata getMetadata();

    
    boolean setMetadata(Metadata metadata);

    TaggingPost addTaggPost(Tag tag, Agent agent, boolean positive);

    List<ObjectLink> getObjectLinks();

    ObjectLinkPost addObjectLink(ObjectName name, Agent agent, String type);

    ArrayList<ObjectName> getObjectNames();

    ArrayList<Tag> getTags();

    List<Tagging> getTaggings();
}
