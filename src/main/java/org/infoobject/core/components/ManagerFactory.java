package org.infoobject.core.components;

import org.infoobject.magicmap.node.application.InformationNodeManager;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.infoobject.dao.InformationObjectRepository;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.crawl.xml.XsltMetadataExtractor;
import org.infoobject.core.agent.application.AgentManager;

/**
 * <p>
 * Class ManagerFactory ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 17:31:10
 */
public interface ManagerFactory extends ComponentFactory{
    InformationNodeManager getInformationNodeManager();

    InformationObjectManager getInformationObjectManager();

    CrawlerManager getCrawlerManager();

    XsltMetadataExtractor getXslExtractor();

    InformationObjectRepository getInformationObjectRepository() ;

    AgentManager getAgentManager();
}
