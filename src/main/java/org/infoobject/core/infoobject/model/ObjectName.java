package org.infoobject.core.infoobject.model;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;


public class ObjectName {
	private final String name;
    private final String positionServer;

    /**
     *
     * @param name unique node name
     * @param positionServer an uri describing the server.
     */
    public ObjectName(String name, String positionServer) {
		this.name = name;
        this.positionServer = positionServer;
    }

	public String getName() {
		return name;
	}

    public String getPositionServer() {
        return positionServer;
    }

    /**
     *
     * @return returns getPositionServer() + "/nodes/" + getName()
     */
    public String toString() {
		return positionServer + "/" + name;
	}
	/**
	 *
	 * @param name the name
	 * @param positionServer the server
     * @return a name.
	 */
	public static ObjectName positionName(String name, String positionServer) {
		return new ObjectName(name, positionServer);
	}

    public URI getNodeUri(ValueFactory valueFactory) {
        return valueFactory.createURI(toString());
    }

    public URI getServerUri(ValueFactory valueFactory) {
        return valueFactory.createURI(positionServer);
    }

    /**
     *
     * @param uri
     * @return
     */
    public static ObjectName fromUri(String uri) {
        int i = uri.lastIndexOf("/");
        return positionName(uri.substring(i+1),uri.substring(0,i));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectName)) return false;

        ObjectName that = (ObjectName) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (positionServer != null ? !positionServer.equals(that.positionServer) : that.positionServer != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (positionServer != null ? positionServer.hashCode() : 0);
        return result;
    }
}
