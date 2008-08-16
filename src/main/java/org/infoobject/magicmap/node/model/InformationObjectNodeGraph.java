package org.infoobject.magicmap.node.model;

import net.sf.magicmap.client.model.node.INodeGraph;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.relation.domain.*;
import org.infoobject.core.relation.domain.PositionRelation.Factory;

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
public interface InformationObjectNodeGraph extends INodeGraph, RelationModel {

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

    /**
     * 
     * @param node
     * @param node1
     * @return
     */
    PositionRelationEdge getObjectEdge(InformationObjectNode node, Node node1);

    /**
     *
     * @param node
     * @param node1
     * @return
     */
    InformationRelationEdge getInformationEdge(InformationObjectNode node, InformationObjectNode node1);

    /**
     *
     * @param n1
     * @param n2
     * @param factory
     * @return
     */
    PositionRelationEdge addRelation(InformationObjectNode n1, Node n2, Factory factory);

    /**
     *
     * @param n1
     * @param n2
     * @param factory
     * @return
     */
    InformationRelationEdge addEdge(InformationObjectNode n1, InformationObjectNode n2, InformationRelation.Factory factory);

}
