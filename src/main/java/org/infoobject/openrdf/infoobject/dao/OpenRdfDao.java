package org.infoobject.openrdf.infoobject.dao;

import org.openrdf.repository.Repository;
import org.openrdf.model.URI;
import org.openrdf.model.Statement;
import org.openrdf.model.BNode;
import org.infoobject.openrdf.util.OpenRdfTemplate;
import org.infoobject.core.agent.Agent;

import java.util.List;

/**
 * <p>
 * Class OpenRdfDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:21:07
 */
public abstract class OpenRdfDao<T>{

    private OpenRdfTemplate rdf;

     private String prefix =
            "prefix rdfs:   <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "prefix dc:     <http://purl.org/dc/elements/1.1/> \n" +
            "prefix :       <http://www.magicmap.de/2008/06/infoobject#> \n" +
            "prefix xmlns:  <http://www.w3.org/2001/XMLSchema#> \n" +
            "prefix owl:    <http://www.w3.org/2002/07/owl#> \n" +
            "prefix foaf:   <http://xmlns.com/foaf/0.1/> \n "+
            "prefix skos:   <http://www.w3.org/2004/02/skos/core#> \n";

    public OpenRdfDao(Repository repos) {
        this.rdf = new OpenRdfTemplate();
        this.rdf.setRepository(repos);

    }

    public OpenRdfTemplate getRdfTemplate() {
        return rdf;
    }

    public URI getAgentUri(Agent agent){
        return createUri("agent_sha1:" + agent.getId());
    }
    protected Statement createStatement(BNode taggingNode, URI type, URI tagging) {
        return getRdfTemplate().getValueFactory().createStatement(taggingNode, type,tagging);
    }
    protected URI createUri(String uri){
        return getRdfTemplate().getValueFactory().createURI(uri);
    }

    public String getQueryPrefix() {
        return prefix;
    }


    public void commit() {
       this.getRdfTemplate().commit();
   }
}
