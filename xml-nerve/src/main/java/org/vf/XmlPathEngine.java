package org.vf;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;

/**
 * Utility class for evaluating XPath expressions on XML content provided as a String.
 * @author Oguzhan Ulusoy
 */
public class XmlPathEngine {

    /**
     * Checks if the result of the given XPath expression exists in the specified XML file.
     *
     * @param xml the path to the XML file to be queried
     * @param expression the XPath expression to evaluate
     * @return {@code true} if the XPath expression matches at least one node, {@code false} otherwise
     */
    public static boolean isExists(String xml, String expression) {
        return getQueryResult(xml, expression) != null;
    }

    /**
     * Retrieves the text content of the node matched by the given XPath expression
     * from the specified XML file.
     *
     * @param xml the path to the XML file to be queried
     * @param expression the XPath expression to evaluate
     * @return the text content of the matched node, or {@code null} if no node matches or an error occurs
     */
    public static String getValue(String xml, String expression) {
        return getQueryResult(xml, expression);
    }

    /**
     * Evaluates the given XPath expression against the specified XML file and returns the text content
     * of the first matching node.
     *
     * @param xml the path to the XML file to be parsed
     * @param expression the XPath expression to evaluate
     * @return the text content of the first node matching the XPath expression,
     *         or {@code null} if no match is found or an error occurs
     */
    public static String getQueryResult(String xml, String expression) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Document document = builder.parse(new File(xml));
            InputSource is = new InputSource(new StringReader(xml));
            Document document = builder.parse(is);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression compiledExpr = xpath.compile(expression);

            Node node = (Node) compiledExpr.evaluate(document, XPathConstants.NODE);

            if (node != null) {
                return node.getTextContent();
            }
        } catch (ParserConfigurationException | XPathExpressionException | SAXException | IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
