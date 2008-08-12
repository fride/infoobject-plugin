package org.infoobject.magicmap.infoobject.ui.util;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.impl.ReadOnlyList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.util.concurrent.Lock;
import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.infoobject.model.InformationObjectModel;
import org.infoobject.core.infoobject.model.InformationObjectNode;
import org.infoobject.core.infoobject.model.InformationObjectNodeModel;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Class InformationObjectListFactory ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 12.08.2008
 *         Time: 22:09:29
 */
public class InformationObjectNodeListFactory  {

    private Set<InformationObject> unsavedObjects = new HashSet<InformationObject>();

    private EventList<InformationObject> informationObjects = new BasicEventList<InformationObject>();

    private EventList<Node> nodeList = new BasicEventList<Node>();
    private final InformationObjectNodeModel nodeModel;
    private final InformationObjectModel informationObjectModel;

    public InformationObjectNodeListFactory(InformationObjectNodeModel nodeModel, InformationObjectModel informationObjectModel) {
        this.nodeModel = nodeModel;
        this.informationObjectModel = informationObjectModel;
        nodeModel.addNodeModelListener(new NodeModelListener() {
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

    public FilterList<Node> getNodeList(MatcherEditor<Node> matcher) {
        return new FilterList<Node>(getNodeList(),matcher);
    }

    public FilterList<InformationObject> getInformationObjectList(MatcherEditor<InformationObject> matcher) {
        return new FilterList<InformationObject>(getInformationObjectList(),matcher);
    }

    public ReadOnlyList<Node> getNodeList() {
        return new ReadOnlyList<Node>(nodeList);
    }

    public ReadOnlyList<InformationObject> getInformationObjectList() {
        return new ReadOnlyList<InformationObject>(informationObjects);
    }



    public boolean isSaved(InformationObject object) {
        return unsavedObjects.contains(object);
    }

    /**
     *
     * @param informationObject
     */
    protected void add(InformationObject informationObject) {
        Lock lock = this.informationObjects.getReadWriteLock().writeLock();
        try {
            lock.lock();
            if (!informationObjects.contains(informationObject)){

                informationObjects.add(informationObject);
                unsavedObjects.remove(informationObject);
            }

        } finally {
            lock.unlock();
        }
    }

    protected void remobe(InformationObject informationObject) {
        Lock lock = this.informationObjects.getReadWriteLock().writeLock();
        try {
            lock.lock();
            if (!informationObjects.contains(informationObject)){

                informationObjects.remove(informationObject);
                unsavedObjects.remove(informationObject);
            }
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

    /**
     *
     * @param informationObject
     */
    public void addUnsaved(InformationObject informationObject) {
        this.unsavedObjects.add(informationObject);
        add(informationObject);
    }
}
