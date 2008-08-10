<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="yes" xml:space="default"/>

    <xsl:param name="base_uri" select="'.'"/>
    <xsl:param name="source_host" select="'.'"/>
    <xsl:param name="source_protocol" select="'.'"/>
    <xsl:param name="source_file" select="'.'"/>
    <xsl:param name="subject" select="concat('&lt;', $base_uri, '&gt;')"/>
    <xsl:param name="link" select="':link'"/>

    <xsl:template match="/">
        <![CDATA[
@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .
@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix xmlns:       <http://www.w3.org/2001/XMLSchema#> .
@prefix html:       <http:/www.magicmap.de/2008/voc/html_facet#> .
@prefix dc:       <http://purl.org/dc/elements/1.1/> .
<> rdfs:Label "Mediawiki" .
]]>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="//*[name()='title']">
        &lt;<xsl:value-of select="$base_uri"></xsl:value-of>&gt; dc:title "<xsl:value-of select="text()"></xsl:value-of>" .        
    </xsl:template>
    <xsl:template match="//*[name()='a' and not(starts-with(@href, '#'))]">
        <xsl:value-of select="$subject"/>
        <xsl:choose>
            <xsl:when test="starts-with(@href, '/') or starts-with(@href, './')">
                html:link &lt;<xsl:value-of select="concat($source_protocol ,'://', $source_host,@href)"/>&gt; ;
            </xsl:when>
            <xsl:otherwise>
                html:link &lt;<xsl:value-of select="@href"/>&gt; ;
            </xsl:otherwise>
        </xsl:choose>
        html:text  """ <xsl:value-of select="."/> """ ;
        html:title """ <xsl:value-of select="@title"/> """ .
    </xsl:template>

    <xsl:template match="text()"/>

</xsl:stylesheet>