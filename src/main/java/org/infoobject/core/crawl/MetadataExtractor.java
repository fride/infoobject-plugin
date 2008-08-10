package org.infoobject.core.crawl;

/**
 * <p>
 * Class MetadataExtractor ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:33:56
 */
public interface MetadataExtractor {

    MetadataExtractorResult extract(String uri);
}
