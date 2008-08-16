package org.infoobject.openrdf.util;

import org.openrdf.model.*;
import org.openrdf.query.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.LinkedList;


import org.openrdf.query.TupleQueryResult;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryConnection;

/**
 *
 */
public class OpenRdfTemplate{
    protected Repository repository;
    private final ThreadLocal<RepositoryConnection> connection = new ThreadLocal<RepositoryConnection>();

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void add(Iterable<Statement> statements) throws RdfException{
        RepositoryConnection repositoryConnection = null;
        try {
            repositoryConnection = getConnection();
            repositoryConnection.setAutoCommit(false);
            repositoryConnection.add(statements);
        } catch (RepositoryException e) {
            throw new RdfException(e);
        }
    }

    public void withConnection(ConnectionCallback clb) throws RdfException {
        RepositoryConnection cnx = null;
        try{
            cnx = getConnection();
            clb.doInConnection(cnx);
        } catch (Exception ex){
            throw new RdfException(ex);
        }
    }

    public RepositoryConnection getConnection(){
        if (this.connection.get() == null){
            try {
                this.connection.set(repository.getConnection());
                this.connection.get().setAutoCommit(false);
            } catch (RepositoryException e) {
                throw new IllegalStateException(e);
            }
        }
        return connection.get();
    }
    /**
     *
     * @param queryString
     * @param mapper
     * @return
     * @throws RdfException
     */
    public<T> List<T> queryList(String queryString, BindingSetMapper<T> mapper) throws RdfException{
        RepositoryConnection repositoryConnection = null;
        TupleQueryResult result = null;
        List<T> objects = new LinkedList<T>();
        try{
            repositoryConnection = getConnection();
            TupleQuery query = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            result = query.evaluate();
            int row = 0;
            while (result.hasNext()){
                final T mapped = mapper.map(result.next(), row++);
                if (mapped != null){
                    objects.add(mapped);
                }
            }
        } catch (RepositoryException e) {
            throw new RdfException(e);
        } catch (MalformedQueryException e) {
            throw new RdfException(e);
        } catch (QueryEvaluationException e) {
            throw new RdfException(e);
        } finally{
            cleanup(result);
        }
        return objects;
    }

    public void commit(){
        if (this.connection.get() ==  null) throw new IllegalStateException("No Connection to commit!");
        else {
            try {
                this.connection.get().commit();
                this.connection.get().close();
                this.connection.set(null);
            } catch (RepositoryException e) {
                throw new IllegalStateException(e);
            }
        }
    }
    public ValueFactory getValueFactory() {
        return repository.getValueFactory();
    }


    public URI createURI(String s) {
        return getValueFactory().createURI(s);
    }

    public URI createURI(String s, String s1) {
        return getValueFactory().createURI(s, s1);
    }

    public BNode createBNode() {
        return getValueFactory().createBNode();
    }

    public BNode createBNode(String s) {
        return getValueFactory().createBNode(s);
    }

    public Literal createLiteral(String s) {
        return getValueFactory().createLiteral(s);
    }

    public Literal createLiteral(String s, String s1) {
        return getValueFactory().createLiteral(s, s1);
    }

    public Literal createLiteral(String s, URI uri) {
        return getValueFactory().createLiteral(s, uri);
    }

    public Literal createLiteral(boolean b) {
        return getValueFactory().createLiteral(b);
    }

    public Literal createLiteral(byte b) {
        return getValueFactory().createLiteral(b);
    }

    public Literal createLiteral(short i) {
        return getValueFactory().createLiteral(i);
    }

    public Literal createLiteral(int i) {
        return getValueFactory().createLiteral(i);
    }

    public Literal createLiteral(long l) {
        return getValueFactory().createLiteral(l);
    }

    public Literal createLiteral(float v) {
        return getValueFactory().createLiteral(v);
    }

    public Literal createLiteral(double v) {
        return getValueFactory().createLiteral(v);
    }

    public Literal createLiteral(XMLGregorianCalendar xmlGregorianCalendar) {
        return getValueFactory().createLiteral(xmlGregorianCalendar);
    }

    public Statement createStatement(Resource resource, URI uri, Value value) {
        return getValueFactory().createStatement(resource, uri, value);
    }

    public Statement createStatement(Resource resource, URI uri, Value value, Resource resource1) {
        return getValueFactory().createStatement(resource, uri, value, resource1);
    }

    private void cleanup(TupleQueryResult result) {
        try{
            if (result != null){
                result.close();
            }
        }
        catch(Exception ex){
            throw new IllegalStateException(ex);
        }
    }

}
