package org.infoobject.core.components;

import org.infoobject.core.agent.application.AgentManager;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.crawl.xml.XsltMetadataExtractor;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.position.application.PositionLinkRelationManager;
import org.infoobject.core.tag.application.TaggingRelationManager;
import org.infoobject.magicmap.node.application.InformationNodeManager;

import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;

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
 *         Time: 17:16:33
 */
public abstract class AbstractManagerFactory implements ManagerFactory {
    private final ModelFactory modelFactory;
    private InformationNodeManager informationNodeManager;
    private InformationObjectManager informationObjectManager;
    private CrawlerManager crawlerManager;
    private XsltMetadataExtractor xslExtractor;
    private AgentManager agentManager;
    private PositionLinkRelationManager positionLinkRelationManager;
    private TaggingRelationManager taggingRelationManager;

    public AbstractManagerFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public InformationNodeManager getInformationNodeManager() {
        if (informationNodeManager ==null) {
            informationNodeManager = new InformationNodeManager(modelFactory.getInformationObjectNodeModel(), getInformationObjectManager());

        }
        return informationNodeManager;
    }

    public InformationObjectManager getInformationObjectManager() {
        if (informationObjectManager == null) {
            informationObjectManager = new InformationObjectManager(
                    modelFactory.getInformationObjectModel(), getInformationObjectRepository(), getAgentManager());
        }
        return informationObjectManager;
    }

    public CrawlerManager getCrawlerManager() {
        if (crawlerManager == null) {
            crawlerManager = new CrawlerManager(getXslExtractor());
        }

        return crawlerManager;
    }

    public XsltMetadataExtractor getXslExtractor() {
        if (xslExtractor == null) {
            xslExtractor = new XsltMetadataExtractor();
            try {
                xslExtractor.addXslt("classpath:org/infoobject/xslt/html.xsl");
                xslExtractor.addXslt("classpath:org/infoobject/xslt/mediawiki.xsl");
            } catch (IOException e) {
                handleException("XSLT", e);
            } catch (TransformerConfigurationException e) {
                handleException("XSLT", e);
            }
        }
        return xslExtractor;
    }

    public AgentManager getAgentManager() {
        if (this.agentManager == null) {
            agentManager = new AgentManager();
        }
        return agentManager;
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }

    public PositionLinkRelationManager getPositionLinkRelationManager() {
        if (positionLinkRelationManager == null) {
            positionLinkRelationManager = new PositionLinkRelationManager(
                    modelFactory.getInformationObjectNodeModel(),
                    modelFactory.getInformationObjectNodeGraph(),
                    modelFactory.getInformationObjectModel());
        }
        return positionLinkRelationManager;
    }

    public TaggingRelationManager getTaggingRelationManager() {
        if (taggingRelationManager == null) {
            taggingRelationManager = new TaggingRelationManager(
                    modelFactory.getInformationObjectNodeModel()
            );
        }
        return taggingRelationManager;
    }

    public void start() {
        modelFactory.getInformationObjectModel().addInformationObjectListener(getTaggingRelationManager());
        modelFactory.getInformationObjectModel().addInformationObjectListener(getPositionLinkRelationManager());        
    }

    public void stop() {
       
    }

    protected abstract void handleException(String message, Exception ex);
}
