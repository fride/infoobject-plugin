package org.infoobject.openrdf.util;

import org.openrdf.repository.RepositoryConnection;

/**
 * <p>
 * Class ConnectionCallback ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:13:17
 */
public interface ConnectionCallback {
    /**
     *
     * @param cnx
     * @throws Exception
     */
    void doInConnection(RepositoryConnection cnx) throws Exception;
}
