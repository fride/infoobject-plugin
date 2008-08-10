package org.infoobject.core.util.net;

import net.sf.magicmap.client.utils.HtmlSaxParser;
import org.cyberneko.html.HTMLConfiguration;

/**
 * <p>
 * Class HtmlSaxParserFactory ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 01:35:42
 */
public class HtmlSaxParserFactory {

    private static HtmlSaxParser parser;

    static HTMLConfiguration config = new HTMLConfiguration();
    static {
        config.setFeature("http://cyberneko.org/html/features/augmentations", true);
        config.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
        //config.setFeature("http://cyberneko.org/html/features/report-errors", true);
    }

    public static HtmlSaxParser getSaxParser() {
        return new HtmlSaxParser(config);

    }
}

