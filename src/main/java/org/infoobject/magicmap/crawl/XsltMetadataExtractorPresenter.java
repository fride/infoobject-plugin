package org.infoobject.magicmap.crawl;

import org.infoobject.core.crawl.xml.XsltMetadataExtractor;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.BasicEventList;

/**
 * <p>
 * Class XsltMetadataExtractorPresenter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 02:20:50
 */
public class XsltMetadataExtractorPresenter {
    private XsltMetadataExtractor extractor;
    private EventList<String> xslt = new BasicEventList<String>();

    public XsltMetadataExtractorPresenter(XsltMetadataExtractor extractor) {
        this.extractor = extractor;
    }
}
