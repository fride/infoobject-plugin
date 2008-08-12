package org.infoobject.magicmap.infoobject.ui.forms;

import net.sf.magicmap.client.utils.AbstractModel;
import net.sf.magicmap.client.utils.DocumentAdapter;

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


    private JTextField uriField = new JTextField(10);
    private JTextField mimeField = new JTextField();
    private JList facets = new JList();
    private String uri;
    private String mime;

    private JComponent form;

    public InformationObjectForm() {
        new DocumentAdapter(uriField){
            public void handleChange(String s) {
                setUri(s);
            }
        };
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        if (this.uri == null || !this.uri.equals(uri)) {
            String old = this.uri;
            this.uri = uri;
            firePropertyChange("uri", old,uri);
        }
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        if (this.mime == null || !this.mime.equals(uri)) {
            String old = this.mime;
            this.mime = mime;
            firePropertyChange("mime", old,mime);
        }
    }

    public JComponent getForm() {
        if (form == null) {
            FormLayout l = new FormLayout("right:p");
            CellConstraints cc = new CellConstraints();
            PanelBuilder b = new DefaultFormBuilder(l);
            b.appendRelatedComponentsGapColumn();
            b.appendColumn("p:grow");

            b.appendRow("p");
            b.add(new JLabel("Titel der Information"), cc.xy(1, b.getRowCount()));
            b.add(uriField, cc.xy(3, b.getRowCount()));

            b.appendRelatedComponentsGapRow();
            b.appendRow("p");
            b.add(new JLabel("MIME"), cc.xy(1, b.getRowCount()));
            b.add(mimeField, cc.xy(3, b.getRowCount()));


            form = b.getPanel();
        }
        return form;
    }



}
