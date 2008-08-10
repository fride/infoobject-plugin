package org.infoobject.magicmap.visualization.layout;

import edu.uci.ics.jung.graph.Graph;

/**
 * <p>
* Class LayoutThread ZUSAMMENFASSUNG
* </p>
* <p>
* DETAILS
* </p>
*
* @author Jan Friderici
*         Date: 10.08.2008
*         Time: 23:21:13
*/
final class LayoutThread {
    private Thread layoutThread;
    private Graph graph;
    private AssociationSpringLayout springLayout;
    private boolean terminated;
    private boolean suspended;
    private Runnable worker = new Runnable(){
        public void run(){
            while (!terminated) {
                try {
                    if (!suspended) {
                        int x = 129;
                        x = x +2;
                        springLayout.advancePositions();
                    }
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    // nix zu dun ;-)
                }
            }
        }
    };

    /**
     *
     * @param springLayout
     */
    public LayoutThread( AssociationSpringLayout springLayout) {
        this.springLayout = springLayout;
    }

    public void suspend() {
        this.suspended = true;
        layoutThread.interrupt();
    }

    public void unsupend() {
        this.suspended = false;
        layoutThread.interrupt();
    }

    public void start() {
        if (layoutThread != null) {
            return;
        }
        layoutThread = new Thread(worker,"association layout thread");
        layoutThread.setDaemon(true);
        //layoutThread.setPriority(Thread.MIN_PRIORITY+1);
        layoutThread.start();
    }

    public void stop () {
        this.terminated = true;
        layoutThread.interrupt();
    }
}
