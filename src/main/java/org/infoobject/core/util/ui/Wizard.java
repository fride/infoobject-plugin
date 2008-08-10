package org.infoobject.core.util.ui;

import com.jgoodies.forms.factories.ButtonBarFactory;

import javax.swing.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.VetoableChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import net.sf.magicmap.client.gui.utils.MagicAction;

/**
 * <p>
 * Class Wizzard ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 23:15:46
 */
public class Wizard extends JDialog {

    private List<WizardPage> pages = new LinkedList<WizardPage>();
    private int currentPage = 0;
    private CardLayout cards = new CardLayout();
    private JPanel contents = new JPanel();
    private JComponent buttonPanel;
    private VetoableChangeSupport veto;
    private JButton nextButton;
    private JButton prevButton;
    private JButton finishButton;
    private JButton closeButton;

    private MagicAction nextAction = new MagicAction("Weiter") {
        public void actionPerformed(ActionEvent event) {
            nextPage();
        }
    };
    private MagicAction prevAction = new MagicAction("Zurück") {
        public void actionPerformed(ActionEvent event) {
            prevPage();
        }
    };



    private MagicAction finishAction = new MagicAction("Fertig") {
        public void actionPerformed(ActionEvent event) {
            finish();
        }
    };
    private MagicAction closeAction = new MagicAction("Abbrechen") {
        public void actionPerformed(ActionEvent event) {
            doCancel();
        }
    };

    private PropertyChangeListener nextListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent event) {
            nextAction.setEnabled(Boolean.TRUE.equals(event.getNewValue()));
        }
    };

    private PropertyChangeListener finishEnabledListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent event) {
            finishAction.setEnabled(Boolean.TRUE.equals(event.getNewValue()));
        }
    };

    private PropertyChangeListener previousEnabledListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent event) {
            prevAction.setEnabled(Boolean.TRUE.equals(event.getNewValue()));
        }
    };


    public Wizard(Iterable<WizardPage> pages) throws HeadlessException {
        veto = new VetoableChangeSupport(this);
        contents.setLayout(cards);
        setPages(pages);
        showPage(this.pages.get(0));
    }

    public Wizard(Iterable<WizardPage> pages,Frame frame) throws HeadlessException {
        super(frame);
        veto = new VetoableChangeSupport(this);
        contents.setLayout(cards);
        setPages(pages);
        showPage(this.pages.get(0));
    }

    private void showPage(WizardPage wizardPage) {
        cards.show(contents, wizardPage.getTitle());
        wizardPage.onEnter();

    }

    public void nextPage(){
        if (getCurrentPage().isNextEnabled()){
            leave();
            ++currentPage;
            enter();
        }
    }

    private void enter() {
        showPage(getCurrentPage());
        getCurrentPage().addPropertyChangeListener("nextEnabled", nextListener);
        getCurrentPage().addPropertyChangeListener("finishEnabled", finishEnabledListener);
        getCurrentPage().addPropertyChangeListener("previousEnabled", previousEnabledListener);
        nextAction.setEnabled(getCurrentPage().isNextEnabled());
        prevAction.setEnabled(getCurrentPage().isPreviousEnabled());
        finishAction.setEnabled(getCurrentPage().isFinishEnabled());
    }

    private void leave() {
        getCurrentPage().removePropertyChangeListener("nextEnabled", nextListener);
        getCurrentPage().removePropertyChangeListener("finishEnabled", finishEnabledListener);
        getCurrentPage().removePropertyChangeListener("previousEnabled", previousEnabledListener);
    }

    public void prevPage() {
        if (getCurrentPage().isPreviousEnabled()) {
            leave();
            currentPage--;
            enter();            
        }
    }

    public void finish() {
        if (getCurrentPage().isNextEnabled()) {
            if (getCurrentPage().finish()) {
                setVisible(false);
            }
        }
    }
    public void doCancel() {
        setVisible(false);
    }

    public WizardPage getCurrentPage() {
        return pages.get(currentPage);
    }
    /**
     * 
     * @param pages
     */
    protected void setPages(Iterable<WizardPage> pages){
        for (WizardPage page:pages) {
            this.pages.add(page);
            contents.add(page.getView(), page.getTitle());
        }
        nextButton = new JButton(nextAction);
        prevButton = new JButton(prevAction);
        finishButton = new JButton(finishAction);
        closeButton = new JButton(closeAction);
        
        buttonPanel =ButtonBarFactory.buildRightAlignedBar(prevButton,nextButton, finishButton,closeButton);
        JPanel p = new JPanel(new BorderLayout());
        p.add(contents, BorderLayout.CENTER);
        p.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(p);
    }
}
