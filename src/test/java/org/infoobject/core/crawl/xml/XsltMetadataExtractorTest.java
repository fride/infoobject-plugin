package org.infoobject.core.crawl.xml;

import junit.framework.TestCase;
import org.infoobject.core.crawl.MetadataExtractorResult;
import org.infoobject.core.infoobject.domain.Metadata;

import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * <p>
 * Class XsltMetadataExtractorTest ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 17.08.2008
 *         Time: 11:28:27
 */
public class XsltMetadataExtractorTest extends TestCase {


    public void testHtml() throws IOException, TransformerConfigurationException {
        XsltMetadataExtractor xslExtractor = new XsltMetadataExtractor();
        xslExtractor.addXslt("classpath:org/infoobject/xslt/html.xsl");
       // xslExtractor.addXslt("classpath:org/infoobject/xslt/mediawiki.xsl");

        final MetadataExtractorResult res = xslExtractor.extract("http://www.spiegel.de");
        final Metadata metadata = new Metadata(res.getMetadataGraph(), new Timestamp(System.currentTimeMillis()));
        System.out.printf("depiction: " + metadata.getDepiction()); 
    }
}
