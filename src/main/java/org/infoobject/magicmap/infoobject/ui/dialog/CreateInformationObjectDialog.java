package org.infoobject.magicmap.infoobject.ui.dialog;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import org.infoobject.magicmap.infoobject.ui.forms.ObjectLinkForm;
import org.infoobject.magicmap.infoobject.ui.InformationMetadataView;
import org.infoobject.magicmap.infoobject.ui.InformationTaggingView;

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
    private InformationMetadataView metadataView;
    private ObjectLinkForm linkForm;
    private InformationTaggingView informationTaggingView;

    private CardLayout cards = new CardLayout();
    private JComponent buttonPanel;
    private JLabel contentPanel = new JLabel();
    private Action loadInformationAction;
    private Action saveLinkAction;
    private Action saveTaggingAction;

    /**
     *
     * @param frame
     * @param metadataView
     * @param linkForm
     * @param informationTaggingView
     * @throws HeadlessException
     */
    public CreateInformationObjectDialog(
            Frame frame,
            InformationMetadataView metadataView,
            ObjectLinkForm linkForm,
            InformationTaggingView informationTaggingView) throws HeadlessException {
        super(frame);
        setTitle("Informationsobjekt anlegen");
        this.metadataView = metadataView;
        this.linkForm = linkForm;
        this.informationTaggingView = informationTaggingView;
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
