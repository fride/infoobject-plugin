package org.infoobject.core.util.ui;

import net.sf.magicmap.client.utils.AbstractModel;

import javax.swing.*;

/**
 * <p>
 * Class DefaultWizardPage ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 23:25:47
 */
public abstract class DefaultWizardPage extends AbstractModel implements WizardPage{
    private String title;
    private final JComponent component;

    private boolean finishEnabled;
    private boolean nextEnabled;
    private boolean previousEnabled;

    public DefaultWizardPage(String title, JComponent component) {
        this.title = title;
        this.component = component;
    }

    public String getTitle() {
        return title;
    }

    protected void setFinishEnabled(boolean finishEnabled) {
        if (this.finishEnabled != finishEnabled) {
            this.finishEnabled = finishEnabled;
            firePropertyChange("finishEnabled", !finishEnabled, finishEnabled);
        }
    }

    protected void setNextEnabled(boolean nextEnabled) {
        if (this.nextEnabled != nextEnabled) {
            this.nextEnabled = nextEnabled;
            firePropertyChange("nextEnabled", !nextEnabled, nextEnabled);
        }
    }

    protected void setPreviousEnabled(boolean previousEnabled) {
        if (this.previousEnabled != previousEnabled) {
            this.previousEnabled = previousEnabled;
            firePropertyChange("previousEnabled", !previousEnabled, previousEnabled);
        }
    }

    public boolean isFinishEnabled() {
        return finishEnabled;
    }

    public boolean isNextEnabled() {
        return nextEnabled;
    }

    public boolean isPreviousEnabled() {
        return previousEnabled;
    }

    public void onEnter() {
       
    }

    public boolean onLeave() {
        return true;
    }

    public JComponent getView() {
        return component;
    }
}
