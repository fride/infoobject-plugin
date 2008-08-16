package org.infoobject.magicmap.components;

import net.sf.magicmap.client.gui.MainFrame;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.gui.views.MapView;
import net.sf.magicmap.client.gui.views.OutlineView;
import net.sf.magicmap.client.model.node.INodeSelectionModel;
import net.sf.magicmap.client.model.outline.OutlineModel;
import net.sf.magicmap.client.visualization.NodeCanvas;
import org.infoobject.core.components.ComponentFactory;
import org.infoobject.core.components.ModelFactory;
import org.infoobject.core.infoobject.ui.util.InformationObjectNodeListFactory;
import org.infoobject.core.infoobject.ui.model.InformationObjectPresenter;
import org.infoobject.core.infoobject.ui.action.AbstractNodeAction;

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
    private InformationObjectPresenter informationObjectPresenter;
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

    public InformationObjectPresenter getInformationObjectPresenter() {
        if (informationObjectPresenter == null ) {
            informationObjectPresenter = new InformationObjectPresenter(
                    managerFactory.getInformationNodeManager(), managerFactory.getInformationObjectManager(), managerFactory.getCrawlerManager(),
                    getInformationObjectNodeListFactory()
            );
        }
        return informationObjectPresenter;
    }

    public void start() {
        final InformationObjectPresenter presenter = getInformationObjectPresenter();
        final AbstractNodeAction createAction = presenter.getShowCreateAnEditDialogAction();
        getNodeSelectionModel().addNodeModelSelectionListener(createAction);
        final AbstractNodeAction deleteAction = presenter.getDeleteInformationObjectAction();
        getNodeSelectionModel().addNodeModelSelectionListener(deleteAction);

        //factory.getMapView().getMenuContainer().addSeperator();
        this.getMapView().getMenuContainer().addNodeMenuItem(presenter, new JMenuItem(createAction));
        this.getMapView().getMenuContainer().addNodeMenuItem(presenter, new JMenuItem(deleteAction));

        this.getOutlineView().getMenuContainer().addNodeMenuItem(presenter, new JMenuItem(createAction));
        this.getOutlineView().getMenuContainer().addNodeMenuItem(presenter, new JMenuItem(deleteAction));

        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
