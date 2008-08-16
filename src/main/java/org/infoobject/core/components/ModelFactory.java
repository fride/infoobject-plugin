package org.infoobject.core.components;

import org.infoobject.core.infoobject.domain.InformationObjectModel;
import org.infoobject.magicmap.node.model.InformationObjectNodeModel;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;
import net.sf.magicmap.client.model.node.INodeModel;

/**
 * <p>
 * Class ModelFactory ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 17:29:38
 */
public interface ModelFactory extends ComponentFactory{
    InformationObjectModel getInformationObjectModel();

    InformationObjectNodeModel getInformationObjectNodeModel();

    INodeModel getNodeModel();

    InformationObjectNodeGraph getInformationObjectNodeGraph();
}
