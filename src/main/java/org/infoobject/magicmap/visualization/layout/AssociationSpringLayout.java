package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.visualization.MagicSpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.Edge;
import net.sf.magicmap.client.model.location.jung.MagicRepulsionFunction;
import net.sf.magicmap.client.model.location.jung.JungNodePlacer;
import net.sf.magicmap.client.model.node.Node;

import java.util.Set;

/**
 * <p>
 * Springlayout....
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.03.2008
 *         Time: 10:34:23
 */
public class AssociationSpringLayout extends MagicSpringLayout {

	/**
	 * Constructor for a SpringLayout for a raw graph with associated
	 * dimension--the input knows how big the graph is. Defaults to the unit
	 * length function.
     * @param g he graph
     */
	public AssociationSpringLayout(Graph g) {
		this(g,UNITLENGTHFUNCTION, new MagicRepulsionFunction());
	}

	/**
	 * Constructor for a SpringLayout for a raw graph with associated component.
	 *
	 * @param g the input Graph
	 * @param f the length function
	 * @param r the repulsion function
	 */
	public AssociationSpringLayout(Graph g, LengthFunction f, RepulsionFunction r) {
		super(g, f, r);
	}

	// TH:
	/**
	 * Returns the x coordinate of the vertex from the Coordiantes object.
	 *
	 * @see edu.uci.ics.jung.visualization.Layout#getX(edu.uci.ics.jung.graph.Vertex)
	 */
	@Override
	public void setX(Vertex v, double x) {
		super.setX(v, x);	//To change body of overridden methods use File | PluginSettings | File Templates.
		//final Node node = (Node) v.getUserDatum(JungNodePlacer.NODE_KEY);
		//node.setInformation((int)x,node.getY(), node.getZ());
	}

	/**
	 * Returns the y coordinate of the vertex from the Coordiantes object.
	 *
	 * @see edu.uci.ics.jung.visualization.Layout#getX(edu.uci.ics.jung.graph.Vertex)
	 */
	@Override
	public void setY(Vertex v, double y) {
		super.setY(v, y);	//To change body of overridden methods use File | PluginSettings | File Templates.
		//final Node node = (Node) v.getUserDatum(JungNodePlacer.NODE_KEY);
		//node.setInformation(node.getX(), (int)y, node.getZ());
	}


    @SuppressWarnings({"unchecked"})
    @Override
    public Set<Edge> getVisibleEdges() {
        return super.getVisibleEdges();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Set<Vertex> getVisibleVertices() {
        return super.getVisibleVertices();
    }

    @SuppressWarnings({"unchecked"})
    protected void moveNodes() {
		super.moveNodes();
		synchronized (getCurrentSize()) {
            Set<Vertex> vs = getVisibleVertices();
            Vertex[] vertieces = vs.toArray(new Vertex[vs.size()]);
            for (Vertex v:vertieces) {
				if (isNotCalculatedVertx(v)) {
					continue;
				}
				Node n = (Node) v.getUserDatum(JungNodePlacer.NODE_KEY);
				if (!dontMove(v)) {
					n.setPositionSilent((int) getX(v), (int) getY(v), n.getZ());
				} else {
					super.setX(v, n.getX());
					super.setY(v, n.getY());
				}
			}
		}
	}
}
