package org.infoobject.core.infoobject.to;

import net.sf.json.JSONObject;

/**
 *
 */
public class TaggingTo {

    private String tagged;
    private String tag;
    private boolean positive;
    private String agentId;


    public TaggingTo(String tagged, String tag, boolean positive, String agentid) {
        this.tagged = tagged;
        this.tag = tag;
        this.positive = positive;
        this.agentId = agentid;
    }


    public String getTagged() {
        return tagged;
    }

    public String getTag() {
        return tag;
    }

    public boolean isPositive() {
        return positive;
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
        if (!(o instanceof TaggingTo)) return false;

        TaggingTo tagging = (TaggingTo) o;

        if (agentId != null ? !agentId.equals(tagging.agentId) : tagging.agentId != null) return false;
        if (tag != null ? !tag.equals(tagging.tag) : tagging.tag != null) return false;
        if (tagged != null ? !tagged.equals(tagging.tagged) : tagging.tagged != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (tagged != null ? tagged.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        return result;
    }
}
