package org.infoobject.core.infoobject.ui.util;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.impl.matchers.TrueMatcher;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.core.infoobject.domain.InformationObject;

/**
 * <p>
 * Class InformationObjectMatcherEditor ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 13.08.2008
 *         Time: 00:46:49
 */
public class InformationObjectMatcherEditor extends AbstractMatcherEditor<Node> {


    public InformationObjectMatcherEditor() {
        setInformationObject(null);
    }

    public void setInformationObject(InformationObject object) {
        final Matcher<Node> m = object == null ? new TrueMatcher<Node>() : new InformationObjectMatcher(object);
        fireChanged(m);
    }
}
