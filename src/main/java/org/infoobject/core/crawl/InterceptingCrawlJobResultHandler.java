package org.infoobject.core.crawl;

import org.infoobject.core.rdf.RdfContainer;

/**
 * <p>
 * Class InterceptingCrawlJobResultHandler ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 19:35:17
 */
public class InterceptingCrawlJobResultHandler implements CrawlJobResultHandler{
    private final CrawlJobResultHandler delegate;

    public InterceptingCrawlJobResultHandler(CrawlJobResultHandler delegate) {
        this.delegate = delegate;
    }

    public void crawlStarted() {
        delegate.crawlStarted();
    }

    public void urlCrawled(RdfContainer result, int depth) {
        delegate.urlCrawled(result, depth);
    }

    public void crawlFinished() {
        delegate.crawlFinished();
    }

    public void crawlFailed(Exception error) {
        delegate.crawlFailed(error);
    }
}
