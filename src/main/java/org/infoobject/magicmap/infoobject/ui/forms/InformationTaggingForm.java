package org.infoobject.magicmap.infoobject.ui.forms;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import net.sf.magicmap.client.utils.AbstractModel;
import net.sf.magicmap.client.utils.DocumentAdapter;
import org.infoobject.core.agent.Agent;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.infoobject.model.Tag;
import org.infoobject.core.infoobject.model.TaggingPost;
import org.infoobject.magicmap.tag.ui.TagginPostCellRenderer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
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
public class InformationTaggingForm extends AbstractModel { 
    private JTextArea tags = new JTextArea(3,3);
    private JList otherTags = new JList();
    private JComponent form;
    private TagginPostCellRenderer cellRenderer;

    public InformationTaggingForm() {

        new DocumentAdapter(tags){
            public void handleChange(String s) {
                checkTags(s);
            }
        };
        cellRenderer = new TagginPostCellRenderer();
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

    public JComponent getForm() {
        if (form == null) {
            FormLayout l = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(l);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p:grow");
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p");

            
            b.appendRow("p");
            b.add(new JLabel("Meine Tags"), cc.xy(1, b.getRowCount()));
            b.add(new JScrollPane(tags), cc.xyw(3, b.getRowCount(),3));


            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(new JLabel("Alle Tags"), cc.xy(1, b.getRowCount()));
            b.add(new JScrollPane(otherTags), cc.xyw(3, b.getRowCount(),3));

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
            new TaggingPost(new InformationObject("..."), Agent.ANONYMOUS, Tag.create("Zeitung"), true),
            new TaggingPost(new InformationObject("..."), Agent.ANONYMOUS, Tag.create("Anspruch"), false)
        };

        DefaultComboBoxModel m = new DefaultComboBoxModel(posts);
        InformationTaggingForm detailsForm = new InformationTaggingForm();
        detailsForm.setTagModel(m);
        JComponent component = detailsForm.getForm();
        f.getContentPane().add(component);
        f.pack();
        f.setVisible(true);
        
    }
}
