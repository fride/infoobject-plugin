prefix rdfs:   <http://www.w3.org/2000/01/rdf-schema#>
prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
prefix dc:     <http://purl.org/dc/elements/1.1/>
prefix :       <http://www.magicmap.de/2008/06/infoobject#>
prefix xmlns:       <http://www.w3.org/2001/XMLSchema#>
prefix owl: <http://www.w3.org/2002/07/owl#>
prefix skos: <http://www.w3.org/2004/02/skos/core#>

select * where {
    ?filter_link a :ObjectLinking ;
        :hasInformation ?info ;
        :hasObject <$POSITION$> .

    ?info :hasObjectLink ?link  .
    optional { ?info dc:creator ?info_creator .}
    optional { ?info dc:title ?info_title ; :size ?info_size ; dc:format ?mime}

    ?link :hasObject ?linked_object ; dc:creator ?link_creator .

    optional { ?link :linkType ?link_type .}
    optional { ?tagging :tags ?info ; :tags ?tagged ; :isPositive ?positive ; dc:creator ?tag_creator ; :hasTag ?tag . ?tag :rawTag ?raw}

}