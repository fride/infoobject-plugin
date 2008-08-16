package org.infoobject.openrdf.infoobject.dao;

import org.openrdf.model.*;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.sail.memory.MemoryStore;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.domain.Tag;
import org.infoobject.core.infoobject.dao.TaggingDao;
import org.infoobject.core.util.Digest;
import org.infoobject.openrdf.util.ConnectionCallback;
import org.infoobject.openrdf.util.RdfException;
import org.infoobject.openrdf.util.BindingSetMapper;
import org.infoobject.core.rdf.vocabulary.InformationObjectVoc;
import org.infoobject.core.rdf.vocabulary.DC;

import java.util.List;
import java.util.Collections;

/**
 * <p>
 * Class TaggingDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 19:23:35
 */
public class RdfTaggingDao extends OpenRdfDao<TaggingTo> implements TaggingDao {


    private final String tagUri = "http://www.magicmap.de/tag/";
    private final String taggingUri = "http://www.magicmap.de/tagging/";

    public RdfTaggingDao(Repository repos) {
        super(repos);
    }

    /**
     * @param tagging
     */
    public void save(final TaggingTo tagging) {
        final ValueFactory valueFactory = getRdfTemplate().getValueFactory();
        final String normalizedTag = Tag.normalize(tagging.getTag());
        final String id = Digest.sha1(tagging.getTagged(), tagging.getAgentId(), normalizedTag);
        final URI  node = getRdfTemplate().createURI("tagging:", id);
        final URI tagUri = createtagUri(normalizedTag);
        final URI subjectUri = valueFactory.createURI(tagging.getTagged());

        try {
            getRdfTemplate().withConnection(new ConnectionCallback() {
                public void doInConnection(RepositoryConnection cnx) throws Exception {
                    if (!cnx.hasStatement(tagUri, RDF.TYPE, InformationObjectVoc.Tag,false)){
                        cnx.add(tagUri, RDF.TYPE, InformationObjectVoc.Tag);
                        cnx.add(tagUri, InformationObjectVoc.rawTag, valueFactory.createLiteral(normalizedTag));
                    }
                    cnx.remove(node,null,null);
                    cnx.add(node, RDF.TYPE, InformationObjectVoc.Tagging);
                    cnx.add(node, InformationObjectVoc.tags, subjectUri);
                    cnx.add(node, InformationObjectVoc.isPositive, valueFactory.createLiteral(tagging.isPositive()));
                    cnx.add(node, InformationObjectVoc.hasTag, tagUri);
                    cnx.add(node, DC.creator, valueFactory.createURI(tagging.getAgentId()));
                }
            });
        } catch (RdfException e) {
            e.printStackTrace();
        }
    }



    protected Statement createStatement(BNode taggingNode, URI type, URI tagging) {
        return getRdfTemplate().getValueFactory().createStatement(taggingNode, type,tagging);
    }

    public List<Tag> findTags(){
        try {
            return getRdfTemplate().queryList(getQueryPrefix() + " select * where {?id a :Tag; :tagRaw ?raw}", new BindingSetMapper<Tag>() {
                public Tag map(BindingSet binding, int row) {
                    return Tag.create(binding.getValue("raw").stringValue());
                }
            });
        } catch (RdfException e) {
            throw new IllegalStateException(e);
        }

    }
    /**
     * @return
     */
    public List<TaggingTo> findAll() {
        return Collections.emptyList();
    }

    public List<TaggingTo> findAllByUri(String uri){
        String query = getQueryPrefix() +
                " select * where { ?tagging :tags <$URI$> ; :tags ?tagged ; :isPositive ?positive ; dc:creator ?agentid ; :hasTag ?tag . }".replace("$URI$", uri);
                //" select * where { ?tagging :tags <$URI$> ; a :Tagging ; :tags ?tagged ; :isPositive ?positive ; dc:creator ?agentid ; :hasTag ?tag . }".replace("$URI$", uri);
        return doList(query);
    }

    private List<TaggingTo> doList(String query) {
        System.out.println("queryList = " + query);
        try {
            return getRdfTemplate().queryList(query, new BindingSetMapper<TaggingTo>() {
                public TaggingTo map(BindingSet binding, int row) {
                    String tagged = binding.getBinding("tagged").getValue().stringValue();
                    String agentid = binding.getBinding("agentid").getValue().stringValue();
                    String tag = binding.getBinding("tag").getValue().stringValue();
                    boolean positive = ((Literal)binding.getBinding("positive").getValue()).booleanValue();

                    TaggingTo tagging = new TaggingTo(tagged, tag, positive, agentid);
                    return tagging;
                }
            });
        } catch (RdfException e) {
            throw new IllegalStateException(e);
        }
    }

    public URI createtagUri(String tag){
        return getRdfTemplate().getValueFactory().createURI(tagUri, tag.replace(" ", "_").toLowerCase());
    }

    public URI getTagNamespaceUri(){
        return getRdfTemplate().getValueFactory().createURI(tagUri);
    }
    public static void main(String[] args) throws RepositoryException, RDFHandlerException {
        Repository repos = new SailRepository(new MemoryStore());
        repos.initialize();
        RdfTaggingDao dao = new RdfTaggingDao(repos);
        dao.save(new TaggingTo("http://www.heise.de", "Zeitung", true, "jabber:jan@googlemail.com"));
        dao.save(new TaggingTo("http://www.heise.de", "Zeitschrift", true, "jabber:jan@googlemail.com"));
        dao.save(new TaggingTo("http://www.heise.de", "Online", true, "jabber:jan@googlemail.com"));
        dao.save(new TaggingTo("http://www.heise.de", "Deutsch", true, "jabber:jan@googlemail.com"));
        dao.save(new TaggingTo("http://www.heise.de", "Trollwiese", true, "jabber:jan@googlemail.com"));
        dao.save(new TaggingTo("http://www.heise.de", "IT", true, "jabber:jan@googlemail.com"));

        dao.save(new TaggingTo("http://www.heise.de", "Zeitschrift", false, "jabber:niemand@googlemail.com"));
        dao.save(new TaggingTo("http://www.heise.de", "Trollwiese", true, "jabber:niemand@googlemail.com"));


        dao.save(new TaggingTo("http://www.golem.de", "Zeitschrift", false, "jabber:niemand@googlemail.com"));
        dao.save(new TaggingTo("http://www.golem.de", "Trollwiese", true, "jabber:niemand@googlemail.com"));
        dao.save(new TaggingTo("http://www.golem.de", "IT", true, "jabber:niemand@googlemail.com"));



        repos.getConnection().export(new N3Writer(System.out));
        System.out.println("dao = " + dao.findAllByUri("http://www.golem.de"));
    }

}
