package org.infoobject.core.crawl;

import org.infoobject.core.rdf.RdfContainer;

/**
 * <p>
 * Class CrawlJobResultHandlerAdapter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 19:34:57
 */
public class CrawlJobResultHandlerAdapter implements CrawlJobResultHandler{
    public void crawlStarted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param result
     * @param depth
     */
    public void urlCrawled(RdfContainer result, int depth) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     */
    public void crawlFinished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @param error
     */
    public void crawlFailed(Exception error) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
