package org.infoobject.core.infoobject.domain;

import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.infoobject.event.InformationObjectListener;
import org.infoobject.core.infoobject.to.MetadataTo;

import java.util.List;

/**
 * <p>
 * Class InformationObjectModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 15:02:37
 */
public interface InformationObjectModel {


    /**
     *
     * @param uri
     * @param tag
     * @param agent
     * @param positive
     * @return
     */
    TaggingPost addTagging(final String uri, Tag tag, Agent agent, boolean positive);

    /**
     *
     * @param uri
     * @param name
     * @param agent
     * @param type
     * @return
     */
    ObjectLinkPost addObjectLink(final String uri, ObjectName name, Agent agent, String type);


    /**
     * 
     * @param uri
     * @param name
     */
    void remove(String uri, ObjectName name);

    void add(MetadataTo meta);

    void addInformationObjectListener(InformationObjectListener l);

    void removeInformationObjectListener(InformationObjectListener l);

    InformationObjectListener[] getInformationObjectListener();


    /**
     * 
     * @param name
     * @return
     */
    List<InformationObject> getInformationObjects(ObjectName name);
    
    InformationObject getInformationObject(String uri);

    
}
