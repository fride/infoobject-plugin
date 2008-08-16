package org.infoobject.core.util.ui;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * <p>
 * Class WizardPage ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 23:20:46
 */
public interface WizardPage {
    String getTitle();
    boolean isFinishEnabled();
    boolean isNextEnabled();
    boolean isPreviousEnabled();

    void onEnter();
    
    JComponent getView();

    void addPropertyChangeListener(String name, PropertyChangeListener propertyChangeListener);

    void removePropertyChangeListener(String name, PropertyChangeListener propertyChangeListener);


    void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);

    void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);

    PropertyChangeListener[] getPropertyChangeListeners();


    boolean finish();

    void cancel();
}
