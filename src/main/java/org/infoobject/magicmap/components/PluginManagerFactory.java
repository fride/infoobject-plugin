package org.infoobject.magicmap.components;

import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.visualization.NodeCanvas;
import org.infoobject.core.components.AbstractManagerFactory;
import org.infoobject.core.components.ModelFactory;
import org.infoobject.core.infoobject.dao.InformationObjectRepository;
import org.infoobject.magicmap.visualization.VisualizationManager;
import org.infoobject.openrdf.infoobject.RdfInformationObjectRepository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import java.io.File;

/**
 * <p>
 * Class PluginManagerFactory ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 16.08.2008
 *         Time: 17:31:29
 */
public class PluginManagerFactory extends AbstractManagerFactory {
    private final ConsoleView consoleView;
    private final NodeCanvas nodeCanvas;
    private InformationObjectRepository informationObjectRepository;

    private VisualizationManager visualizationManager;

    public PluginManagerFactory(ModelFactory modelFactory, ConsoleView consoleView, NodeCanvas nodeCanvas) {
        super(modelFactory);
        this.consoleView = consoleView;
        this.nodeCanvas = nodeCanvas;
    }

    protected void handleException(String message, Exception ex) {
        consoleView.append(message + ": " + ex.getMessage());
        throw new RuntimeException(ex);
    }

    
    public InformationObjectRepository getInformationObjectRepository() {
        if (informationObjectRepository == null) {
            File dataDir = new File(System.getProperty("user.home") + "/.mmnfo/rdf");
            consoleView.append("Infoobjects settup with dir " + dataDir.getAbsolutePath());
            SailRepository sailRepository = new SailRepository(new MemoryStore(dataDir));
            try {
                sailRepository.initialize();
            } catch (RepositoryException e) {
                handleException("Init RDF", e);
            }
            informationObjectRepository = new RdfInformationObjectRepository(sailRepository);
        }
        return informationObjectRepository;
    }

    public VisualizationManager getVisualizationManager() {
        if (visualizationManager == null) {
            visualizationManager = new VisualizationManager(getModelFactory().getInformationObjectNodeGraph(), nodeCanvas);
        }
        return visualizationManager;
    }

    @Override
    public void start() {
        super.start();
    }
}
