package org.infoobject.core.infoobject.model;

import net.sf.magicmap.client.model.node.*;
import net.sf.magicmap.client.model.location.INodePlacer;
import net.sf.magicmap.client.interfaces.NodeModelListener;
import net.sf.magicmap.client.meta.MapInfo;

import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;
import java.util.List;

import org.infoobject.core.infoobject.model.InformationObjectNodeModel;

/**
 * <p>
 * Class InformationNodeModel ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:44:16
 */
public class InformationObjectNodeModelImpl implements InformationObjectNodeModel {
    private final INodeModel nodeModel;
    private InformationObjectNodeGraphImpl infoGraphImpl;
    
    public InformationObjectNodeModelImpl(INodeModel nodeModel) {
        this.nodeModel = nodeModel;
        infoGraphImpl = new InformationObjectNodeGraphImpl();
        this.nodeModel.addNodeGraph(infoGraphImpl);
    }

    public InformationObjectNode findInformationNode(String uri) {
        return (InformationObjectNode)nodeModel.findNode(InformationObjectNode.createInformationObjectId(uri));
    }

    

    public InformationObjectNodeGraph getInformationObjectNodeGraph(){
        return infoGraphImpl;
    }

    public void addNode(Node node) {
        nodeModel.addNode(node);
    }

    public void removeNode(Node node) {
        nodeModel.removeNode(node);
    }

    public void updateNode(Node node, int i, Object o) {
        nodeModel.updateNode(node, i, o);
    }

    public void rehashNode(Node node, String s) {
        nodeModel.rehashNode(node, s);
    }

    public ArrayList<? extends Node> findNeighbors(Node node) {
        return nodeModel.findNeighbors(node);
    }

    @Deprecated
    public ArrayList<? extends Node> findNonNeighbors(Node node) {
        return nodeModel.findNonNeighbors(node);
    }

    public void addNodeModelListener(NodeModelListener nodeModelListener) {
        nodeModel.addNodeModelListener(nodeModelListener);
    }

    public void removeNodeModelListener(NodeModelListener nodeModelListener) {
        nodeModel.removeNodeModelListener(nodeModelListener);
    }

    public NodeModelListener[] nodeModelListeners() {
        return nodeModel.nodeModelListeners();
    }

    public Set<AccessPointSeerNode> getAccessPointSeerNodes() {
        return nodeModel.getAccessPointSeerNodes();
    }

    public ArrayList<LocationNode> getLocationsWithAtLeastOneAccessPoint(ArrayList<AccessPointNode> accessPointNodes) {
        return nodeModel.getLocationsWithAtLeastOneAccessPoint(accessPointNodes);
    }

    public ArrayList<LocationNode> getLocationsWithAtLeastOneAccessPoint(ArrayList<AccessPointNode> accessPointNodes, Node node) {
        return nodeModel.getLocationsWithAtLeastOneAccessPoint(accessPointNodes, node);
    }

    public Node findNode(String s) {
        return nodeModel.findNode(s);
    }

    public boolean nodeExists(String s) {
        return nodeModel.nodeExists(s);
    }

    public AccessPointNode findAccessPoint(String s) {
        return nodeModel.findAccessPoint(s);
    }

    public boolean accessPointExists(String s) {
        return nodeModel.accessPointExists(s);
    }

    public ClientNode findClient(String s) {
        return nodeModel.findClient(s);
    }

    public boolean clientExists(String s) {
        return nodeModel.clientExists(s);
    }

    public Collection<Node> getNodes() {
        return nodeModel.getNodes();
    }

    public void clear() {
        nodeModel.clear();
    }

    public void setNodePlacer(INodePlacer iNodePlacer) {
        nodeModel.setNodePlacer(iNodePlacer);
    }

    public INodePlacer getNodePlacer() {
        return nodeModel.getNodePlacer();
    }

    public void setCurrentMap(MapInfo mapInfo) {
        nodeModel.setCurrentMap(mapInfo);
    }

    public MapNode getCurrentMap() {
        return nodeModel.getCurrentMap();
    }

    public void setServerID(String s) {
        nodeModel.setServerID(s);
    }

    public String getServerID() {
        return nodeModel.getServerID();
    }

    public <K, N extends INode> INodeIndex<K, N> createIndex(NodeKeyFunction<K, N> knNodeKeyFunction) {
        return nodeModel.createIndex(knNodeKeyFunction);
    }

    public boolean removeIndex(INodeIndex iNodeIndex) {
        return nodeModel.removeIndex(iNodeIndex);
    }

    public List<? extends INodeIndex> getNodeIndexList() {
        return nodeModel.getNodeIndexList();
    }

    public Collection<INodeGraph> getSubGraphs() {
        return nodeModel.getSubGraphs();
    }

    public IMagicEdge addEdge(Node node, Node node1) {
        return nodeModel.addEdge(node, node1);
    }

    public IMagicEdge getEdge(Node node, Node node1) {
        return nodeModel.getEdge(node, node1);
    }

    public boolean removeEdge(Node node, Node node1) {
        return nodeModel.removeEdge(node, node1);
    }

    public Set<? extends IMagicEdge> getEdges() {
        return nodeModel.getEdges();
    }

    public void addNodeGraphListener(NodeGraphListener nodeGraphListener) {
        nodeModel.addNodeGraphListener(nodeGraphListener);
    }

    public void removeNodeGraphListener(NodeGraphListener nodeGraphListener) {
        nodeModel.removeNodeGraphListener(nodeGraphListener);
    }

    public NodeGraphListener[] getNodeGraphListeners() {
        return nodeModel.getNodeGraphListeners();
    }

    public boolean addNodeGraph(INodeGraph iNodeGraph) {
        return nodeModel.addNodeGraph(iNodeGraph);
    }

    public boolean removeSubGraph(INodeGraph iNodeGraph) {
        return nodeModel.removeSubGraph(iNodeGraph);
    }

    public Collection<EdgeType> getSupportedEdgeTypes() {
        return nodeModel.getSupportedEdgeTypes();
    }
}
