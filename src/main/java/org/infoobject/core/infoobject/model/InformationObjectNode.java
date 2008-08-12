package org.infoobject.core.infoobject.model;

import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.util.Digest;

import java.util.ArrayList;

/**
 * <p>
 * Class InformationObjectNode ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 17:59:01
 */
public class InformationObjectNode extends Node {

    public static final int NODE_TYPE =-6762;
    private final InformationObject information;

    public InformationObjectNode(InformationObjectNodeModel iNodeModel, InformationObject information) {
        super(iNodeModel);
        this.information = information;
        setName(createInformationObjectId(information.getUri()));

    }

    @Override
    public String getDisplayName() {
        if (information.getMetadata() != null && information.getMetadata().getTitle() != null) {
            return information.getMetadata().getTitle();
        } else {
            return information.getUri();
        }
    }

    /**
     * 
     * @return
     */
    public InformationObject getInformationObject() {
        return information;
    }

    public ArrayList<? extends Node> getNeighbors() {
        return new ArrayList<Node>();
    }

    @Override
    public boolean isPhysical() {
        return false;
    }

    /**
     * 
     * @return
     */
    public InformationObjectNodeModel getModel() {
        return (InformationObjectNodeModel) super.getModel();
    }

    public int getType() {
        return NODE_TYPE;
    }
    public static String createInformationObjectId(String uri) {
        return "nfo:" + Digest.sha1(uri);
    }

}
