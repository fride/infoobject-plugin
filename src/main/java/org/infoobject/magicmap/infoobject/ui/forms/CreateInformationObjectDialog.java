package org.infoobject.magicmap.infoobject.ui.forms;

import com.jgoodies.forms.builder.ButtonBarBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Class CreateInformationObjectDialog ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 23:53:22
 */
public class CreateInformationObjectDialog extends JDialog {
    private InformationMetadataForm metadataForm;
    private ObjectLinkForm linkForm;
    private InformationTaggingForm informationTaggingForm;

    private CardLayout cards = new CardLayout();
    private JComponent buttonPanel;
    private JLabel contentPanel = new JLabel();
    private Action loadInformationAction;
    private Action saveLinkAction;
    private Action saveTaggingAction;

    /**
     *
     * @param frame
     * @param metadataForm
     * @param linkForm
     * @param informationTaggingForm
     * @throws HeadlessException
     */
    public CreateInformationObjectDialog(
            Frame frame,
            InformationMetadataForm metadataForm,
            ObjectLinkForm linkForm,
            InformationTaggingForm informationTaggingForm) throws HeadlessException {
        super(frame);
        setTitle("Informationsobjekt anlegen - Knoten und Information wählen");
        this.metadataForm = metadataForm;
        this.linkForm = linkForm;
        this.informationTaggingForm = informationTaggingForm;
        contentPanel.setLayout(cards);
        contentPanel.add("link", linkForm.getForm());
        ButtonBarBuilder b = new ButtonBarBuilder();
        b.addGriddedButtons(new JButton[]{ new JButton(loadInformationAction)});
    }

    public void setLoadInformationAction(Action loadInformationAction) {
        this.loadInformationAction = loadInformationAction;
    }

    public void setSaveLinkAction(Action saveLinkAction) {
        this.saveLinkAction = saveLinkAction;
    }

    public void setSaveTaggingAction(Action saveTaggingAction) {
        this.saveTaggingAction = saveTaggingAction;
    }
}
