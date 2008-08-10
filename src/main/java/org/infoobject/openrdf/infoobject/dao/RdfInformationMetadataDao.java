package org.infoobject.openrdf.infoobject.dao;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.model.URI;
import org.openrdf.model.Literal;
import org.infoobject.core.rdf.vocabulary.InformationObjectVoc;
import org.infoobject.core.rdf.vocabulary.DC;
import org.infoobject.openrdf.util.ConnectionCallback;
import org.infoobject.openrdf.util.RdfException;
import org.infoobject.core.infoobject.to.InformationMetadataTo;
import org.infoobject.core.infoobject.dao.InformationMetadataDao;

import java.util.List;

/**
 * <p>
 * Class InformationMetaDao ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 21:06:50
 */
public class RdfInformationMetadataDao extends OpenRdfDao<InformationMetadataTo> implements InformationMetadataDao {
    public RdfInformationMetadataDao(Repository repos) {
        super(repos);
    }

    /**
     * @param data
     */
    public void save(final InformationMetadataTo data) {
        final URI infoUri = getRdfTemplate().createURI(data.getUri());
        final Literal crawlDate = getRdfTemplate().createLiteral(System.currentTimeMillis());
        try {
            getRdfTemplate().withConnection(new ConnectionCallback() {
                public void doInConnection(RepositoryConnection cnx) throws Exception {
                    if (data.getTitle() != null){
                        cnx.add(infoUri, DC.Title, getRdfTemplate().createLiteral(data.getTitle()));
                    }
                    cnx.add(infoUri, InformationObjectVoc.size, getRdfTemplate().createLiteral(data.getSize()));
                    cnx.add(infoUri, DC.Format, getRdfTemplate().createLiteral(data.getMimeType()));
                    cnx.add(infoUri, InformationObjectVoc.crawlDate, crawlDate);
                }
            });
        } catch (RdfException e) {
            throw new IllegalArgumentException(e);
        }


    }


    /**
     * @param uri
     * @return
     */
    public InformationMetadataTo load(String uri) {
        return null;
    }

    /**
     * @return
     */
    public List<InformationMetadataTo> findAll() {
        return null;
    }
}
