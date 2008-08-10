<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="no"/>

    <xsl:param name="base_uri" select="'.'"/>
    <xsl:param name="source_host" select="'.'"/>
    <xsl:param name="source_protocol" select="'.'"/>
    <xsl:param name="source_file" select="'.'"/>
    <xsl:param name="subject" select="concat('&lt;', $base_uri, '&gt;')"/>
    <xsl:param name="link" select="':link'"/>

    <xsl:template match="/">
                <![CDATA[
@prefix xmlns:       <http://www.w3.org/2001/XMLSchema#> .
@prefix mw:       <http:/www.magicmap.de/2008/voc/mediawiki_facet#> .
]]>
        <xsl:for-each select="//*[@id='ca-nstab-main']">
            <xsl:call-template name="article"/>
        </xsl:for-each>

        <xsl:for-each select="//*[@id='mw-normal-catlinks']/*[name()='span']/*[name()='a']">
            <xsl:call-template name="categories"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="article">
        <xsl:value-of select="$subject"/>  mw:pageType "Article" .

    </xsl:template>
    <xsl:template name="categories">
        <xsl:variable name="category_url" select="concat('&lt;', $source_protocol ,'://', $source_host, @href, '&gt;')"/>
        <xsl:value-of select="$category_url"/> mw:title "<xsl:value-of select="@title"/>" ; mw:hasPage <xsl:value-of select="$subject"/>.
    </xsl:template>

    <xsl:template match="text()"/>

</xsl:stylesheet>