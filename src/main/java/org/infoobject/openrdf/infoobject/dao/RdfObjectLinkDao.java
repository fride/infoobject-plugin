package org.infoobject.openrdf.infoobject.dao;

import org.infoobject.core.infoobject.dao.ObjectLinkDao;
import org.infoobject.core.infoobject.domain.ObjectName;
import org.infoobject.core.infoobject.to.ObjectLinkingTo;
import org.infoobject.core.rdf.vocabulary.DC;
import org.infoobject.core.rdf.vocabulary.InformationObjectVoc;
import org.infoobject.core.util.Digest;
import org.infoobject.openrdf.util.BindingSetMapper;
import org.infoobject.openrdf.util.ConnectionCallback;
import org.infoobject.openrdf.util.RdfException;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class ObjectLinkDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 20:35:08
 */
public class RdfObjectLinkDao extends OpenRdfDao<ObjectLinkingTo> implements ObjectLinkDao {

    private final String linkUri = "http://www.magicmap.de/objectlink/";

    public RdfObjectLinkDao(Repository repos) {
        super(repos);
    }

    /**
     * @param linkingTo
     */
    public void save(final ObjectLinkingTo linkingTo) {
        final ValueFactory factory = getRdfTemplate().getValueFactory();
        final URI objectId = linkingTo.getObject().getNodeUri(factory);
        final URI infoUri = getRdfTemplate().createURI(linkingTo.getUri());
        final URI linkNode = getRdfTemplate().createURI(linkUri, Digest.md5(linkingTo.getUri(), objectId.toString()));
        try {
            getRdfTemplate().withConnection(new ConnectionCallback() {
                public void doInConnection(RepositoryConnection cnx) throws Exception {
                    cnx.remove(linkNode,null,null);
                    cnx.add(linkNode, RDF.TYPE, InformationObjectVoc.ObjectLink);
                    cnx.add(linkNode, InformationObjectVoc.hasInformation, infoUri);
                    cnx.add(linkNode, InformationObjectVoc.hasObject, objectId);
                    cnx.add(linkNode, DC.creator, factory.createURI(linkingTo.getAgentId()));
                    cnx.add(infoUri, InformationObjectVoc.hasObjectLink, linkNode);

                    cnx.add(linkNode, InformationObjectVoc.linkType, factory.createLiteral(linkingTo.getLinkType()));
                }
            });
        } catch (RdfException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param linkingTo
     * @return
     */
    public boolean delete(ObjectLinkingTo linkingTo) {
        final ValueFactory factory = getRdfTemplate().getValueFactory();
        final URI objectId = linkingTo.getObject().getNodeUri(factory);
        final URI linkNode = getRdfTemplate().createURI(linkUri, Digest.md5(linkingTo.getUri(), objectId.toString()));
        final ArrayList<Boolean> deleted =  new ArrayList<Boolean>();
        try {
          getRdfTemplate().withConnection(new ConnectionCallback() {
              public void doInConnection(RepositoryConnection cnx) throws Exception {
                if (cnx.hasStatement(linkNode, RDF.TYPE, InformationObjectVoc.ObjectLink,false)){
                    deleted.add(true);
                    cnx.remove((URI)null, InformationObjectVoc.hasObjectLink, linkNode);
                    cnx.remove(linkNode, null,null);
                }
              }
          });
        } catch (RdfException e) {
            throw new IllegalArgumentException(e);
        }
        return deleted.size() == 1; 
    }

    /**
     * @return
     */
    public List<ObjectLinkingTo> findAll() {
        String query = getQueryPrefix() +
                " select * where {" +
                "   ?link a :ObjectLinking; " +
                "       :hasInformation ?info; " +
                "       :hasObject ?position ;" +
                "       dc:creator ?creator ." +
                "   optional {" +
                "       ?link :linkType ?link_type ." +
                "   }" +
                "}";
        try {
            return getRdfTemplate().queryList(query, new BindingSetMapper<ObjectLinkingTo>() {
                public ObjectLinkingTo map(BindingSet binding, int row) {
                    ObjectLinkingTo objectLinkingTo = new ObjectLinkingTo(
                            binding.getValue("info").stringValue(),
                            ObjectName.fromUri(binding.getValue("position").stringValue()),
                            binding.getValue("link_type").stringValue(),
                            binding.getValue("creator").stringValue());


                    return objectLinkingTo;
                }
            });
        } catch (RdfException e) {
            throw new IllegalStateException(e);
        }
    }

}
