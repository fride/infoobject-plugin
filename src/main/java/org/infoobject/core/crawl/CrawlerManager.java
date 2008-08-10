package org.infoobject.core.crawl;

import org.infoobject.core.crawl.CrawlJobResultHandler;
import org.infoobject.core.rdf.vocabulary.InformationObjectVoc;
import org.infoobject.core.rdf.RdfContainer;
import org.openrdf.model.Graph;
import org.openrdf.model.Statement;
import org.openrdf.model.Resource;
import org.openrdf.model.vocabulary.RDF;

import java.util.Iterator;

/**
 * <p>
 * Class MetadataExtractorManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:34:23
 */
public class CrawlerManager {



    private MetadataExtractor extractor;

    public CrawlerManager(MetadataExtractor extractor) {
        this.extractor = extractor;
    }


    public void crawl(CrawlJob job, CrawlJobResultHandler handler) {
        extractUrl(job.getUri(), job.getDepth(), handler);


    }

    /**
     * 
     * @param uri
     * @param depth
     * @param handler
     */
    private void extractUrl(String uri, int depth, CrawlJobResultHandler handler) {
        System.out.println("Crawling uri " + uri + " with " + extractor);
        MetadataExtractorResult extractorResult = extractor.extract(uri);
        if (extractorResult.getError() != null){
            handler.crawlFailed(extractorResult.getError());
        } else {
            RdfContainer metadataGraph = extractorResult.getMetadataGraph();
            handler.urlCrawled(metadataGraph, depth);
            if (depth > 0 ){
                Iterator<Statement> statementIterator = metadataGraph.match(null, RDF.TYPE, InformationObjectVoc.HardLink);
                while (statementIterator.hasNext()) {
                    Resource linkedUri = statementIterator.next().getSubject();
                    extractUrl(linkedUri.toString(), depth-1, handler);
                }
            } else {
                handler.crawlFinished();
            }
        }

    }
}
