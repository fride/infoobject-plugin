package org.infoobject.magicmap.infoobject.ui;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.sf.magicmap.client.utils.AbstractModel;
import net.sf.magicmap.client.utils.DocumentAdapter;
import org.infoobject.core.agent.domain.Agent;
import org.infoobject.core.infoobject.domain.Tag;
import org.infoobject.core.infoobject.domain.TaggingPost;
import org.infoobject.core.infoobject.domain.support.DefaultInformationObject;
import org.infoobject.core.tag.ui.TaggingCellrenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class InformationObjectDetailsForm ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 21:40:05
 */
public class InformationTaggingView extends AbstractModel { 
    private JTextArea tags = new JTextArea(3,3);
    private JList otherTags = new JList();
    private JComponent form;
    private TaggingCellrenderer cellRenderer;

    public InformationTaggingView() {

        new DocumentAdapter(tags){
            public void handleChange(String s) {
                checkTags(s);
            }
        };
        cellRenderer = new TaggingCellrenderer();
        this.otherTags.setCellRenderer(cellRenderer);
    }

    
    public void setTagModel(ComboBoxModel listModel) {
        otherTags.setModel(listModel);
    }

    private void checkTags(String tagsString) {
        String[] tagsArray = tagsString != null &&
                tagsString.contains(",") ? tagsString.split(",") :
                new String[]{tagsString};
        cellRenderer.setMyTags(tagsArray);
        otherTags.repaint();

    }

    public JComponent getView() {
        if (form == null) {
            FormLayout l = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(l);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("fill:p:grow");
            
            b.appendRow("p");
            b.add(new JLabel("Meine Tags"), cc.xy(1, b.getRowCount()));
            b.add(new JScrollPane(tags), cc.xy(3, b.getRowCount()));


            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(new JLabel("Alle Tags"), cc.xy(1, b.getRowCount()));
            b.add(new JScrollPane(otherTags), cc.xy(3, b.getRowCount()));

            form = b.getPanel();
        }
        return form;
    }

    public List<String> getTags(){
        String tagText = this.tags.getText();
        ArrayList<String> tagArray = new ArrayList<String>();
        if (tagText != null && tagText.length() > 1) {
            for (String str: tagText.split(",")){
                tagArray.add(str.trim());
            }
        }
        return  tagArray;
    }






    public static void main(String[] args) {
        JFrame f = new JFrame();

        TaggingPost[] posts = new TaggingPost[] {
            new TaggingPost(new DefaultInformationObject("..."), Agent.ANONYMOUS, Tag.create("Zeitung"), true),
            new TaggingPost(new DefaultInformationObject("..."), Agent.ANONYMOUS, Tag.create("Anspruch"), false)
        };

        DefaultComboBoxModel m = new DefaultComboBoxModel(posts);
        InformationTaggingView detailsView = new InformationTaggingView();
        detailsView.setTagModel(m);
        JComponent component = detailsView.getView();
        f.getContentPane().add(component);
        f.pack();
        f.setVisible(true);
        
    }
}
