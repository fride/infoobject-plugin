package org.infoobject.core.crawl.xml;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.openrdf.rio.turtle.TurtleParserFactory;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.helpers.StatementCollector;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.infoobject.core.crawl.MetadataExtractorResult;
import org.infoobject.core.crawl.MetadataExtractor;
import org.infoobject.core.crawl.ExtractorException;
import org.infoobject.core.util.net.HtmlSaxParserFactory;
import org.infoobject.core.util.Digest;
import org.infoobject.core.rdf.vocabulary.DC;
import org.infoobject.core.rdf.vocabulary.InformationObjectVoc;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.sql.Timestamp;

/**
 * <p>
 * Class XsltMetadataExtractor ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:35:07
 */
public class XsltMetadataExtractor implements MetadataExtractor {

    static HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
    private final AbstractSAXParser saxParser = HtmlSaxParserFactory.getSaxParser();
    private TransformerFactory factory = TransformerFactory.newInstance();
    private final Map<String,Transformer> transformers = new HashMap<String,Transformer>();

    /**
     *
     * @param xslt
     * @throws java.io.IOException
     * @throws javax.xml.transform.TransformerConfigurationException
     */
    public void addXslt(String xslt) throws IOException, TransformerConfigurationException {
        URL xsltUrl = getXsltUrl(xslt);
        StreamSource source = new StreamSource(xsltUrl.openConnection().getInputStream());
        transformers.put(xslt, factory.newTransformer(source));
    }

    private URL getXsltUrl(String xslt) throws MalformedURLException {
        URL xsltUrl = xslt.startsWith("classpath:") ?
                getClass().getClassLoader().getResource(xslt.substring("classpath:".length())) :
                new URL(xslt);
        return xsltUrl;
    }

    public List<String> getTransformerNames(){
        return new ArrayList<String>(transformers.keySet());
    }
    public String getXstl(String name) throws IOException {
        StringBuilder b = new StringBuilder("");
        if (this.transformers.containsKey(name)) {
            InputStream inputStream = getXsltUrl(name).openStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String line = r.readLine();
            while (line != null) {
                b.append(line);
            }
        }
        return b.toString();
    }
    /**
     * @param uri
     * @return a result.
     */
    public MetadataExtractorResult extract(String uri) {
        final MetadataExtractorResult result = new MetadataExtractorResult(uri, new Timestamp(System.currentTimeMillis()));
        final TurtleParserFactory parserFactory = new TurtleParserFactory();
        try {
            URL url = new URL(uri);
            StringWriter stringWriter = new StringWriter();
            final Result transformResult = new StreamResult(stringWriter);


            for (Transformer transformer: this.transformers.values()){
                transformer.setParameter("base_uri", uri);
                transformer.setParameter("source_host", url.getHost());
                transformer.setParameter("source_protocol", url.getProtocol());
                transformer.setParameter("source_file", url.getFile());
                transformer.transform(new DocumentSource(getDocument(uri, result)), transformResult);

                String rdf = stringWriter.toString();
                File file = new File("/tmp/" + Digest.sha1(uri, transformer.toString()) + ".rdf");
                FileWriter w = new FileWriter(file);
                w.write(rdf);
                w.flush();
                w.close();

                if (rdf != null && rdf.length() > 0){
                    System.out.println("parsing = " + stringWriter.toString());
                    RDFParser rdfParser = parserFactory.getParser();
                    StatementCollector statementCollector = new StatementCollector();
                    rdfParser.setRDFHandler(statementCollector);
                    rdfParser.parse(new StringReader(stringWriter.toString()), uri);
                    result.getMetadataGraph().addAll(statementCollector.getStatements());                    
                }
            }
        } catch (MalformedURLException e) {
            result.setError(e);
        } catch (Exception e){
            result.setError(e);
        }
        return result;
    }

    
    public Document getDocument(String uri, MetadataExtractorResult result) throws ExtractorException {
        final GetMethod m = new GetMethod(uri);
        try {
            int http = httpClient.executeMethod(m);
            Header contentType = m.getResponseHeader("Content-Type");
            Header lastModified =  m.getResponseHeader("Last-Modified");
            System.out.println("result = " + m.getResponseHeaders());
            result.getMetadataGraph().setUniqueProperty(DC.Format, contentType.getValue());
            result.getMetadataGraph().setUniqueProperty(InformationObjectVoc.size, m.getResponseContentLength());
            if (contentType.getValue().startsWith("text/html")) {
                Document document = new SAXReader(saxParser).read(m.getResponseBodyAsStream());
                return document;
            } else {
                throw new ExtractorException("Document no supported " + contentType);
            }
        } catch (DocumentException e) {
            throw new ExtractorException("Cant parse xml! " + e.getMessage());
        } catch (IOException e) {
            throw new ExtractorException(e.getMessage());
        } finally{
            m.releaseConnection();
        }
    }

    public static void main(String[] args) throws Exception, TransformerConfigurationException {
        XsltMetadataExtractor extractor = new XsltMetadataExtractor();
        extractor.addXslt("classpath:org/infoobject/xslt/html.xsl");
        //extractor.addXslt("classpath:org/infoobject/xslt/mediawiki.xsl");
        //extractor.extract("http://www.spiegel.de");
        System.out.println("--------------------------");
        /*MetadataExtractorResult result = extractor.extract("http://de.wikipedia.org/wiki/Berlin");
        if (result.getError() != null){
            throw result.getError();
        }
        System.out.println("result = " + result.getMetadataGraph());*/

        MetadataExtractorResult result = extractor.extract("http://www.heise.de");
        if (result.getError() != null){
            throw result.getError();
        }
        System.out.println("result = " + result.getMetadataGraph());

    }

}
