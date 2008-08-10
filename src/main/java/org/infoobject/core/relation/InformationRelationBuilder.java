package org.infoobject.core.relation;

import org.infoobject.core.infoobject.model.InformationObjectNode;
import org.infoobject.core.infoobject.model.InformationRelation;

/**
 * <p>
 * Class InformationRelationBuilder ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 16:59:58
 */
public interface InformationRelationBuilder {

    void setInformationNode(InformationObjectNode node);
    InformationRelation build();
}
