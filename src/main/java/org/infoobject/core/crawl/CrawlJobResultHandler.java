package org.infoobject.core.crawl;

import org.openrdf.model.Graph;
import org.infoobject.core.rdf.RdfContainer;

/**
 * <p>
 * Class CrawlJobResultHandler ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:55:30
 */
public interface CrawlJobResultHandler {

    void crawlStarted();

    /**
     *
     * @param result
     * @param depth
     */
    void urlCrawled(RdfContainer result, int depth);

    /**
     * 
     */
    void crawlFinished();

    /**
     * 
     * @param error
     */
    void crawlFailed(Exception error);
}
