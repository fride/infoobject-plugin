package org.infoobject.openrdf.infoobject.dao;

import org.infoobject.core.infoobject.dao.InformationMetadataDao;
import org.infoobject.core.infoobject.to.MetadataTo;
import org.infoobject.openrdf.util.ConnectionCallback;
import org.infoobject.openrdf.util.RdfException;
import org.openrdf.model.Statement;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import java.util.Iterator;
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
public class RdfInformationMetadataDao extends OpenRdfDao<MetadataTo> implements InformationMetadataDao {
    public RdfInformationMetadataDao(Repository repos) {
        super(repos);
    }

    /**
     * @param data
     */
    public void save(final MetadataTo data) {
        try {
            getRdfTemplate().withConnection(new ConnectionCallback() {
                public void doInConnection(RepositoryConnection cnx) throws Exception {
                    final Iterator<Statement> statementIterator = data.getMetadata().match(data.getMetadata().getSubject(), null, null);
                    while (statementIterator.hasNext()) {
                        cnx.add(statementIterator.next());
                    }
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
    public MetadataTo load(String uri) {
        return null;
    }

    /**
     * @return
     */
    public List<MetadataTo> findAll() {
        return null;
    }
}
