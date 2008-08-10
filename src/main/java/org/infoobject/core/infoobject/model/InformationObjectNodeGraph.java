package org.infoobject.core.infoobject.model;

import net.sf.magicmap.client.model.node.INodeGraph;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.model.RelationEdge;

import java.util.Set;

/**
 * <p>
 * Ein Graph der Informationsobjekte untereinander und mit physikalichen Knoten verbindet.
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 18:01:37
 */
public interface InformationObjectNodeGraph extends INodeGraph {

    /**
     * 
     * @param n1
     * @param n2
     * @return
     */
    RelationEdge addEdge(Node n1, Node n2);

    /**
     *
     * @param node
     * @param node1
     * @return
     */
    RelationEdge getEdge(Node node, Node node1);

    /**
     *
     * @param node
     * @return
     */
    Set<? extends RelationEdge> getEdges(Node node);

    /**
     *
     * @return
     */
    Set<? extends RelationEdge> getEdges();

    /**
     *
     * @param relationEdge
     */
    void updateRelation(RelationEdge relationEdge);

    ObjectRelationEdge getObjectEdge(InformationObjectNode node, Node node1);
    
    InformationRelationEdge getInformationEdge(InformationObjectNode node, InformationObjectNode node1);

    ObjectRelationEdge addRelation(InformationObjectNode n1, Node n2, ObjectRelation.Factory factory);

    InformationRelationEdge addEdge(InformationObjectNode n1, InformationObjectNode n2, InformationRelation.Factory factory);

}
