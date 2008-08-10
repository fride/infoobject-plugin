package org.infoobject.magicmap.plugin;

import net.sf.magicmap.client.plugin.AbstractPlugin;
import net.sf.magicmap.client.plugin.IPluginDescriptor;
import net.sf.magicmap.client.utils.Settings;
import net.sf.magicmap.client.controller.IController;
import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.model.node.Node;
import org.infoobject.magicmap.visualization.VisualizationManager;
import org.infoobject.magicmap.infoobject.ui.model.InformationObjectPresenter;
import org.infoobject.core.infoobject.InformationNodeManager;
import org.infoobject.core.infoobject.InformationObjectManager;
import org.infoobject.core.infoobject.model.InformationObjectModel;
import org.infoobject.core.infoobject.model.InformationObjectNodeModel;
import org.infoobject.core.infoobject.model.ObjectName;
import org.infoobject.core.agent.AgentManager;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.crawl.xml.XsltMetadataExtractor;
import org.infoobject.core.tag.TaggingRelationHandler;
import org.infoobject.openrdf.infoobject.RdfInformationObjectRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import javax.swing.*;
import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import foxtrot.Worker;
import foxtrot.Task;

/**
 * <p>
 * Class InformationObjectPlugin ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 19:36:22
 */
public class InformationObjectPlugin extends AbstractPlugin {

    private VisualizationManager visualizationManager;
    private InformationNodeManager informationNodeManager;
    private InformationObjectManager informationObjectManager;
    private IController controller;
    private List<Exception> startExceptions = new LinkedList<Exception>();
    private RdfInformationObjectRepository repos;
    private AgentManager agentManager;
    private CrawlerManager crawlerManager;
    private XsltMetadataExtractor xslExtractor;
    private ConsoleView consoleView;
    private InformationObjectPresenter informationPresenter;

    private TaggingRelationHandler taggingRelationHandler;
    public InformationObjectPlugin(IPluginDescriptor iPluginDescriptor) {
        super(iPluginDescriptor);
    }

    @Override
    public void setController(IController iController) {
        super.setController(iController);
        controller = iController;
    }

    @Override
    public void startPlugin() {
        super.startPlugin();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void stopPlugin() {
        super.stopPlugin();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void connect() {
        super.connect();
    }

    @Override
    public void loadMap() {
        super.loadMap();
        final InformationObjectNodeModel nodeModel = informationNodeManager.getNodeModel();
        visualizationManager.setMap(nodeModel.getCurrentMap());
        try {
            final ArrayList<Node> nodes = new ArrayList<Node>(nodeModel.getNodes());
            for (Node node:nodes) {
                if (node.isPhysical()){
                    consoleView.append("Loading " + node.getDisplayName());
                    informationObjectManager.load(new ObjectName(node.getName(), nodeModel.getServerID()));
                }
            }

        }catch (Exception ex){
            consoleView.append(ex.getMessage());
        }

    }

    @Override
    public void setup(Settings settings) {
        super.setup(settings);
        GuiComponentFactory factory = new GuiComponentFactory();
        File dataDir = new File(System.getProperty("user.home") + "/.mmnfo/rdf");
        factory.getConsoleView().append("Infoobjects settup with dir " + dataDir.getAbsolutePath());
        SailRepository sailRepository = new SailRepository(new MemoryStore(dataDir));
        consoleView = factory.getConsoleView();
        try {
            sailRepository.initialize();
            repos = new RdfInformationObjectRepository(sailRepository);
            agentManager = new AgentManager();
            setupParsers(factory.getConsoleView());
            crawlerManager = new CrawlerManager(xslExtractor);
            informationObjectManager = new InformationObjectManager(new InformationObjectModel(), repos, agentManager);
            informationNodeManager = new InformationNodeManager(controller.getNodeModel(), informationObjectManager);
            visualizationManager = new VisualizationManager(informationNodeManager.getInformationNodeGraph(), factory.getNodeCanvas());
            informationPresenter = new InformationObjectPresenter(informationNodeManager, informationObjectManager, crawlerManager);
            setupActions(factory);
            taggingRelationHandler = new TaggingRelationHandler(informationNodeManager.getNodeModel());

        } catch (Exception e) {
            startExceptions.add(e);
        } finally {
            if (startExceptions.size() > 0) {
                factory.getConsoleView().append("Errors starting InformationObjectPlugin!");
                for (Exception ex : startExceptions) {
                    factory.getConsoleView().append(ex.getMessage());
                }
            } else {

                consoleView.append("Infoobjects loaded");
            }
        }


    }

    private void setupActions(GuiComponentFactory factory) {
        factory.getMapView().getMenuContainer().addNodeMenuItem(informationPresenter, new JMenuItem(informationPresenter.getShowCreateDialogAction()));
        factory.getOutlineView().getMenuContainer().addNodeMenuItem(informationPresenter, new JMenuItem(informationPresenter.getShowCreateDialogAction()));
    }

    private void setupParsers(ConsoleView consoleView)  {
        xslExtractor = new XsltMetadataExtractor();
        try {
            xslExtractor.addXslt("classpath:org/infoobject/xslt/html.xsl");
            xslExtractor.addXslt("classpath:org/infoobject/xslt/mediawiki.xsl");
        } catch (IOException ex) {
            consoleView.append("Error loading xsl " + ex.getMessage());
        } catch (TransformerConfigurationException e) {
            consoleView.append("Error loading xsl " + e.getMessage());
        }
    }

}