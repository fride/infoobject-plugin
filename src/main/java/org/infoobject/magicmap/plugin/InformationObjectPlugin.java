package org.infoobject.magicmap.plugin;

import net.sf.magicmap.client.controller.IController;
import net.sf.magicmap.client.plugin.AbstractPlugin;
import net.sf.magicmap.client.plugin.IPluginDescriptor;
import net.sf.magicmap.client.utils.Settings;
import org.infoobject.core.components.DefaultModelFactory;
import org.infoobject.core.components.ModelFactory;
import org.infoobject.core.infoobject.ui.model.InformationObjectPresenter;
import org.infoobject.core.position.application.PositionLinkRelationManager;
import org.infoobject.core.tag.application.TaggingRelationManager;
import org.infoobject.magicmap.components.GuiComponentFactory;
import org.infoobject.magicmap.components.PluginManagerFactory;
import org.infoobject.magicmap.node.application.InformationNodeLoader;
import org.infoobject.magicmap.visualization.VisualizationManager;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

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
    private IController controller;
    private List<Exception> startExceptions = new LinkedList<Exception>();
    private InformationObjectPresenter informationPresenter;

    private TaggingRelationManager taggingRelationManager;
    private GuiComponentFactory factory;

    private PositionLinkRelationManager positionLinkRelationManager;
    private ModelFactory modelFactory;
    private PluginManagerFactory managerFactory;

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
        managerFactory.getVisualizationManager().setMap(modelFactory.getInformationObjectNodeModel().getCurrentMap());
    }

    @Override
    public void setup(Settings settings) {
        super.setup(settings);
        factory = new GuiComponentFactory(modelFactory, managerFactory);
        modelFactory = new DefaultModelFactory(controller);
        managerFactory = new PluginManagerFactory(modelFactory, factory.getConsoleView(), factory.getNodeCanvas());
        try {
            informationPresenter = factory.getInformationObjectPresenter();
            factory.start();
            setupActions(factory);



            positionLinkRelationManager = new PositionLinkRelationManager(modelFactory.getInformationObjectNodeModel(), modelFactory.getInformationObjectNodeGraph(), modelFactory.getInformationObjectModel());
            registerNodeModelListeners();
        } catch (Exception e) {
            startExceptions.add(e);
            e.printStackTrace();
        } finally {
            if (startExceptions.size() > 0) {
                factory.getConsoleView().append(" " + startExceptions.size() + " Errors starting InformationObjectPlugin!");
                for (Exception ex : startExceptions) {
                    factory.getConsoleView().append(ex.getMessage());
                }
            } else {
                factory.getConsoleView().append("Infoobjects loaded");
            }
        }


    }

    private void registerNodeModelListeners() {
        modelFactory.getInformationObjectNodeModel().addNodeModelListener(new InformationNodeLoader(managerFactory,factory));
        taggingRelationManager = new TaggingRelationManager(modelFactory.getInformationObjectNodeModel());
        modelFactory.getInformationObjectModel().addInformationObjectListener(taggingRelationManager);
    }

    private void setupActions(GuiComponentFactory factory) {
        //factory.getMapView().getMenuContainer().addSeperator();
        factory.getMapView().getMenuContainer().addNodeMenuItem(informationPresenter, new JMenuItem(informationPresenter.getShowCreateAnEditDialogAction()));
        factory.getMapView().getMenuContainer().addNodeMenuItem(informationPresenter, new JMenuItem(informationPresenter.getDeleteInformationObjectAction()));

        //factory.getMapView().getMenuContainer().addSeperator();
        factory.getOutlineView().getMenuContainer().addNodeMenuItem(informationPresenter, new JMenuItem(informationPresenter.getShowCreateAnEditDialogAction()));
        factory.getOutlineView().getMenuContainer().addNodeMenuItem(informationPresenter, new JMenuItem(informationPresenter.getDeleteInformationObjectAction()));
    }



}
