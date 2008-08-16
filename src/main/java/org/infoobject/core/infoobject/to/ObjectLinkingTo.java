package org.infoobject.core.infoobject.to;

import net.sf.json.JSONObject;

import org.infoobject.core.infoobject.domain.ObjectName;

/**
 * <p>
 * Class ObjectLink ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 01:19:53
 */
public class ObjectLinkingTo {
    private String uri;
    private ObjectName object;
    private String linkType;
    private String agentId;

    public ObjectLinkingTo(String uri, ObjectName object, String linkType, String agentId) {
        this.uri = uri;
        this.object = object;
        this.linkType = linkType;
        this.agentId = agentId;
    }

    public String getUri() {
        return uri;
    }

    public ObjectName getObject() {
        return object;
    }

    public String getLinkType() {
        return linkType;
    }

    public String getAgentId() {
        return agentId;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectLinkingTo)) return false;

        ObjectLinkingTo that = (ObjectLinkingTo) o;

        if (agentId != null ? !agentId.equals(that.agentId) : that.agentId != null) return false;
        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        return result;
    }
}
