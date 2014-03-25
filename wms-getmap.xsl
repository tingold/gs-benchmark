<?xml version="1.0" encoding="UTF-8"?>
<!-- 
This XLST will take the a JMeter JMX filem extract WMS parameters and output and CSV File. 
While this may seem redundant it reduces the overall file size and allows for different 
parameters to be fed into a standardized JMeter test. 
-->


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
    <!--
    The output CSV file will have the following field order
    layers, imageformat,styles, transparent, tiled, srs, height, width,bbox
     -->
    <xsl:template match="/">
        <xsl:for-each select="//HTTPSamplerProxy/elementProp/collectionProp[@name='Arguments.arguments']">
            <xsl:if test="elementProp[@name='request']/text() = 'getmap'">                     
                    <xsl:value-of select="elementProp[@name='LAYERS']/stringProp[@name='Argument.value']/text()"/>,&quot;<xsl:value-of select="elementProp[@name='FORMAT']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='STYLES']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='TRANSPARENT']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='TILED']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='SRS']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='HEIGHT']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='WIDTH']/stringProp[@name='Argument.value']/text()"/>&quot;,&quot;<xsl:value-of select="elementProp[@name='BBOX']/stringProp[@name='Argument.value']/text()"/>&quot;
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>