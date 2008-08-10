prefix rdfs:   <http://www.w3.org/2000/01/rdf-schema#>
prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
prefix dc:     <http://purl.org/dc/elements/1.1/>
prefix :       <http://www.magicmap.de/2008/06/infoobject#>
prefix xmlns:       <http://www.w3.org/2001/XMLSchema#>
prefix owl: <http://www.w3.org/2002/07/owl#>
prefix skos: <http://www.w3.org/2004/02/skos/core#>

select * where {
    ?tag a :Tagging
}