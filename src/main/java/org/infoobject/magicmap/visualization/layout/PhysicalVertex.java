package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.graph.impl.UndirectedSparseVertex;
import edu.uci.ics.jung.utils.UserData;

import java.lang.ref.WeakReference;

import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.model.location.jung.JungNodePlacer;

/**
 * <p>
 * Class PhysicalVertex ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 23:24:41
 */
public class PhysicalVertex extends UndirectedSparseVertex {

    private final WeakReference<Node> node;

    public PhysicalVertex(Node node) {
        this.node =  new WeakReference<Node>(node);
        addUserDatum(JungNodePlacer.NODE_KEY,node, UserData.SHARED);
    }

    public Node getNode() {
        return node.get();
    }
}
