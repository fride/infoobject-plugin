package org.infoobject.core.infoobject.model;

import net.sf.magicmap.client.model.node.*;
import net.sf.magicmap.client.model.location.MagicGraphEvent;

import java.util.*;

import edu.uci.ics.jung.graph.impl.*;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventType;
import edu.uci.ics.jung.utils.UserData;
import org.infoobject.core.infoobject.model.InformationObjectNodeGraph;
import org.infoobject.core.infoobject.model.RelationEdge;
import org.infoobject.core.infoobject.InformationNodeManager;

/**
 * <p>
 * Class InformationNodeGraph ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:47:38
 */
public class InformationObjectNodeGraphImpl implements InformationObjectNodeGraph {


    private final SparseGraph graph = new UndirectedSparseGraph();
    private final Map<Node, Vertex> physicalNodes = new HashMap<Node, Vertex>();
    private final Map<InformationObjectNode, Vertex> informationNodes = new HashMap<InformationObjectNode, Vertex>();
    private final Map<RelationEdge, Edge> relations = new HashMap<RelationEdge, Edge>();
    private final List<NodeGraphListener> listeners = Collections.synchronizedList(new ArrayList<NodeGraphListener>());


    public InformationObjectNodeGraphImpl() {
        graph.addListener(new GraphEventListener() {
            public void vertexAdded(GraphEvent event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void vertexRemoved(GraphEvent event) {

            }

            public void edgeAdded(GraphEvent event) {
                Edge e = (Edge) event.getGraphElement();
                fireEdgAdded(getRelation(e));
            }

            public void edgeRemoved(GraphEvent event) {
                Edge e = (Edge) event.getGraphElement();
                fireEdgeRemoved(getRelation(e));
            }
        }, GraphEventType.ALL_SINGLE_EVENTS);
    }

    /**
     *
     * @param node
     */
    public void insertNode(Node node) {
        findVertex(node, true);
    }

    /**
     * Dont use it. Neede only cause of api.
     * @param n1
     * @param n2
     * @return
     */
    @Deprecated
    public RelationEdge addEdge(Node n1, Node n2) {
        throw new IllegalStateException("Not suported");
    }


    

    public RelationEdge getRelation(Edge edge) {
        return (RelationEdge) edge.getUserDatum("RELATION");
    }



    private InformationObjectNode getInformationNode(Vertex v) {
        return (InformationObjectNode) (v != null ? v.getUserDatum("NODE") : null);
    }

    private Node getNode(Vertex v1) {
        return (Node) (v1 != null ? v1.getUserDatum("NODE") : null);
    }

    /**
     * 
     * @param relationEdge
     */
    public void updateRelation(RelationEdge relationEdge) {
        double weight = relationEdge.getWeight();
        if (weight == 0) {
            removeEdge(relationEdge.getSourceNode(), relationEdge.getTargetNode());
            // TODO notify;
        } else {
            // Todo notify change!
        }
    }

    /**
     *
     * @param node
     * @param createIfNotFound
     * @return
     */
    protected Vertex findVertex(Node node, boolean createIfNotFound) {
        Vertex vertex = null;

        if (node.isPhysical()) {
            vertex = physicalNodes.get(node);
            if (vertex == null){
                vertex = new UndirectedSparseVertex();
                vertex.setUserDatum("NODE", node, UserData.SHARED);
                graph.addVertex(vertex);
                physicalNodes.put(node,vertex);
            }
        } else if (node instanceof InformationObjectNode){
            InformationObjectNode info = (InformationObjectNode) node;
            vertex = informationNodes.get(info);
            if (vertex == null) {
                vertex = new UndirectedSparseVertex();
                vertex.setUserDatum("NODE", info, UserData.SHARED);
                graph.addVertex(vertex);
                informationNodes.put(info,vertex);
            }
        }  else if (createIfNotFound){
            throw new IllegalArgumentException("Node type not supported " + node.getClass().getSimpleName());

        }
        return vertex;
    }

    /**
     * @param node
     * @param node1
     * @return
     */
    public RelationEdge getEdge(Node node, Node node1) {
        final Vertex v1 = findVertex(node, false);
        final Vertex v2 = findVertex(node1, false);
        final Edge edge = (v1 != null && v2 != null) ? v1.findEdge(v2) : null;
        
        return (RelationEdge) (edge != null ? edge.getUserDatum("RELATION") : null);
    }

    public ObjectRelationEdge getObjectEdge(InformationObjectNode node, Node node1) {
        final Vertex v1 = findVertex(node, false);
        final Vertex v2 = findVertex(node1, false);
        final Edge edge = (v1 != null && v2 != null) ? v1.findEdge(v2) : null;

        return (ObjectRelationEdge) (edge != null ? edge.getUserDatum("RELATION") : null);
    }

    public InformationRelationEdge getInformationEdge(InformationObjectNode node, InformationObjectNode node1) {
        final Vertex v1 = findVertex(node, false);
        final Vertex v2 = findVertex(node1, false);
        final Edge edge = (v1 != null && v2 != null) ? v1.findEdge(v2) : null;

        return (InformationRelationEdge) (edge != null ? edge.getUserDatum("RELATION") : null);
    }

    public boolean removeEdge(Node n1, Node n2) {
        final Vertex v1 = findVertex(n1, false);
        final Vertex v2 = findVertex(n2, false);
        final Edge edge = (v1 != null && v2 != null) ? v1.findEdge(v2) : null;
        if (edge != null) {
            this.graph.removeEdge(edge);
            this.relations.remove(getRelation(edge));
            edge.removeUserDatum("RELATION");
            return true;
        }
        return false;
    }

    public Set<? extends RelationEdge> getEdges(Node node) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<? extends RelationEdge> getEdges() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void fireEdgeRemoved(RelationEdge edge) {
        MagicGraphEvent event = new MagicGraphEvent(this, edge);
        for (NodeGraphListener l: getNodeGraphListeners()) {
            l.edgeRemoved(event);
        }
    }

    protected void fireEdgAdded(RelationEdge edge) {
        MagicGraphEvent event = new MagicGraphEvent(this, edge);
        for (NodeGraphListener l: getNodeGraphListeners()) {
            l.edgeAdded(event);
        }
    }
    
    public void addNodeGraphListener(NodeGraphListener nodeGraphListener) {
        listeners.add(nodeGraphListener);
    }

    public void removeNodeGraphListener(NodeGraphListener nodeGraphListener) {
        listeners.remove(nodeGraphListener);
    }

    public NodeGraphListener[] getNodeGraphListeners() {
        return listeners.toArray(new NodeGraphListener[listeners.size()]);
    }

    public Collection<EdgeType> getSupportedEdgeTypes() {
        return Collections.singletonList(new EdgeType("relation"));
    }

    /**
     * 
     * @param n1
     * @param n2
     * @param factory
     * @return
     */
    public ObjectRelationEdge addRelation(InformationObjectNode n1, Node n2, ObjectRelation.Factory factory) {
        UndirectedSparseEdge edge = createJungEdge(n1, n2);
        ObjectRelationEdge relationEdge = (ObjectRelationEdge) edge.getUserDatum("RELATION");
        if (relationEdge == null) {
            relationEdge = new ObjectRelationEdge(n2, n1, this);
            relationEdge.addRelation(factory.create(relationEdge));
            edge.addUserDatum("RELATION", relationEdge, UserData.SHARED);
            graph.addEdge(edge);
        } else {
            relationEdge.addRelation(factory.create(relationEdge));
        }
        return relationEdge;
    }

    public InformationRelationEdge addEdge(InformationObjectNode n1, InformationObjectNode n2, InformationRelation.Factory factory) {
        UndirectedSparseEdge edge = createJungEdge(n1, n2);
        InformationRelationEdge relationEdge = (InformationRelationEdge) edge.getUserDatum("RELATION");
        if (relationEdge == null) {
            relationEdge = new InformationRelationEdge(n1, n2, this);
            relationEdge.addRelation(factory.create(relationEdge));
            edge.addUserDatum("RELATION", relationEdge, UserData.SHARED);
            this.relations.put(relationEdge, edge);
            graph.addEdge(edge);
        } else {
            relationEdge.addRelation(factory.create(relationEdge));
        }
        return relationEdge;
    }

    private UndirectedSparseEdge createJungEdge(InformationObjectNode n1, Node n2) {
        final Vertex v1 = findVertex(n1, false);
        final Vertex v2 = findVertex(n2, false);
        if (v1 == null || v2 == null) throw new IllegalArgumentException("No Vertex for nodes");
        Edge e = v1.findEdge(v2);

        return (UndirectedSparseEdge) (e != null ?  e : new UndirectedSparseEdge(v1, v2));
    }


}
