package org.infoobject.openrdf.infoobject;

import org.infoobject.core.infoobject.*;
import org.infoobject.core.infoobject.model.InformationObjectModel;
import org.infoobject.core.infoobject.model.InformationObjectRepository;
import org.infoobject.core.infoobject.model.ObjectName;
import org.infoobject.core.infoobject.to.InformationObjectTo;
import org.infoobject.core.infoobject.to.TaggingTo;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.agent.AgentManager;
import org.infoobject.openrdf.infoobject.dao.*;
import org.infoobject.openrdf.util.RdfException;
import org.infoobject.openrdf.util.OpenRdfTemplate;
import org.infoobject.openrdf.util.BindingSetMapper;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.query.BindingSet;
import org.openrdf.sail.memory.MemoryStore;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.model.Literal;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>
 * Class RdfInformationObjectManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:55:30
 */
public class RdfInformationObjectRepository extends InformationObjectRepository {
    private final Repository repos;
    private final OpenRdfTemplate rdfTemplate;
    static StringTemplateGroup templates = new StringTemplateGroup("sparql");

    // org.infoobject.template
    public RdfInformationObjectRepository(Repository repos) {
        super(new RdfTaggingDao(repos),new RdfInformationMetadataDao(repos),new RdfObjectLinkDao(repos));
        this.repos = repos;
        rdfTemplate = new OpenRdfTemplate();
        rdfTemplate.setRepository(repos);
    }

    /**
     *
     * @param name
     * @return
     */

    public List<InformationObjectTo> findInformationsByObject(ObjectName name) {

        // Findet es im plugin nicht! scheisse!
        //StringTemplate objectTemplate = templates.getInstanceOf("/org/infoobject/template/sparql/infoobjects");
        String query = "prefix rdfs:   <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "prefix dc:     <http://purl.org/dc/elements/1.1/>\n" +
                "prefix :       <http://www.magicmap.de/2008/06/infoobject#>\n" +
                "prefix xmlns:       <http://www.w3.org/2001/XMLSchema#>\n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
                "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "\n" +
                "select * where {\n" +
                "    ?filter_link a :ObjectLinking ;\n" +
                "        :hasInformation ?info ;\n" +
                "        :hasObject <$POSITION$> .\n" +
                "\n" +
                "    ?info :hasObjectLink ?link  .\n" +
                "    optional { ?info dc:creator ?info_creator .}\n" +
                "    optional { ?info dc:title ?info_title ; :size ?info_size ; dc:format ?mime}\n" +
                "\n" +
                "    ?link :hasObject ?linked_object ; dc:creator ?link_creator .\n" +
                "\n" +
                "    optional { ?link :linkType ?link_type .}\n" +
                "    optional { ?tagging :tags ?info ; :tags ?tagged ; :isPositive ?positive ; dc:creator ?tag_creator ; :hasTag ?tag . ?tag :rawTag ?raw}\n" +
                "\n" +
                "}";

        //objectTemplate.setAttribute("POSITION", name.toString());
        //String query = objectTemplate.toString();
        query = query.replace("$POSITION$", name.toString());
        System.out.println("query = " + query);


        final InformationObjectToTransformer tr = new InformationObjectToTransformer();
        try {
            final List<InformationObjectTo> list = rdfTemplate.queryList(query, new BindingSetMapper<InformationObjectTo>() {
                public InformationObjectTo map(BindingSet binding, int row) {
                    System.out.println(
                            "linked_object = " + binding.getValue("linked_object") + " => " +
                                    binding.getValue("info").stringValue());

                    return tr.transform(binding);
                }
            });
            return list;
        } catch (RdfException e) {
            throw new IllegalStateException(e);
        }
    }


    public InformationObjectTo loadInformations(final String uri){
        StringTemplate objectTemplate = templates.getInstanceOf("org/infoobject/template/sparql/infoobject");
        objectTemplate.setAttribute("URI", "<" + uri + ">");
        final InformationObjectTo info = new InformationObjectTo(uri);
        final ObjectLinkTransformer objectLinkTransformer = new ObjectLinkTransformer();
        final TaggingTransformer taggingTransformer = new TaggingTransformer();

        System.out.println("objectTemplate.toString() = " + objectTemplate.toString());
        try {
            rdfTemplate.queryList(objectTemplate.toString(), new BindingSetMapper<InformationObjectTo>() {
                public InformationObjectTo map(BindingSet bindingSet, int row) {
                    if (row == 1){
                        info.setMetadata(new InformationMetadataTo(uri));
                        if (bindingSet.hasBinding("info_title")){
                            info.getMetadata().setTitle(bindingSet.getValue("info_title").stringValue());
                        }
                        if (bindingSet.hasBinding("info_size")){
                            info.getMetadata().setSize(((Literal)bindingSet.getValue("info_size")).longValue());
                        }
                        if (bindingSet.hasBinding("mime")){
                            info.getMetadata().setMimeType(bindingSet.getValue("mime").stringValue());
                        }
                    }
                    info.getObjectLinkings().add(objectLinkTransformer.transform(bindingSet));
                    info.getTaggings().add(taggingTransformer.transform(bindingSet));
                    return null;
                }
            });
        } catch (RdfException e) {
            e.printStackTrace();
        }
        return info;
    }
    @Override
    protected RdfTaggingDao getTaggingDao() {
        return (RdfTaggingDao)super.getTaggingDao();
    }

    @Override
    protected RdfInformationMetadataDao getInformationMetadataDao() {
        return (RdfInformationMetadataDao) super.getInformationMetadataDao();
    }

    @Override
    protected RdfObjectLinkDao getObjectLinkDao() {
        return (RdfObjectLinkDao) super.getObjectLinkDao();
    }

    public static void main(String[] args) throws RepositoryException, RDFHandlerException {
        Repository r = new SailRepository(new MemoryStore());
        r.initialize();
        RdfInformationObjectRepository repos = new RdfInformationObjectRepository(r);

        InformationObjectManager manager = new InformationObjectManager(new InformationObjectModel(), repos, new AgentManager());

        InformationMetadataTo meta = new InformationMetadataTo("http://www.heise.de");
        meta.setTitle("Gomen und so");
        meta.setMimeType("text/html");
        meta.setEncoding("");
        repos.saveInformationMetadata(Collections.singletonList(meta));
        ArrayList<TaggingTo> dao = new ArrayList<TaggingTo>();
        dao.add(new TaggingTo("http://www.heise.de", "Zeitung", true, "jabber:jan@googlemail.com"));
        dao.add(new TaggingTo("http://www.heise.de", "Zeitschrift", true, "jabber:jan@googlemail.com"));
        dao.add(new TaggingTo("http://www.heise.de", "Online", true, "jabber:jan@googlemail.com"));
        dao.add(new TaggingTo("http://www.heise.de", "Deutsch", true, "jabber:jan@googlemail.com"));
        dao.add(new TaggingTo("http://www.heise.de", "Trollwiese", true, "jabber:jan@googlemail.com"));
        dao.add(new TaggingTo("http://www.heise.de", "IT", true, "jabber:jan@googlemail.com"));

        dao.add(new TaggingTo("http://www.heise.de", "Zeitschrift", false, "jabber:niemand@googlemail.com"));
        dao.add(new TaggingTo("http://www.heise.de", "Trollwiese", true, "jabber:niemand@googlemail.com"));


        dao.add(new TaggingTo("http://www.golem.de", "Zeitschrift", false, "jabber:niemand@googlemail.com"));
        dao.add(new TaggingTo("http://www.golem.de", "Trollwiese", true, "jabber:niemand@googlemail.com"));
        dao.add(new TaggingTo("http://www.golem.de", "IT", true, "jabber:niemand@googlemail.com"));

        dao.add(new TaggingTo("http://friderici.net", "IT", true, "jabber:niemand@googlemail.com"));
        dao.add(new TaggingTo("http://friderici.net", "Java", true, "jabber:jan@googlemail.com"));
        dao.add(new TaggingTo("http://friderici.net", "Len", true, "jabber:kerstin@googlemail.com"));
        dao.add(new TaggingTo("http://friderici.net", "Jabba", true, "jabber:len@googlemail.com"));

        repos.saveTaggings(dao);

        ObjectLinkingTo link = new ObjectLinkingTo(
                "http://friderici.net",
                ObjectName.positionName("client2", "http:/www.de"),
                "homepage",
                "jabber:jan@googlemail.com");

        repos.saveObjectLinkings(Collections.singletonList(link));


        repos.findInformationsByObject(link.getObject());

        manager.load(link.getObject());
        manager.explode("http://friderici.net");

        r.getConnection().export(new N3Writer(System.out));



        System.out.println("link = " + manager.getModel().getInformationObject("http://friderici.net"));
    }




}
