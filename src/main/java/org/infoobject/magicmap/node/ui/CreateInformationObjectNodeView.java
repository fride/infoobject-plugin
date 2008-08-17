package org.infoobject.magicmap.node.ui;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import ca.odell.glazedlists.util.concurrent.Lock;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.utils.AbstractModel;
import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;
import org.infoobject.core.crawl.CrawlJobResultHandler;
import org.infoobject.core.crawl.CrawlJobResultHandlerAdapter;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.crawl.ui.CrawlUrlDialog;
import org.infoobject.core.infoobject.application.InformationObjectManager;
import org.infoobject.core.infoobject.domain.Metadata;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.Tagging;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObject;
import org.infoobject.core.rdf.RdfContainer;
import org.infoobject.magicmap.infoobject.ui.InformationMetadataView;
import org.infoobject.magicmap.infoobject.ui.InformationTaggingView;
import org.infoobject.magicmap.infoobject.ui.ObjectLinkDetailsView;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectLoadCallback;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectNodeListFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Class CreateInformationObjectUI ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 19:10:04
 */
public class CreateInformationObjectNodeView extends AbstractModel {
    private InformationObjectManager informationObjectManager;
    private CrawlerManager crawlerManager;
    private InformationObjectNodeListFactory informationObjectNodeListFactory;

    private ObjectLinkSelectionView objectLinkSelectionView;
    private final Map<String, Action> actionMap = new HashMap<String, Action>();
    private ObjectLinkDetailsView objectLinkDetailsView;
    private JComponent view;
    private InformationTaggingView informationTaggingView;
    private InformationMetadataView informationMetadataView;


    private EventList<Tagging> currentTags = new BasicEventList<Tagging>();
    private EventComboBoxModel<Tagging> currentTagsModel = new EventComboBoxModel<Tagging>(currentTags);

    private boolean enabled;
    private ObjectName objectName;

    public CreateInformationObjectNodeView(InformationObjectManager informationObjectManager, CrawlerManager crawlerManager, InformationObjectNodeListFactory informationObjectNodeListFactory) {
        this.informationObjectManager = informationObjectManager;
        this.crawlerManager = crawlerManager;
        this.informationObjectNodeListFactory = informationObjectNodeListFactory;
        objectLinkSelectionView = new ObjectLinkSelectionView(informationObjectNodeListFactory);

        objectLinkSelectionView.addPropertyChangeListener("selectedNode", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                checkCreate();
            }
        });
        objectLinkSelectionView.addPropertyChangeListener("selectedInformationObject", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                setInformation(objectLinkSelectionView.getSelectedInformationObject());
                checkCreate();
            }
        });

        setupActions();
    }

    private void setInformation(org.infoobject.core.infoobject.domain.InformationObject info) {
        final Lock lock = this.currentTags.getReadWriteLock().writeLock();
        if (info != null) {
            final Metadata metadata = info.getMetadata();
            informationMetadataView.setMimeType(metadata.getMimeType());
            informationMetadataView.setTitle(metadata.getTitle());
            try {
                lock.lock();
                this.currentTags.clear();
                this.currentTags.addAll(info.getTaggings());
            }finally {
                lock.unlock();
            }
            StringBuilder b = new StringBuilder();
            informationMetadataView.getView().setEnabled(true);
            if (getSelectedNode() != null) {
                for (Tagging tagging : info.getTaggings(informationObjectManager.getAgent())) {
                    b.append(tagging.getTag().getRawValue());
                }
            }
            //this.informationTaggingView.setTags();
        } else {
            informationMetadataView.getView().setEnabled(false);
            try {
                lock.lock();
                currentTags.clear();
            }finally {
                lock.unlock();
            }
        }
    }


    public JComponent getView() {
        if (view == null) {
            createComponents();

            FormLayout l = new FormLayout("fill:p:grow");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(l);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p:grow");

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("information", informationMetadataView.getView());
            tabs.addTab("Verbindung", objectLinkDetailsView.getView());
            tabs.addTab("Tags", informationTaggingView.getView());

            b.appendRow("p");
            b.add(objectLinkSelectionView.visualProxy(null, actionMap), cc.xy(1,b.getRowCount()));

            b.appendUnrelatedComponentsGapRow();
            b.appendRow("p");
            b.add(tabs, cc.xy(1,b.getRowCount()));
            this.view = b.getPanel();


        }
        return view;
    }

    private void createComponents() {
        objectLinkDetailsView = new ObjectLinkDetailsView(new DefaultListModel());
        informationTaggingView = new InformationTaggingView();
        informationTaggingView.setTagModel(currentTagsModel);
        informationMetadataView = new InformationMetadataView();
    }

    public void createInformation() {
        if (isEnabled()) {
            final org.infoobject.core.infoobject.domain.InformationObject object = getSelectedInformationObject();
            final Node selectedNode = getSelectedNode();

            objectName = new ObjectName(selectedNode.getName(), selectedNode.getModel().getServerID());
            assert(null != selectedNode.getModel().findNode(objectName.getName()));
            
            informationObjectManager.saveObjectLinkings(
                    Collections.singletonMap(objectName, objectLinkDetailsView.getMyType().getText()),
                     object.getUri());
            
            informationObjectManager.saveInformationMetadata(Collections.singletonList(object.getMetadata()));

            final Map<String,Boolean> taggings = new HashMap<String, Boolean>();
            CollectionUtils.forAllDo(this.informationTaggingView.getTags() , new Closure<String>() {
                public void execute(String tag) {
                    if (tag.startsWith("--")) {
                        taggings.put(tag.substring(2),false);
                    } else {
                        taggings.put(tag,true);
                    }
                }
            });

            informationObjectManager.saveTaggings(taggings, object.getUri());
        }
        else {
            if (getSelectedInformationObject() == null) {
                throw new IllegalStateException("Choose an information objet");
            }
            if (getSelectedNode() == null) {
                throw new IllegalStateException("Choose node");
            }
        }
    }

    public Node getSelectedNode() {
        return objectLinkSelectionView.getSelectedNode();
    }

    public org.infoobject.core.infoobject.domain.InformationObject getSelectedInformationObject() {
        return objectLinkSelectionView.getSelectedInformationObject();
    }

    public void checkCreate() {
        boolean ok = null != this.objectLinkSelectionView.getSelectedInformationObject();
        final Node node = getSelectedNode();
        ok &= (null != node && node != Node.EMPTY_NODE && node.isPhysical());
        setEnabled(ok);
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected void loadInformation(final InformationObjectLoadCallback cb) {
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
        CrawlUrlDialog.crawl(MainGUI.getInstance().getMainFrame(), crawlerManager, crawlJobResultHandler);
    }

    private void setupActions() {
        final InformationObjectLoadCallback cb = new InformationObjectLoadCallback() {
            public void onInformationObjectLoaded(org.infoobject.core.infoobject.domain.InformationObject loaded) {
                objectLinkSelectionView.setSelectedInformationObject(loaded);
            }
        };
        Action loadAction = new MagicAction("Lden") {
            public void actionPerformed(ActionEvent event) {
                loadInformation(cb);
            }
        };
        actionMap.put("load", loadAction);
    }

    private void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            firePropertyChange("enabled", !enabled, enabled);
        }
    }

    public void setSelectedNode(Node selectedNode) {
        this.objectLinkSelectionView.setSelectedNode(selectedNode);
    }
}
