package org.infoobject.core.tag.ui;

import org.infoobject.core.infoobject.domain.TaggingPost;
import org.infoobject.core.infoobject.domain.Tag;

import javax.swing.*;
import java.util.Set;
import java.util.HashSet;
import java.awt.*;

/**
 * <p>
 * Class TagginPostCellRenderer ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 22:14:56
 */
public class TagginPostCellRenderer extends DefaultListCellRenderer {

    private Set<String> myPositiveTags = new HashSet<String>();
    private Set<String> myNegativeTags = new HashSet<String>();


    public void addPositiveTag(String tag) {
        this.myPositiveTags.add(tag);
    }
    public void addNegativeTag(String tag) {
        this.myNegativeTags.add(tag);
    }

    public void removeTag(String tag) {
        this.myPositiveTags.remove(tag);
        this.myNegativeTags.remove(tag);
    }
    
    @Override
    public Component getListCellRendererComponent(JList jList, Object data, int row, boolean b, boolean b1) {
        JLabel label = (JLabel) super.getListCellRendererComponent(jList, data, row, b, b1);
        if (data != null) {
            TaggingPost tagging = (TaggingPost)data;
            if (myNegativeTags.contains(tagging.getTag().getNormalizedValue())){
                label.setText(tagging.getTag().getRawValue() + "(-)");
            } else if (myPositiveTags.contains(tagging.getTag().getNormalizedValue())){
                label.setText(tagging.getTag().getRawValue() + "(+)");
            } else {
                label.setText(tagging.getTag().getRawValue());                
            }
        }
        return label;
    }

    public void setMyTags(String[] tagsArray) {
        this.myNegativeTags.clear();
        this.myPositiveTags.clear();

        for (String aTag:tagsArray) {
            aTag = aTag.trim();
            if (aTag.startsWith("--") && aTag.length() > 2){
                myNegativeTags.add(Tag.normalize(aTag.substring(2)));
            }
            else {
                myPositiveTags.add(Tag.normalize(aTag));
            }
        }
    }
}
