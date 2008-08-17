package org.infoobject.magicmap.components;

import org.infoobject.core.components.AbstractManagerFactory;
import org.infoobject.core.components.ModelFactory;
import org.infoobject.core.infoobject.dao.InformationObjectRepository;
import org.infoobject.openrdf.infoobject.RdfInformationObjectRepository;
import org.infoobject.magicmap.node.application.InformationNodeLoader;
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
    private InformationObjectRepository informationObjectRepository;
    private InformationNodeLoader informationNodeLoader;
    

    public PluginManagerFactory(ModelFactory modelFactory) {
        super(modelFactory);
    }
    
    protected void handleException(String message, Exception ex) {
        ex.printStackTrace();
        throw new RuntimeException(ex);
    }

    
    public InformationObjectRepository getInformationObjectRepository() {
        if (informationObjectRepository == null) {
            File dataDir = new File(System.getProperty("user.home") + "/.mmnfo/rdf");
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


    public InformationNodeLoader getInformationNodeLoader() {
        if (informationNodeLoader == null) {
            informationNodeLoader = new InformationNodeLoader(getInformationObjectManager());
        }
        return informationNodeLoader;
    }

    @Override
    public void start() {
        super.start();
    }
}
