package org.infoobject.core.rdf.vocabulary;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 *
 *
 */
public class DC {
    public static final String NS = "http://purl.org/dc/elements/1.1/";

    public static final URI Title;
    public static final URI Type;
    public static final URI Format;
    public static final URI Language;
    public static final URI creator;

    static{
        final ValueFactory factory = new ValueFactoryImpl();
        Title = factory.createURI(NS,"title");
        Type = factory.createURI(NS,"type");
        Format = factory.createURI(NS,"format");
        Language = factory.createURI(NS,"language");
        creator = factory.createURI(NS,"creator");
    }
}