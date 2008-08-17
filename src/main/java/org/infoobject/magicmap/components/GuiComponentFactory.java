package org.infoobject.magicmap.components;

import net.sf.magicmap.client.gui.MainFrame;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.utils.GUIBuilder;
import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.gui.views.MapView;
import net.sf.magicmap.client.gui.views.OutlineView;
import net.sf.magicmap.client.model.node.INodeSelectionModel;
import net.sf.magicmap.client.model.outline.OutlineModel;
import net.sf.magicmap.client.visualization.NodeCanvas;
import org.infoobject.core.components.ComponentFactory;
import org.infoobject.core.components.ModelFactory;
import org.infoobject.magicmap.infoobject.ui.util.InformationObjectNodeListFactory;
import org.infoobject.magicmap.node.ui.CreateInformationObjectNodeView;
import org.infoobject.magicmap.node.ui.InformationNodePresenter;
import org.infoobject.magicmap.node.ui.action.LoadAction;
import org.infoobject.magicmap.node.ui.action.ShowCreateInformationObjectAction;
import org.infoobject.magicmap.visualization.application.VisualizationManager;

import javax.swing.*;

/**
 *
 */
public class GuiComponentFactory implements ComponentFactory {

    private  OutlineModel outlineModel;
    private  NodeCanvas nodeCanvas;
    private  MapView mapView;
    private  OutlineView outlineView;
    private  INodeSelectionModel nodeSelectionModel;
    private  ConsoleView consoleView;
    private  MainFrame mainFrame;
    private  JMenu infoMenu;
    private InformationObjectNodeListFactory informationObjectNodeListFactory;
    private InformationNodePresenter informationNodePresenter;
    private VisualizationManager visualizationManager;
    private CreateInformationObjectNodeView createInformationObjectView;
    
    private final ModelFactory modelFactory;
    private final PluginManagerFactory managerFactory;

    public GuiComponentFactory(ModelFactory modelFactory, PluginManagerFactory managerFactory) {
        this.modelFactory = modelFactory;
        this.managerFactory = managerFactory;
    }

    /**
     *
     * @return
     */
    public OutlineModel getOutlineModel() {
        if (this.outlineModel == null) {
            outlineModel = getOutlineView().getOutlineModel();
        }
        return outlineModel;
    }


    public VisualizationManager getVisualizationManager() {
        if (visualizationManager == null) {
            visualizationManager = new VisualizationManager(
                    modelFactory.getInformationObjectNodeGraph(),
                    modelFactory.getInformationObjectNodeModel(), 
                    getNodeCanvas());
        }
        return visualizationManager;
    }

    /**
     *
     * @return
     */
    public OutlineView getOutlineView() {
        if (outlineView == null){
            outlineView = (OutlineView) getMainGUI().getJComponent("outlineView");
        }
        return outlineView;
    }

    /**
     *
     * @return
     */
    public NodeCanvas getNodeCanvas() {
        if (nodeCanvas == null) {
            nodeCanvas = getMapView().getNodeCanvas();
        }
        return nodeCanvas;
    }


    public MapView getMapView() {
        if (mapView == null) {
            mapView = (MapView) getMainGUI().getJComponent("mapView");
        }
        return mapView;
    }

    public MainGUI getMainGUI() {
        return MainGUI.getInstance();
    }


    public ConsoleView getConsoleView() {
        if (consoleView == null) {
            consoleView = (ConsoleView) getMainGUI().getJComponent("consoleView");
        }
        return consoleView;
    }

    /**
     *
     * @return
     */
    public INodeSelectionModel getNodeSelectionModel() {
        if (nodeSelectionModel == null) {
            nodeSelectionModel =  getMainGUI().getNodeSelectionModel();
        }
        return nodeSelectionModel;
    }

    public MainFrame getMainFrame() {
        if (mainFrame == null) {
            mainFrame = getMainGUI().getMainFrame();
        }
        return mainFrame;
    }
    public JMenu getInfoObjectMenu() {
        if (infoMenu == null) {
            final JMenuBar bar = getMainFrame().getJMenuBar();
            infoMenu = new JMenu("Informationsobjekte");
            bar.add(infoMenu);
        }
        return infoMenu;
    }

    public InformationObjectNodeListFactory getInformationObjectNodeListFactory() {
        if (informationObjectNodeListFactory == null) {
            informationObjectNodeListFactory = new InformationObjectNodeListFactory(
                    this.modelFactory.getInformationObjectNodeModel(),
                    modelFactory.getInformationObjectModel());
        }
        return informationObjectNodeListFactory;
    }

    public InformationNodePresenter getInformationNodePresenter() {
        if (informationNodePresenter == null) {
            informationNodePresenter = new InformationNodePresenter(managerFactory.getInformationNodeManager());
        }
        return informationNodePresenter;
    }



    public CreateInformationObjectNodeView getCreateInformationObjectView() {
        if (createInformationObjectView == null) {
            createInformationObjectView = new CreateInformationObjectNodeView(
                    managerFactory.getInformationObjectManager(),
                    managerFactory.getCrawlerManager(),
                    getInformationObjectNodeListFactory());
        }
        return createInformationObjectView;
    }

    public void start() {

        managerFactory.start();
        ShowCreateInformationObjectAction showCreateAction = new ShowCreateInformationObjectAction(getCreateInformationObjectView(), getMainFrame());
        getNodeSelectionModel().addNodeModelSelectionListener(showCreateAction);

        LoadAction loadAction = new LoadAction(managerFactory.getInformationNodeManager());
        
        getNodeSelectionModel().addNodeModelSelectionListener(loadAction);

        //factory.getMapView().getMenuContainer().addSeperator();
        this.getMapView().getMenuContainer().addNodeMenuItem(this, new JMenuItem(showCreateAction));
        this.getMapView().getMenuContainer().addNodeMenuItem(this, new JMenuItem(loadAction));
        //this.getMapView().getMenuContainer().addNodeMenuItem(presenter, new JMenuItem(deleteAction));

        this.getOutlineView().getMenuContainer().addNodeMenuItem(this, new JMenuItem(showCreateAction));
        this.getOutlineView().getMenuContainer().addNodeMenuItem(this, new JMenuItem(loadAction));
        //this.getOutlineView().getMenuContainer().addNodeMenuItem(presenter, new JMenuItem(deleteAction));

        //To change body of implemented methods use File | Settings | File Templates.
        modelFactory.getInformationObjectModel().addInformationObjectListener(managerFactory.getTaggingRelationManager());
        getInfoObjectMenu().add(GUIBuilder.createCheckBoxMenuItem(getInformationNodePresenter().getEnableAutoLoadAction(), true));
        // Visualisierung starten
        getVisualizationManager().start();

    }

    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
