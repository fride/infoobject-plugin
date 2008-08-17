package org.infoobject.magicmap.visualization.ui.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Class InformationNodeIconCache ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 17.08.2008
 *         Time: 11:00:49
 */
public class InformationNodeIconCache {

    private Map<String, ImageIcon> iconCache = new HashMap<String, ImageIcon>();



    public static InformationNodeIconCache instance;

    public static InformationNodeIconCache getInstance() {
        if (instance == null) {
            instance = new InformationNodeIconCache();
        }
        return instance;
    }

    public ImageIcon getNodeIcon(String uri) {
        ImageIcon icon = iconCache.get(uri);

        return icon != null ? icon : createIcon(uri);
    }

    private ImageIcon createIcon(String uri) {
        URL uriToLoad = null;
        ImageIcon icon = null;
        try {
            if (uri.startsWith("classpath:")) {
                uriToLoad = getClass().getClassLoader().getResource(uri.substring("classpath:".length()));
            } else {
                uriToLoad = new URL(uri);
            }
            final BufferedImage bufferedImage = ImageIO.read(uriToLoad);
            if (bufferedImage != null) {
                icon = new ImageIcon(bufferedImage);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (icon != null) {
            iconCache.put(uri, icon);
        }
        return icon;
    }
}
