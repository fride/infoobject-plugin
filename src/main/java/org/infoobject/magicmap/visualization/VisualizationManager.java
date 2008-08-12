package org.infoobject.magicmap.visualization;

import net.sf.magicmap.client.model.node.NodeGraphListener;
import net.sf.magicmap.client.model.node.IMagicEdge;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.model.node.MapNode;
import net.sf.magicmap.client.model.location.MagicGraphEvent;
import net.sf.magicmap.client.visualization.VisualEdge;
import net.sf.magicmap.client.visualization.VisualNode;
import net.sf.magicmap.client.visualization.VisualizationContext;
import net.sf.magicmap.client.visualization.NodeCanvas;
import edu.uci.ics.jung.visualization.MagicSpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;
import org.infoobject.core.infoobject.model.InformationObjectNodeGraph;
import org.infoobject.core.infoobject.model.RelationEdge;
import org.infoobject.magicmap.visualization.layout.InformationNodeLayoutManager;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>
 * Class VisualizationManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 19:15:45
 */
public class VisualizationManager {
    private final InformationObjectNodeGraph graph;
    private final NodeCanvas nodeCanvas;
    private final Map<RelationEdge, VisualEdge> edgeMap = new HashMap<RelationEdge, VisualEdge>();
    private final Map<Node, VisualNode> nodeMap = new HashMap<Node, VisualNode>();

    private final InformationNodeLayoutManager layoutManager = new InformationNodeLayoutManager(new UndirectedSparseGraph());
    private MapNode map;
    private boolean started;
    public VisualizationManager(InformationObjectNodeGraph graph, NodeCanvas nodeCanvas) {
        this.graph = graph;
        this.nodeCanvas = nodeCanvas;
        
        graph.addNodeGraphListener(new NodeGraphListener() {
            public void edgeAdded(MagicGraphEvent event) {
                IMagicEdge edge = event.getEdge();
                addEdge(edge.getSourceNode(), edge.getTargetNode());
            }

            public void edgeRemoved(MagicGraphEvent event) {
                IMagicEdge edge = event.getEdge();
                removeEdge((RelationEdge) edge);
            }
        });
    }

    private void removeEdge(RelationEdge edge) {
        final VisualNode source = getVisualNode(edge.getSourceNode(), false);
        final VisualNode target = getVisualNode(edge.getTargetNode(), false);
        final VisualEdge visualEdge = (source != null && target != null) ? edgeMap.get(edge) : null;
        if (visualEdge != null) {
            nodeCanvas.removeEdge(edge);
        }
        layoutManager.removeRelation(edge);
    }

    private void addEdge(Node sourceNode, Node targetNode) {
        System.out.println(getClass().getName() + " sourceNode = " + sourceNode);
        System.out.println(getClass().getName() + " targetNode = " + targetNode);
        RelationEdge relation = this.graph.getEdge(sourceNode, targetNode);
        VisualEdge e = edgeMap.get(relation);
        if (e == null) {
            e = new VisualEdge(relation,
                    getVisualNode(relation.getSourceNode(), true),
                    getVisualNode(relation.getTargetNode(), true), getVisualizationContext());
            edgeMap.put(relation, e);
            this.nodeCanvas.addEdge(e);
        }
        layoutManager.addRelation(relation);

    }

    private void start() {
        if (!started) {
            layoutManager.start();
            started = true;
        }
    }

    public void stop() {
        layoutManager.stop();
    }

    private VisualizationContext getVisualizationContext() {
        return nodeCanvas.getContext();
    }

    private VisualNode getVisualNode(Node node, boolean createIfNotFound) {
        VisualNode visualNode = nodeMap.get(node);
        if (visualNode == null && createIfNotFound) {
            visualNode = new VisualNode(node, getVisualizationContext());
            this.nodeMap.put(node, visualNode);
            this.nodeCanvas.addNode(visualNode);
        }
        return visualNode;
    }


    public void setMap(MapNode map) {
        this.map = map;
        this.layoutManager.resize(map.getMapInfo().width, map.getMapInfo().height);
        start();
    }
}
