package org.infoobject.openrdf.util;

import org.openrdf.query.BindingSet;

/**
 * <p>
 * Class BindingSetMapper ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:13:05
 */
public interface BindingSetMapper<T> {

    /**
     *
     * @param binding a binding set
     * @param row the current row
     * @return an object for the current row
     */
    T map(BindingSet binding, int row);
}
