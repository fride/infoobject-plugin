package org.infoobject.magicmap.crawl.ui;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import foxtrot.Task;
import foxtrot.Worker;
import net.sf.magicmap.client.utils.DocumentAdapter;
import net.sf.magicmap.client.gui.utils.MagicAction;
import net.sf.magicmap.client.gui.utils.GUIUtils;
import org.infoobject.core.crawl.CrawlerManager;
import org.infoobject.core.crawl.CrawlJobResultHandler;
import org.infoobject.core.crawl.InterceptingCrawlJobResultHandler;
import org.infoobject.core.crawl.CrawlJob;
import org.infoobject.core.rdf.RdfContainer;

/**
 * <p>
 * Class LoadUriDialog ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 10.08.2008
 *         Time: 10:58:24
 */
public class CrawlUrlDialog extends JDialog {

    private JTextField uriField;
    private JButton loadButton = new JButton();
    private JProgressBar progressBar = new JProgressBar();
    private JTextArea errors = new JTextArea(3,3);
    private JPanel statusPanel = new JPanel();
    private CardLayout cards = new CardLayout();

    public CrawlUrlDialog(JFrame frame) throws HeadlessException {
        super(frame);
        init();
    }

    public CrawlUrlDialog(JDialog frame) throws HeadlessException {
        super(frame);
        init();
    }

    private void init() {
        uriField = new JTextField(20);
        setTitle("Url Laden");
        FormLayout l = new FormLayout("p,3dlu,p:grow,3dlu,p","p,12dlu,p");
        CellConstraints cc = new CellConstraints();
        JPanel p = new JPanel(l);
        p.add(new JLabel("Adresse"), cc.xy(1,1));
        p.add(uriField, cc.xy(3,1));
        p.add(loadButton, cc.xy(5,1));
        p.add(statusPanel, cc.xyw(1,3,5));

        statusPanel.setLayout(cards);
        statusPanel.add(progressBar, "p");
        statusPanel.add(errors, "e");
        new DocumentAdapter(uriField){
            public void handleChange(String s) {
            loadButton.setEnabled(s != null && s.length() > 1);
            }
        };
        errors.setEditable(false);
        errors.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.setVisible(false);
        p.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        getContentPane().add(p);
    }

    public void setLoadAction(Action loadAction) {
        this.loadButton.setAction(loadAction);

    }

    public JTextField getUriField() {
        return uriField;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }
    public void showError(String err) {
        loadButton.setEnabled(true);
        errors.setText(err);
        errors.setSize(errors.getPreferredSize());
        statusPanel.setVisible(true);
        cards.show(statusPanel, "e");
        pack();

    }

    public void startLoad(){
        this.loadButton.setEnabled(false);
        this.progressBar.setIndeterminate(true);
        statusPanel.setVisible(true);
        errors.setSize(0,0);
        cards.show(statusPanel, "p");
        pack();
    }

    public String getUri() {
        return uriField.getText();
    }

    public static void crawl(final JFrame parent, final CrawlerManager crawlerManager,final CrawlJobResultHandler handler){
        final CrawlUrlDialog dlg = new CrawlUrlDialog(parent);
        fillDialog(crawlerManager, handler, dlg);
    }
    public static void crawl(final JDialog parent, final CrawlerManager crawlerManager,final CrawlJobResultHandler handler){
        final CrawlUrlDialog dlg = new CrawlUrlDialog(parent);
        fillDialog(crawlerManager, handler, dlg);
    }

    private static void fillDialog(final CrawlerManager crawlerManager, final CrawlJobResultHandler handler, final CrawlUrlDialog dlg) {
        final InterceptingCrawlJobResultHandler myHandler = new InterceptingCrawlJobResultHandler(handler){
            @Override
            public void crawlStarted() {
                dlg.startLoad();
                super.crawlStarted();
            }

            @Override
            public void urlCrawled(RdfContainer result, int depth) {
                super.urlCrawled(result, depth);

            }

            @Override
            public void crawlFinished() {
                dlg.setVisible(false);
                dlg.dispose();
                super.crawlFinished();
            }

            @Override
            public void crawlFailed(Exception error) {
                dlg.showError(error.getMessage());
                super.crawlFailed(error);
            }
        };
        dlg.setLoadAction(new MagicAction("Laden") {
            public void actionPerformed(ActionEvent event) {
                crawlerManager.crawl(new CrawlJob(dlg.uriField.getText(), 0), myHandler);
            }
        });
        dlg.pack();
        dlg.setModal(true);
        GUIUtils.locateOnScreen(dlg);
        dlg.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final CrawlUrlDialog dlg = new CrawlUrlDialog(f);
        Action ll = new AbstractAction("laden") {
            public void actionPerformed(ActionEvent event) {
                dlg.startLoad();
                Task t = new Task() {
                    public Object run() throws Exception {
                        Thread.sleep(4 * 1000);
                        //throw new IllegalArgumentException("Ennnenene menenmnmnwe");
                        return "";
                    }
                };
                try {
                    Worker.post(t);
                    dlg.setVisible(false);
                } catch (Exception e) {
                    dlg.showError(e.getMessage());
                }
            }
        };
        dlg.setLoadAction(ll);
        f.setVisible(true);
        dlg.pack();
        dlg.setVisible(true);
    }
}
