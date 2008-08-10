package org.infoobject.core.crawl;

/**
 *
 * 
 */
public class CrawlJob {

    private String uri;
    private int depth;

    public CrawlJob(String uri, int depth) {
        this.uri = uri;
        this.depth = depth;
    }

    public String getUri() {
        return uri;
    }

    public int getDepth() {
        return depth;
    }
}
