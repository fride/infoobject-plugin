package org.infoobject.magicmap.infoobject.ui.forms;

import net.sf.magicmap.client.utils.AbstractModel;

import javax.swing.*;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import org.infoobject.core.infoobject.model.TaggingPost;
import org.infoobject.core.infoobject.model.InformationObject;
import org.infoobject.core.infoobject.model.Tag;
import org.infoobject.core.agent.Agent;

/**
 * <p>
 * Class InformationObjectForm ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 22:47:08
 */
public class InformationObjectForm extends AbstractModel {
    private InformationMetadataForm informationMetadataForm;
    private InformationTaggingForm taggingForm;
    private ObjectLinkForm objectLinkForm;


    private JComponent form;
    /**
     *
     * @param informationMetadataForm
     * @param taggingForm
     * @param objectLinkForm
     */
    public InformationObjectForm(InformationMetadataForm informationMetadataForm, InformationTaggingForm taggingForm, ObjectLinkForm objectLinkForm) {
        this.informationMetadataForm = informationMetadataForm;
        this.taggingForm = taggingForm;
        this.objectLinkForm = objectLinkForm;
    }

    public JComponent getForm() {
        if (form == null) {
            FormLayout l = new FormLayout("p:grow");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(l);

            b.appendRow("p");
            b.add(informationMetadataForm.getForm(), cc.xy(1,1));

            b.appendUnrelatedComponentsGapRow();
            b.appendRow("p");
            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Tags", taggingForm.getForm());
            tabs.addTab("Vernfüpfung", objectLinkForm.getForm());

            b.add(tabs, cc.xy(1,3));

            form = b.getPanel();
        }
        return form;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();

        TaggingPost[] posts = new TaggingPost[] {
            new TaggingPost(new InformationObject("..."), Agent.ANONYMOUS, Tag.create("Zeitung"), true),
            new TaggingPost(new InformationObject("..."), Agent.ANONYMOUS, Tag.create("Anspruch"), false)
        };

        DefaultComboBoxModel m = new DefaultComboBoxModel(posts);
        InformationTaggingForm taggingForm = new InformationTaggingForm();
        taggingForm.setTagModel(m);



        InformationMetadataForm metadataForm = new InformationMetadataForm();
        metadataForm.setTitleEditable(true);
        metadataForm.setMimeEditable(false);
        
        ObjectLinkForm objectLinkForm = new ObjectLinkForm(m, m, m);

        InformationObjectForm form = new InformationObjectForm(metadataForm, taggingForm, objectLinkForm);
        f.getContentPane().add(form.getForm());
        f.pack();
        f.setVisible(true);
    }

}
