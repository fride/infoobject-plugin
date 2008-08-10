package org.infoobject.core.tag;

import org.infoobject.core.infoobject.event.TaggingHandler;
import org.infoobject.core.infoobject.event.InformationObjectListenerAdapter;
import org.infoobject.core.infoobject.event.TaggingEvent;
import org.infoobject.core.infoobject.model.*;
import org.infoobject.core.relation.RelationSource;

import java.util.List;

/**
 * <p>
 * Class TaggingRelationHandler ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 13:49:52
 */
public class TaggingRelationHandler extends InformationObjectListenerAdapter {

    

    private final InformationObjectNodeGraph graph;
    private final InformationObjectNodeModel nodeModel;
    public TaggingRelationHandler(InformationObjectNodeModel nodeModel) {
        this.nodeModel = nodeModel;
        this.graph = nodeModel.getInformationObjectNodeGraph();
    }

    @Override
    public void onTagging(TaggingEvent event) {
        final TaggingPost tagging = event.getTagging();
        final InformationObject source = tagging.getInformationObject();
        final InformationObjectNode sourceNode = nodeModel.findInformationNode(source.getUri());
        

        for (Tagging otherTagging : tagging.getTag().getTags()) {
            final InformationObject target = otherTagging.getTagged();
            final InformationObjectNode tagetNode = nodeModel.findInformationNode(target.getUri());
            createOrChangeTaggingRelation(sourceNode, tagetNode);

        }
    }
    public void createOrChangeTaggingRelation(
            final InformationObjectNode source,
            final InformationObjectNode target) {

        final InformationRelationEdge edge1 = graph.addEdge(source, target, new InformationRelation.Factory() {
            public InformationRelation create(InformationRelationEdge edge) {
                return new TaggingRelation(edge);
            }
        });
        edge1.update();
    }

    @Override
    public void onTaggingRemoved(TaggingEvent tagging) {
        super.onTaggingRemoved(tagging);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
