package org.infoobject.magicmap.infoobject.ui.util;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.impl.matchers.TrueMatcher;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.model.InformationObject;

/**
 * <p>
 * Class NodeMatcherEditor ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 12.08.2008
 *         Time: 22:28:31
 */
public class NodeMatcherEditor extends AbstractMatcherEditor<InformationObject> {


    public NodeMatcherEditor() {
        setFilterNode(null);
    }

    public void setFilterNode(Node filterNode) {        
        Matcher<InformationObject> matcher = (filterNode == null) ? new TrueMatcher<InformationObject>() : new NodeMatcher(filterNode);
        fireChanged(matcher);
    }

}
