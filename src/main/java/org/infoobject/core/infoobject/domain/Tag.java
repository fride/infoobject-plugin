package org.infoobject.core.infoobject.domain;
import net.sf.json.JSONObject;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.util.*;

public class Tag implements Comparable<Tag>{
    private final String rawValue;
    private String normalizedValue;
    private Set<Tagging> taggings = new HashSet<Tagging>();
    private static final Map<String, Tag> instanceMap = new HashMap<String, Tag>();
    private static URLCodec codec = new URLCodec();

    /**
     * @param rawValue the raw value. "Ein dooles Ding" eg.
     */
    private Tag(String rawValue) {
        this.rawValue = rawValue;
        this.normalizedValue = normalize(rawValue);
    }


    /**
     *
     * @return
     */
    public String getRawValue() {
        return rawValue;
    }

    /**
     *
     * @return
     */
    public String getNormalizedValue() {
        return normalizedValue;
    }

    /**
     *
     * @param rawValue
     * @return
     */
    public static String normalize(String rawValue) {
        try {
            return codec.encode(rawValue.trim().toLowerCase());
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        if (normalizedValue != null ? !normalizedValue.equals(tag.normalizedValue) : tag.normalizedValue != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (normalizedValue != null ? normalizedValue.hashCode() : 0);
    }

    public int compareTo(Tag tag) {
        return tag != null ? getNormalizedValue().compareTo(tag.getNormalizedValue()) : 1;
    }

    
    public List<Tagging> getTags() {
        return new ArrayList<Tagging>(this.taggings);
    }

    public int getWeight(){
        return taggings.size();
    }
    public boolean remove(Tagging tagging) {
        return this.taggings.remove(tagging);
    }

    public static Tag create(String tag) {
        String key = normalize(tag);
        Tag tagInstance = instanceMap.get(key);
        if (tagInstance == null) {
            tagInstance = new Tag(tag);
            instanceMap.put(key,tagInstance);
        }
        return tagInstance;
    }

    void addTagging(Tagging tagging) {
        this.taggings.remove(tagging);
        this.taggings.add(tagging);

    }
}
