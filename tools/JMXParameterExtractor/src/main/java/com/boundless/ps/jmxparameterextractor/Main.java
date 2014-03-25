/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boundless.ps.jmxparameterextractor;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author tingold@boundlessgeo.com
 */
class Main {

    public static void main(String args[]) {
        if (args.length != 3) {
            StringBuilder sb = new StringBuilder("Description: \n \n");
            sb.append("This tool extracts OGC requests from JMeter jmx files and parses them \n");
            sb.append("into csv files for use in templated JMeter load tests. While this\n");
            sb.append("may seem redundant, it is useful to standardize the data captured from\n");
            sb.append("a JMeter HTTP proxy\n\n");
            sb.append("Usage:\n\n");
            sb.append("arg1: jmx file \n arg2: destination file \n arg3: requests to extract\n");
            sb.append("Current valid values for arg3 are: \nwms-get-map\nwms-getfeature-info\n plain-url\n\n");
            System.out.print(sb.toString());
            return;
        }

        File infile = new File(args[0]);
        if (!infile.exists()) {
            System.out.println("Can't read from input file");
            return;
        }

        try {
            
            String xslName = args[2].concat(".xsl");
            StreamResult sr = new StreamResult(new File(args[1]));
            StreamSource ss = new StreamSource(Main.class.getClassLoader().getResourceAsStream(xslName));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer tranny = factory.newTransformer(ss);
            tranny.transform(new StreamSource(infile), sr);

            System.out.println("Complete. Results written to " + args[1]);

        } catch (Exception ex) {
            System.out.println("Internal Error!");
            ex.printStackTrace();
            return;
        }

    }

}
