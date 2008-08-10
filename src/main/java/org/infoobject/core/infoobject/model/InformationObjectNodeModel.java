package org.infoobject.core.infoobject.model;

import net.sf.magicmap.client.model.node.INodeModel;

/**
 * <p>
 * Class InformationNodeModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 17:59:51
 */
public interface InformationObjectNodeModel extends INodeModel {

    InformationObjectNode findInformationNode(String uri);
    
    InformationObjectNodeGraph getInformationObjectNodeGraph();
}
