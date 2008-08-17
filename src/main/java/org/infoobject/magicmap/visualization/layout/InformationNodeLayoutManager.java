package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import net.sf.magicmap.client.model.node.Node;
import net.sf.magicmap.client.model.node.ClientNode;
import net.sf.magicmap.client.model.location.jung.MagicRepulsionFunction;
import org.infoobject.core.relation.domain.InformationRelationEdge;
import org.infoobject.core.relation.domain.PositionRelationEdge;
import org.infoobject.core.relation.domain.RelationEdge;
import org.infoobject.core.relation.domain.RelationEdgeVisitor;
import org.infoobject.magicmap.node.model.InformationObjectNode;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Class InformationNodeLayoutManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 23:16:12
 */
public class InformationNodeLayoutManager {
    private Graph graph;
    private AssociationSpringLayout layout;
    private LayoutThread thread;
    private Map<Node, PhysicalVertex> physicalVertexMap  = new HashMap<Node, PhysicalVertex>();
    private Map<InformationObjectNode, InformationVertex> infoVertexMap  = new HashMap<InformationObjectNode, InformationVertex>();
    private Map<RelationEdge, Edge> edgeMap  = new HashMap<RelationEdge, Edge>();
    private final Object graphMutex = new Object();

    // TODO Buggy!
    private RelationEdgeVisitor relationAdder = new RelationEdgeVisitor() {
        public void visit(PositionRelationEdge relation) {
            thread.suspend();
            try {
                final Node sourceNode = relation.getSourceNode();
                final InformationObjectNode targetNode = relation.getTargetNode();


                if (sourceNode instanceof ClientNode) {
                    System.out.println("sourceNode = " + sourceNode);
                }
                PhysicalVertex physicalVertex = physicalVertexMap.get(sourceNode);
                InformationVertex infoVertex = infoVertexMap.get(targetNode);
                if (physicalVertex == null){
                    physicalVertex = addPhysicalNode(sourceNode);

                }
                if (infoVertex == null){
                    infoVertex = addInformtionNode(targetNode);
                }
                if (!edgeMap.containsKey(relation)){
                    System.out.println("physicalVertex.getGraph() = " + physicalVertex.getGraph());
                    System.out.println("infoVertex.getGraph() = " + infoVertex.getGraph());
                    RelationJungEdge edge = new RelationJungEdge(physicalVertex, infoVertex, relation);
                    edgeMap.put(relation, edge);
                    graph.addEdge(edge);

                }
                layout.update();
            } finally {
                thread.unsupend();
            }
        }

        public void visit(InformationRelationEdge relation) {
            thread.suspend();
            try {
                final InformationObjectNode sourceNode = relation.getSourceNode();
                final InformationObjectNode targetNode = relation.getTargetNode();
                InformationVertex v1 = infoVertexMap.get(sourceNode);
                InformationVertex v2 = infoVertexMap.get(targetNode);

                if(v1 == null) {
                    v1 = addInformtionNode(sourceNode);
                }
                if(v2 == null) {
                    v2 = addInformtionNode(targetNode);
                }

                if (!edgeMap.containsKey(relation)){
                    RelationJungEdge edge = new RelationJungEdge(v1, v2, relation);
                    edgeMap.put(relation, edge);
                    graph.addEdge(edge);

                }
                layout.update();

            } finally {
                thread.unsupend();
            }
        }
    };



    /**
     *
     * @param graph
     */
    public InformationNodeLayoutManager(Graph graph) {
        this.graph = graph;
        layout = new AssociationSpringLayout(graph);
        layout.setRepulsionFunction(new MagicRepulsionFunction());
        thread = new LayoutThread(layout);
        this.layout.resize(new Dimension(320, 200));
    }

    public void suspend() {
        thread.suspend();
    }

    public void unsupend() {
        thread.unsupend();
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.stop();
    }

    public void removeNode(Node node) {
        thread.suspend();
        synchronized (graphMutex) {
            try {
                Vertex vertex = node.isPhysical() ?
                        physicalVertexMap.remove(node) :
                        infoVertexMap.remove((InformationObjectNode)node);
                if (vertex != null) {
                    this.graph.removeVertex(vertex);
                    layout.update();
                }
            } finally{
                thread.unsupend();
            }
        }

    }
    public void addRelation(RelationEdge edge) {
        System.out.println(getClass().getName() + " edge = " + edge);
        thread.suspend();
        synchronized (graphMutex) {
            try {
                edge.accept(relationAdder);
            } finally{
                thread.unsupend();
            }
        }
    }

    public void move(Node node) {
        Vertex vertex = node.isPhysical() ? physicalVertexMap.get(node) : infoVertexMap.get(node);
        thread.suspend();
        try {
            if (vertex != null) {
                layout.setX(vertex, node.getX());
                layout.setY(vertex, node.getY());
            }
        }finally{
            thread.unsupend();
        }
    }
    public void removeRelation(RelationEdge edge) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void resize(int width, int height) {
        synchronized (graphMutex) {
            this.layout.resize(new Dimension(width, height));
        }
    }
    // Needed for layout stuff even if no edges exist!
    public void addNode(Node node) {
        thread.suspend();
        try {
            if (node.isPhysical() && !physicalVertexMap.containsKey(node)) {
                addPhysicalNode(node);
                layout.update();
            }
        } finally {
            thread.unsupend();
        }
    }

    private InformationVertex addInformtionNode(InformationObjectNode targetNode) {
        InformationVertex infoVertex;
        infoVertex = new InformationVertex(targetNode);
        infoVertexMap.put(targetNode, infoVertex);
        graph.addVertex(infoVertex);
        return infoVertex;
    }

    private PhysicalVertex addPhysicalNode(Node sourceNode) {
        PhysicalVertex physicalVertex;
        physicalVertex = new PhysicalVertex(sourceNode);
        physicalVertexMap.put(sourceNode, physicalVertex);
        graph.addVertex(physicalVertex);
        layout.setFix(physicalVertex);
        return physicalVertex;
    }
}
