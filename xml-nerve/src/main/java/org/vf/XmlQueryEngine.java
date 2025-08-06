package org.vf;

import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Utility class for executing XQuery expressions on XML documents using the Saxon HE processor.
 * @author Oguzhan Ulusoy
 */
public class XmlQueryEngine {

    /**
     * Evaluates the given XQuery expression against the specified XML file and returns
     * whether the result is logically {@code true}.
     *
     * @param xquery the XQuery expression to evaluate
     * @param xml the path to the XML file to query
     * @return {@code true} if the result of the query is "true", {@code false} otherwise or if an error occurs
     */
    public static boolean isExists(String xquery, String xml) {
        return Boolean.parseBoolean(getQueryResult(xquery, xml));
    }

    /**
     * Evaluates the given XQuery expression against the specified XML file and returns the result as a string.
     *
     * @param xquery the XQuery expression to evaluate
     * @param xml the path to the XML file to query
     * @return the result of the query as a string, or {@code null} if an error occurs
     */
    public static String getValue(String xquery, String xml) {
        return getQueryResult(xquery, xml);
    }

    /**
     * Executes the provided XQuery expression against the given XML file and returns the result.
     *
     * @param xquery the XQuery expression to evaluate
     * @param xml the path to the XML file to query
     * @return the result of the query as a string, or {@code null} if an error occurs
     */
    public static String getQueryResult(String xquery, String xml) {
        try {
            Processor processor = new Processor(false);
            XQueryCompiler compiler = processor.newXQueryCompiler();
            /*
            StreamSource sourceStream = new StreamSource(new StringReader(xml));
            XdmNode source = processor.newDocumentBuilder().build(sourceStream);
             */
            XdmNode source = processor.newDocumentBuilder().build(new StreamSource(new File(xml)));
            XQueryExecutable exec = compiler.compile(xquery);
            XQueryEvaluator evaluator = exec.load();
            evaluator.setContextItem(source);
            XdmValue result = evaluator.evaluate();
            StringBuilder sb = new StringBuilder();
            for (XdmItem item : result) {
                sb.append(item.getStringValue()).append("\n");
            }
            String resultString = sb.toString().trim();
            return resultString;
        } catch (SaxonApiException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}