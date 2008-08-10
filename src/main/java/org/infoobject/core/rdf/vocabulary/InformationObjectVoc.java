package org.infoobject.core.rdf.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;


/**
 * <p>
 * Class InformationObjectVoc ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 00:13:27
 */
public class InformationObjectVoc {
    public static String NAMESPACE= "http://www.magicmap.de/2008/06/infoobject#";


    public static final URI ObjectLink;
    public static final URI Tag;
    public static final URI Tagging;
    public static final URI TagPost;
    //public static final URI ObjectLinkPost;
    public static final URI rawTag;
    public static final URI tags;
    public static final URI isPositive;
    public static final URI hasTag;
    public static final URI magicAgentId;
    public static final URI hasInformation;
    public static final URI hasObject;

    public static final URI linkType;

    public static final URI size;
    public static final URI crawlDate;
    public static final URI Link;
    public static final URI HardLink;

    public static URI hasObjectLink;

    static{
        final ValueFactory factory = new ValueFactoryImpl();
        ObjectLink = factory.createURI(NAMESPACE, "ObjectLinking");
        Tag = factory.createURI(NAMESPACE, "Tag");
        Tagging = factory.createURI(NAMESPACE, "Tagging");
        TagPost = factory.createURI(NAMESPACE, "ObjectLinking");
        //ObjectLinkPost = factory.createURI(NAMESPACE, "ObjectLinkPost");
        rawTag = factory.createURI(NAMESPACE, "rawTag");
        tags = factory.createURI(NAMESPACE, "tags");
        isPositive = factory.createURI(NAMESPACE, "isPositive");
        hasTag = factory.createURI(NAMESPACE, "hasTag");
        magicAgentId = factory.createURI(NAMESPACE, "magicAgentId");
        hasInformation = factory.createURI(NAMESPACE, "hasInformation");
        hasObject = factory.createURI(NAMESPACE, "hasObject");
        linkType = factory.createURI(NAMESPACE, "linkType");
        size = factory.createURI(NAMESPACE, "size");
        crawlDate = factory.createURI(NAMESPACE, "crawlDate");
        hasObjectLink = factory.createURI(NAMESPACE, "hasObjectLink");
        Link = factory.createURI(NAMESPACE, "Link");
        HardLink = factory.createURI(NAMESPACE, "HardLink");
    }
}
