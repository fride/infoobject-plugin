package org.infoobject.core.infoobject.domain;

import org.infoobject.core.rdf.RdfContainer;
import org.infoobject.core.rdf.vocabulary.DC;
import org.openrdf.model.impl.GraphImpl;

import java.sql.Timestamp;

/**
 * <p>
 * Class Metadata ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 15:55:21
 */
public class Metadata extends RdfContainer {
    private final Timestamp crawlDate;


    /**
     * 
     * @param container
     */
    public Metadata(RdfContainer container, Timestamp crawlDate) {
        super(container, container.getSubject().toString());
        this.crawlDate = crawlDate;
    }

    public Metadata(String uri) {
        this (new RdfContainer(new GraphImpl(),uri ), new Timestamp(0));
    }

    public Timestamp getCrawlDate() {
        return crawlDate;
    }

    public String getTitle() {
        return super.getUniqeObjectString(DC.Title, getSubject().stringValue());
    }

    public void setTitle(String title) {
        super.setUniqueProperty(DC.Title, title);
    }


    public String getMimeType() {
        return getUniqeObjectString(DC.Format, "application/unknown");
    }
}
