package org.infoobject.core.crawl;

import org.openrdf.model.Graph;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.model.impl.URIImpl;
import org.infoobject.core.rdf.RdfContainer;

import java.sql.Timestamp;

/**
 * <p>
 * Class MetadataExtractorResult ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:32:45
 */
public class MetadataExtractorResult {

    private RdfContainer metadataGraph;
    private String informationUri;
    private Exception error;
    private Timestamp exractionTime;


    public MetadataExtractorResult(String informationUri, Timestamp exractionTime) {
        this.informationUri = informationUri;
        this.exractionTime = exractionTime;
        this.metadataGraph = new RdfContainer(new GraphImpl(), informationUri);
    }

    public void setError(Exception ex){
        this.error = ex;
    }
    public Exception getError() {
        return error;
    }

    public Timestamp getExractionTime() {
        return exractionTime;
    }

    public RdfContainer getMetadataGraph() {
        return metadataGraph;
    }

    public String getInformationUri() {
        return informationUri;
    }
}
