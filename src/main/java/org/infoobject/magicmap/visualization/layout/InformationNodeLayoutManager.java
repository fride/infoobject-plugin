package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.node.model.InformationObjectNode;
import org.infoobject.core.relation.domain.InformationRelationEdge;
import org.infoobject.core.relation.domain.PositionRelationEdge;
import org.infoobject.core.relation.domain.RelationEdgeVisitor;
import org.infoobject.core.relation.domain.RelationEdge;

import java.util.Map;
import java.util.HashMap;
import java.awt.*;

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
    
    private RelationEdgeVisitor relationAdder = new RelationEdgeVisitor() {
        public void visit(PositionRelationEdge relation) {
            thread.suspend();
            try {
                final Node sourceNode = relation.getSourceNode();
                final InformationObjectNode targetNode = relation.getTargetNode();

                PhysicalVertex physicalVertex = physicalVertexMap.get(sourceNode);
                InformationVertex infoVertex = infoVertexMap.get(targetNode);
                if (physicalVertex == null){
                    physicalVertex = new PhysicalVertex(sourceNode);
                    physicalVertexMap.put(sourceNode, physicalVertex);
                    graph.addVertex(physicalVertex);
                    layout.setFix(physicalVertex);

                }
                if (infoVertex == null){
                    infoVertex = new InformationVertex(targetNode);
                    infoVertexMap.put(targetNode, infoVertex);
                    graph.addVertex(infoVertex);
                }
                if (!edgeMap.containsKey(relation)){
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
                    v1 = new InformationVertex(sourceNode);
                    infoVertexMap.put(sourceNode, v1);
                    graph.addVertex(v1);
                }
                if(v2 == null) {
                    v2 = new InformationVertex(targetNode);
                    infoVertexMap.put(targetNode, v2);
                    graph.addVertex(v1);
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
    public void addRelation(RelationEdge edge) {
        System.out.println(getClass().getName() + " edge = " + edge);
        thread.suspend();
        try {
            edge.accept(relationAdder);
        } finally{
            thread.unsupend();
        }
    }

    public void removeRelation(RelationEdge edge) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void resize(int width, int height) {
        this.layout.resize(new Dimension(width, height));
    }
}
