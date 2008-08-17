package org.infoobject.html.domain;

import org.infoobject.core.relation.domain.InformationRelation;
import org.infoobject.core.relation.domain.InformationRelationEdge;

/**
 * <p>
 * Class Anchor ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 16:22:06
 */
public class Anchor extends InformationRelation {

    private String linkText;
    private String linkTitle;

    public Anchor(InformationRelationEdge relation, String linkText, String linkTitle) {
        super(relation);
        this.linkText = linkText;
        this.linkTitle = linkTitle;
    }

    protected double doUpdate() {
        return 1.0; 
    }

    public String getLinkText() {
        return linkText;
    }

    public String getLinkTitle() {
        return linkTitle;
    }
}
