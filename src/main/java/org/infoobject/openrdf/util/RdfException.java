package org.infoobject.openrdf.util;

/**
 * <p>
 * Class RdfException ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:13:43
 */
public class RdfException extends Exception{
    public RdfException() {
    }

    public RdfException(String s) {
        super(s);
    }

    public RdfException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RdfException(Throwable throwable) {
        super(throwable);
    }
}
