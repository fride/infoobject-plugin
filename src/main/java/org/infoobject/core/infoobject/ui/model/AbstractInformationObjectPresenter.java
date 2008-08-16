package org.infoobject.core.infoobject.ui.model;

import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.crawl.CrawlJobResultHandler;
import org.infoobject.core.crawl.CrawlJobResultHandlerAdapter;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.crawl.ui.CrawlUrlDialog;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.infoobject.domain.InformationObject;
import org.infoobject.core.infoobject.domain.Metadata;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObject;
import org.infoobject.core.infoobject.to.MetadataTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.ui.util.InformationObjectNodeListFactory;
import org.infoobject.core.rdf.RdfContainer;
import org.infoobject.magicmap.node.application.InformationNodeManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Class AbstractInformationObjectPresenter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 10:37:25
 */
public class AbstractInformationObjectPresenter extends AbstractModel {

    private final InformationNodeManager nodemanager;
    private final InformationObjectManager informationObjectManager;

    private CrawlerManager crawler;
    private InformationObjectNodeListFactory informationObjectNodeListFactory;


    /**
     * 
     * @param nodemanager
     * @param informationObjectManager
     * @param crawler
     * @param informationObjectNodeListFactory
     */
    public AbstractInformationObjectPresenter(
            InformationNodeManager nodemanager,
            InformationObjectManager informationObjectManager,
            CrawlerManager crawler,
            InformationObjectNodeListFactory informationObjectNodeListFactory) {
        this.nodemanager = nodemanager;
        this.informationObjectManager = informationObjectManager;
        this.crawler = crawler;
        this.informationObjectNodeListFactory = informationObjectNodeListFactory;
    }


    public InformationObjectNodeListFactory getInformationObjectNodeListFactory() {
        return informationObjectNodeListFactory;
    }

    public InformationNodeManager getNodemanager() {
        return nodemanager;
    }

    public InformationObjectManager getInformationObjectManager() {
        return informationObjectManager;
    }

    public CrawlerManager getCrawler() {
        return crawler;
    }

    /**
     * Displays a dialog requesting the user to enter an uri.
     * @param cb
     */
    public void loadInformation(final InformationObjectLoadCallback cb) {
        final CrawlJobResultHandler crawlJobResultHandler = new CrawlJobResultHandlerAdapter(){
            public void urlCrawled(RdfContainer result, int depth) {
                System.out.println("result = " + result);
                DefaultInformationObject informationObject = new DefaultInformationObject(result.getSubject().toString());
                Timestamp now = new Timestamp(System.currentTimeMillis());
                Metadata data = new Metadata(result, now);
                informationObject.setMetadata(data);
                informationObjectNodeListFactory.addUnsaved(informationObject);
                cb.onInformationObjectLoaded(informationObject);
            }
        };
        CrawlUrlDialog.crawl(MainGUI.getInstance().getMainFrame(), crawler, crawlJobResultHandler);
    }


    public void delete(org.infoobject.core.infoobject.domain.InformationObject info, Node node) {
        informationObjectManager.delete(info, new ObjectName(node.getName(), node.getModel().getServerID()), "mailto:noone@nix.de");            
    }

    public void createInformationObject(Node node, InformationObject informationObject, String type, Map<String, Boolean> tags) {
        final Metadata metadata = informationObject.getMetadata();
        MetadataTo metadataTo = new MetadataTo(metadata, "",metadata.getCrawlDate());
        this.informationObjectManager.saveInformationMetadata(
                Collections.singletonList(metadataTo));
        this.informationObjectManager.saveObjectLinkings(
                Collections.singletonList(new ObjectLinkingTo(informationObject.getUri(), new ObjectName(node.getName(), node.getModel().getServerID()), "", "mailto:test@test.de"))
        );
        List<TaggingTo> taggingTo = new ArrayList<TaggingTo>(tags.size());
        for (Map.Entry<String, Boolean> entry : tags.entrySet()) {
            taggingTo.add(new TaggingTo(informationObject.getUri(), entry.getKey(),entry.getValue(), "mailto:noone@nix.de"));
        }
        this.informationObjectManager.saveTaggings(taggingTo);
    }
}


