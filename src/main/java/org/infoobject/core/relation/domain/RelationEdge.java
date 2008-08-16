package org.infoobject.core.relation.domain;

import net.sf.magicmap.client.model.node.IMagicEdge;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.node.model.InformationObjectNodeGraph;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * <p>
 * Class RelationEdge ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 12:48:47
 */
public abstract class RelationEdge<ST extends Node, TT extends Node, RT extends Relation> implements IMagicEdge, Iterable<RT> {

    private final WeakReference<ST> source;
    private final WeakReference<TT> target;
    private final Set<RT> relations = new HashSet<RT>();
    private final WeakReference<InformationObjectNodeGraph> graph;
    private double length;
    private double weight;


    public RelationEdge(ST source, TT target, InformationObjectNodeGraph graph) {
        this.graph = new WeakReference<InformationObjectNodeGraph>(graph);
        this.source = new WeakReference<ST>(source);
        this.target = new WeakReference<TT>(target);
    }

    public ST getSourceNode() {
        return source.get();
    }

    public TT getTargetNode() {
        return target.get();
    }

    public InformationObjectNodeGraph getGraph() {
        return graph.get();
    }
    public void addRelation(RT relation) {
        this.relations.add(relation);
    }
    public void removeRelation(RT relation) {
        this.relations.remove(relation);
    }
    
    public void addAttribute(String s, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getValue(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLength(double length) {
        this.length = length;
    }
    public double getLength() {
        return length;
    }

    public Iterator<RT> iterator() {
        return new ArrayList<RT>(relations).iterator();
    }

    public abstract double getEstimatedLength();

    public double getDistanceRatio() {
        return 0;
    }

    public synchronized void update() {
        final List<RT> deleteCandidates = new LinkedList<RT>();
        double odweight = weight;
        this.weight = 0;
        for (RT rt: relations) {
            rt.update();
            if (0.0 == rt.getWeight()){
                deleteCandidates.add(rt);
            }
            weight += weight;
        }
        if (deleteCandidates.size() > 0) {
            this.relations.removeAll(deleteCandidates);
        }
        if (odweight != weight) {
            getGraph().updateRelation(this);
        }

    }
    
    public int size() {
        return relations.size();
    }

    public double getWeight() {
        return weight;
    }
    public abstract void accept(RelationEdgeVisitor v);
}
