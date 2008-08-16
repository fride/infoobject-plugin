package org.infoobject.core.tag.ui;

import net.sf.magicmap.client.utils.AbstractModel;
import org.infoobject.core.infoobject.domain.Tag;
import org.infoobject.core.infoobject.domain.Tagging;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.FilterList;

/**
 * <p>
 * Class TagPresenter ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 20:41:06
 */
public class TagPresenter extends AbstractModel {
    private EventList<Tag> tagList = new BasicEventList<Tag>();
    private FilterList<Tag> myTagList = new FilterList<Tag>(tagList);

    private EventList<Tagging> informationTagList = new BasicEventList<Tagging>();
    private EventList<Tagging> myinformationTagList = new BasicEventList<Tagging>();

    private boolean showAllTags;
    private boolean showInformationTags;

}
