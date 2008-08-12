package org.infoobject.magicmap.infoobject.ui.model;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.impl.ReadOnlyList;
import ca.odell.glazedlists.util.concurrent.Lock;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.crawl.CrawlJobResultHandler;
import org.infoobject.core.crawl.CrawlJobResultHandlerAdapter;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.infoobject.InformationNodeManager;
import org.infoobject.core.infoobject.InformationObjectManager;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.infoobject.model.InformationObjectNode;
import org.infoobject.core.infoobject.model.ObjectName;
import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.rdf.RdfContainer;
import org.infoobject.core.rdf.vocabulary.DC;
import org.infoobject.magicmap.crawl.ui.CrawlUrlDialog;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectNodeListFactory;
import org.infoobject.magicmap.plugin.GuiComponentFactory;

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

    private final InformationObjectNodeListFactory informationObjectNodeListFactory;
    private final InformationNodeManager nodemanager;
    private final InformationObjectManager informationObjectManager;
    private GuiComponentFactory guiComponentFactory;
    
    private CrawlerManager crawler;


    /**
     * 
     * @param nodemanager
     * @param informationObjectManager
     * @param crawler
     * @param guiComponentFactory to create guis.
     */
    public AbstractInformationObjectPresenter(InformationNodeManager nodemanager, InformationObjectManager informationObjectManager, CrawlerManager crawler, GuiComponentFactory guiComponentFactory) {
        this.nodemanager = nodemanager;
        this.informationObjectManager = informationObjectManager;
        this.crawler = crawler;
        this.guiComponentFactory = guiComponentFactory;
        this.informationObjectNodeListFactory = new InformationObjectNodeListFactory(nodemanager.getNodeModel(), informationObjectManager.getModel());
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

    public GuiComponentFactory getGuiComponentFactory() {
        return guiComponentFactory;
    }

    /**
     * Displays a dialog requesting the user to enter an uri.
     * @param cb
     */
    public void loadInformation(final InformationObjectLoadCallback cb) {
        final CrawlJobResultHandler crawlJobResultHandler = new CrawlJobResultHandlerAdapter(){
            public void urlCrawled(RdfContainer result, int depth) {
                System.out.println("result = " + result);
                InformationObject informationObject = new InformationObject(result.getSubject().toString());
                informationObject.setMetadata(new InformationMetadataTo(result.getSubject().toString()));
                informationObject.getMetadata().setTitle(result.getUniqeObjectString(DC.Title));
                informationObject.getMetadata().setMimeType(result.getUniqeObjectString(DC.Format));
                informationObjectNodeListFactory.addUnsaved(informationObject);
                cb.onInformationObjectLoaded(informationObject);
            }
        };
        CrawlUrlDialog.crawl(MainGUI.getInstance().getMainFrame(), crawler, crawlJobResultHandler);
    }


    public void createInformationObject(Node node, InformationObject informationObject, String type, Map<String, Boolean> tags) {
        this.informationObjectManager.saveInformationMetadata(
                Collections.singletonList(informationObject.getMetadata()));
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


