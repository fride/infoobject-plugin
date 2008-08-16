package org.infoobject.core.infoobject.ui.util;

import ca.odell.glazedlists.matchers.Matcher;
import net.sf.magicmap.client.model.node.Node;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.domain.InformationObject;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Class InformationObjectMatcher ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 13.08.2008
 *         Time: 00:43:37
 */
public class InformationObjectMatcher implements Matcher<Node> {
    private final Set<String> idSet = new HashSet<String>();

    public InformationObjectMatcher(InformationObject information) {
        idSet.addAll(CollectionUtils.collect(information.getObjectNames(), new Transformer<ObjectName, String>() {
            public String transform(ObjectName objectName) {
                return objectName.getName();
            }
        }));
    }

    public boolean matches(Node node) {
        return idSet.contains(node.getName());
    }
}
