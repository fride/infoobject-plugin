package org.infoobject.magicmap.visualization.application;

import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;
import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.model.location.MagicGraphEvent;
import net.sf.magicmap.client.model.node.*;
import net.sf.magicmap.client.visualization.NodeCanvas;
import net.sf.magicmap.client.visualization.VisualEdge;
import net.sf.magicmap.client.visualization.VisualNode;
import net.sf.magicmap.client.visualization.VisualizationContext;
import org.infoobject.core.relation.domain.RelationEdge;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;
import org.infoobject.magicmap.node.model.InformationObjectNodeModel;
import org.infoobject.magicmap.visualization.layout.InformationNodeLayoutManager;

import java.util.HashMap;
import java.util.Map;

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
    private final InformationObjectNodeModel nodeModel;
    private final NodeCanvas nodeCanvas;
    private final Map<RelationEdge, VisualEdge> edgeMap = new HashMap<RelationEdge, VisualEdge>();
    private final Map<Node, VisualNode> nodeMap = new HashMap<Node, VisualNode>();

    private final InformationNodeLayoutManager layoutManager = new InformationNodeLayoutManager(new UndirectedSparseGraph());
    private boolean started;
    
    public VisualizationManager(InformationObjectNodeGraph graph, InformationObjectNodeModel nodeModel, NodeCanvas nodeCanvas) {
        this.graph = graph;
        this.nodeModel = nodeModel;
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
        
        nodeModel.addNodeModelListener(new NodeModelListener() {
            public void nodeAddedEvent(Node node) {
                if (node.isPhysical()) {
                    layoutManager.addNode(node);
                }
            }

            public void nodeUpdatedEvent(Node node, int i, Object o) {
                if (!node.isPhysical() && i == NodeModel.UPDATE_POSITION) {
                    layoutManager.move(node);                    
                }
            }

            public void nodeRemovedEvent(Node node) {
                
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

    public void start() {
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
        this.layoutManager.resize(map.getMapInfo().width, map.getMapInfo().height);
        start();
    }
}
