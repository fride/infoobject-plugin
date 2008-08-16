package org.infoobject.magicmap.plugin;

import net.sf.magicmap.client.gui.MainFrame;
import net.sf.magicmap.client.gui.MainGUI;
import net.sf.magicmap.client.gui.views.ConsoleView;
import net.sf.magicmap.client.gui.views.MapView;
import net.sf.magicmap.client.gui.views.OutlineView;
import net.sf.magicmap.client.model.node.INodeSelectionModel;
import net.sf.magicmap.client.model.outline.OutlineModel;
import net.sf.magicmap.client.visualization.NodeCanvas;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: janfriderici
 * Date: Jul 8, 2008
 * Time: 4:37:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuiComponentFactory {

    private  OutlineModel outlineModel;
    private  NodeCanvas nodeCanvas;
    private  MapView mapView;
    private  OutlineView outlineView;
    private  INodeSelectionModel nodeSelectionModel;
    private  ConsoleView consoleView;
    private  MainFrame mainFrame;
    private  JMenu infoMenu;

    public GuiComponentFactory() {
        
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
}
