package org.infoobject.core.infoobject.to;

import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 * Class InformationData ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 07.08.2008
 *         Time: 01:23:14
 */
public class InformationMetadataTo {
    private String uri;
    private String mimeType;
    private String encoding;
    private String title;
    private long size;
    private Set<String> facets = new HashSet<String>();
    private long version;

    public InformationMetadataTo(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

   

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Set<String> getFacets() {
        return facets;
    }

    public void setFacets(Set<String> facets) {
        this.facets = facets;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformationMetadataTo)) return false;

        InformationMetadataTo that = (InformationMetadataTo) o;

        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    public int hashCode() {
        return (uri != null ? uri.hashCode() : 0);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
