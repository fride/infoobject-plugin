package org.infoobject.magicmap.infoobject.ui.model;

import net.sf.magicmap.client.utils.AbstractModel;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.gui.MainGUI;
import org.infoobject.core.infoobject.InformationNodeManager;
import org.infoobject.core.infoobject.InformationObjectManager;
import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.infoobject.model.InformationObjectNode;
import org.infoobject.core.infoobject.model.ObjectName;
import org.infoobject.core.crawl.*;
import org.infoobject.core.rdf.RdfContainer;
import org.infoobject.core.rdf.vocabulary.DC;
import org.infoobject.magicmap.crawl.ui.CrawlUrlDialog;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.impl.ReadOnlyList;
import ca.odell.glazedlists.util.concurrent.Lock;

import java.util.Collections;

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

    private EventList<Node> nodeList = new BasicEventList<Node>();

    private EventList<InformationObject> informationObjects = new BasicEventList<InformationObject>();

    public AbstractInformationObjectPresenter(InformationNodeManager nodemanager, InformationObjectManager informationObjectManager, CrawlerManager crawler) {
        this.nodemanager = nodemanager;
        this.informationObjectManager = informationObjectManager;
        this.crawler = crawler;


        nodemanager.getNodeModel().addNodeModelListener(new NodeModelListener() {
            public void nodeAddedEvent(Node node) {
                if (node.isPhysical()) {
                    add(node);
                } else if (node instanceof InformationObjectNode) {
                    add(((InformationObjectNode)node).getInformationObject());
                }
            }

            public void nodeUpdatedEvent(Node node, int i, Object o) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void nodeRemovedEvent(Node node) {
                if (node.isPhysical()) {
                    remove(node);
                } else if (node instanceof InformationObjectNode) {
                    //remove(((InformationObjectNode)node).getInformationObject());
                }
            }
        });
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
                InformationObject informationObject = new InformationObject(result.getSubject().toString());
                informationObject.setMetadata(new InformationMetadataTo(result.getSubject().toString()));
                informationObject.getMetadata().setTitle(result.getUniqeObjectString(DC.Title));
                informationObject.getMetadata().setMimeType(result.getUniqeObjectString(DC.Format));
                add(informationObject);
                cb.onInformationObjectLoaded(informationObject);
            }
        };
        CrawlUrlDialog.crawl(MainGUI.getInstance().getMainFrame(), crawler, crawlJobResultHandler);
    }

    protected void add(InformationObject informationObject) {
        Lock lock = this.informationObjects.getReadWriteLock().writeLock();
        try {
            if (!informationObjects.contains(informationObject)){
                informationObjects.add(informationObject);
            }
            lock.lock();
        } finally {
            lock.unlock();
        }

    }

    protected void remove(Node node) {
        Lock lock = this.nodeList.getReadWriteLock().writeLock();
        try {
            lock.lock();
            if (!nodeList.contains(node) && node.isPhysical()) {
                nodeList.remove(node);
            }
        } finally {
            lock.unlock();
        }
    }
    protected void add(Node node) {
        Lock lock = this.nodeList.getReadWriteLock().writeLock();
        try {
            lock.lock();
            if (!nodeList.contains(node)) {
                nodeList.add(node);
            }
        } finally {
            lock.unlock();
        }
    }

    public EventList<Node> getNodeList() {
        return new ReadOnlyList<Node>(nodeList);
    }

    public EventList<InformationObject> getInformationObjectList() {
        return new ReadOnlyList<InformationObject>(informationObjects);
    }

    public void createInformationObject(Node node, InformationObject informationObject) {
        this.informationObjectManager.saveInformationMetadata(
                Collections.singletonList(informationObject.getMetadata()));
        this.informationObjectManager.saveObjectLinkings(
                Collections.singletonList(new ObjectLinkingTo(informationObject.getUri(), new ObjectName(node.getName(), node.getModel().getServerID()), "", "mailto:test@test.de"))
        );
    }
}


