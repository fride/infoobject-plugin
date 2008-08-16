package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.graph.impl.UndirectedSparseVertex;
import edu.uci.ics.jung.utils.UserData;
import org.infoobject.magicmap.node.model.InformationObjectNode;

import java.lang.ref.WeakReference;

import net.sf.magicmap.client.model.location.jung.JungNodePlacer;

/**
 * <p>
 * Class InformationVertex ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 23:23:26
 */
public class InformationVertex extends UndirectedSparseVertex {
    private final WeakReference<InformationObjectNode> node;

    public InformationVertex(InformationObjectNode node) {
        this.node =  new WeakReference<InformationObjectNode>(node);
        addUserDatum(JungNodePlacer.NODE_KEY, node,UserData.SHARED);
    }

    public InformationObjectNode getNode() {
        return node.get();
    }
}
