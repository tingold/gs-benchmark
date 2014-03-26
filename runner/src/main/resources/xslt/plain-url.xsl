<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : jmx2url.xsl
    Created on : March 25, 2014, 12:27 PM
    Author     : Tom
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text" indent="none" />
  <xsl:template match="/">
        <xsl:for-each select="//HTTPSamplerProxy/elementProp/collectionProp[@name='Arguments.arguments']">            
             <xsl:for-each select="elementProp[@elementType='HTTPArgument']">                       
                 <xsl:value-of select="stringProp[@name='Argument.name']/text()"/>=<xsl:value-of select="stringProp[@name='Argument.value']/text()"/><xsl:if test="position() != last()">&amp;</xsl:if>                                                   
             </xsl:for-each>           
            <xsl:text>&#xa;</xsl:text>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
