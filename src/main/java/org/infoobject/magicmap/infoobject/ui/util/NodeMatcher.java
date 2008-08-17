package org.infoobject.magicmap.infoobject.ui.util;

import ca.odell.glazedlists.matchers.Matcher;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.InformationObject;

/**
 * <p>
 * Class NodeMatcher ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 12.08.2008
 *         Time: 22:31:11
 */
public class NodeMatcher implements Matcher<InformationObject> {
    private final ObjectName name;

    public NodeMatcher(Node node) {
        this.name = ObjectName.positionName(node.getName(), node.getModel().getServerID());
    }

    public boolean matches(InformationObject informationObject) {

        final boolean b = informationObject.getObjectNames().contains(name);
        if (!b) {
            System.out.println(name +  " not in " + informationObject.getObjectNames());
        }
        return b;
    }
}
