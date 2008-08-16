package org.infoobject.core.rdf;

import org.openrdf.model.*;
import org.openrdf.model.util.GraphUtil;
import org.openrdf.model.util.GraphUtilException;
import org.infoobject.core.rdf.vocabulary.DC;

import java.util.Iterator;
import java.util.Collection;

/**
 * <p>
 * Class ExtractorMetadata ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 11:14:23
 */
public class RdfContainer implements Graph {
    private final Graph delegate;
    private final URI subject;

    public RdfContainer(Graph delegate, String seubject) {
        this.delegate = delegate;
        this.subject = delegate.getValueFactory().createURI(seubject);
    }

    public URI getSubject() {
        return subject;
    }

    public void setTitle(String title) {
        GraphUtil.setUniqueObject(delegate, getSubject(), DC.Title, getValueFactory().createLiteral(title));
    }

    public String getUniqeObjectString(URI type) {
        return getUniqeObjectString(type, "");
    }
    public String getUniqeObjectString(URI type, String defaultValue) {
        try {
            Value object = getUniqueObject(type);
            return object != null ? object.stringValue() : null;
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public Value getUniqueObject(URI type) {
        try {
            return GraphUtil.getUniqueObject(delegate, getSubject(), type);
        } catch (GraphUtilException e) {
            throw new IllegalArgumentException("Property ist not unique");
        }
    }

    public Literal getUniqueProperty(URI type) {
        try {
            return GraphUtil.getUniqueObjectLiteral(delegate, getSubject(), type);
        } catch (GraphUtilException e) {
            throw new IllegalArgumentException("Property ist not unique");
        }
    }

    public URI getUniquePropertyUri(URI type) {
        try {
            return GraphUtil.getUniqueObjectURI(delegate, getSubject(), type);
        } catch (GraphUtilException e) {
            throw new IllegalArgumentException("Property ist not unique");
        }
    }

    public Resource getUniquePropertyResource(URI type) {
        try {
            return GraphUtil.getUniqueObjectResource(delegate, getSubject(), type);
        } catch (GraphUtilException e) {
            throw new IllegalArgumentException("Property ist not unique");
        }
    }

    public void setUniqueProperty(URI type, String value) {
        GraphUtil.setUniqueObject(delegate, getSubject(), type, getValueFactory().createLiteral(value));
    }

    public void setUniqueProperty(URI type, String value, String lang) {
        GraphUtil.setUniqueObject(delegate, getSubject(), type, getValueFactory().createLiteral(value, lang));
    }

    public void setUniqueProperty(URI type, long value) {
        GraphUtil.setUniqueObject(delegate, getSubject(), type, getValueFactory().createLiteral(value));
    }

    public void setUniqueProperty(URI type, boolean value) {
        GraphUtil.setUniqueObject(delegate, getSubject(), type, getValueFactory().createLiteral(value));
    }

    public void setProperty(URI type, String value) {
        delegate.add(getSubject(),type, getValueFactory().createLiteral(value));
    }

    public void setProperty(URI type, String value, String lang) {
        delegate.add(getSubject(),type, getValueFactory().createLiteral(value,lang));
    }

    public void setProperty(URI type, long value) {
        delegate.add(getSubject(),type, getValueFactory().createLiteral(value));
    }

    public void setProperty(URI type, boolean value) {
        delegate.add(getSubject(),type, getValueFactory().createLiteral(value));
    }

    public ValueFactory getValueFactory() {
        return delegate.getValueFactory();
    }

    public boolean add(Resource resource, URI uri, Value value, Resource... resources) {
        return delegate.add(resource, uri, value, resources);
    }

    public Iterator<Statement> match(Resource resource, URI uri, Value value, Resource... resources) {
        return delegate.match(resource, uri, value, resources);
    }

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    public Iterator<Statement> iterator() {
        return delegate.iterator();
    }

    public Object[] toArray() {
        return delegate.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return delegate.toArray(ts);
    }

    public boolean add(Statement statement) {
        return delegate.add(statement);
    }

    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return delegate.containsAll(objects);
    }

    public boolean addAll(Collection<? extends Statement> statements) {
        return delegate.addAll(statements);
    }

    public boolean removeAll(Collection<?> objects) {
        return delegate.removeAll(objects);
    }

    public boolean retainAll(Collection<?> objects) {
        return delegate.retainAll(objects);
    }

    public void clear() {
        delegate.clear();
    }

    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    public int hashCode() {
        return delegate.hashCode();
    }
}
