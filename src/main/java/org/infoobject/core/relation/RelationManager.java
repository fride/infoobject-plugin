package org.infoobject.core.relation;

import org.infoobject.core.infoobject.event.InformationObjectListener;
import org.infoobject.core.infoobject.event.TaggingEvent;
import org.infoobject.core.infoobject.event.ObjectLinkingEvent;
import org.infoobject.core.infoobject.event.InformationMetadataEvent;
import org.infoobject.core.infoobject.model.InformationObjectNodeGraph;
import org.infoobject.core.infoobject.model.ObjectLinkPost;

/**
 * <p>
 * Class RelationManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:44:43
 */
public class RelationManager {

    private final InformationObjectNodeGraph graph;

    public RelationManager(InformationObjectNodeGraph graph) {
        this.graph = graph;
    }

    
}
