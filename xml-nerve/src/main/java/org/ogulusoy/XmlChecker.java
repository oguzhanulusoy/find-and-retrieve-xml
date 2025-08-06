package org.ogulusoy;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for checking the existence of XML elements by tag name within an XML string.
 * @author Oguzhan Ulusoy
 */
public class XmlChecker {

    /**
     * Checks whether an element with the specified tag name exists within the given XML content.
     *
     * @param xml the XML content as a String
     * @param tagName the tag name of the element to search for
     * @return {@code true} if an element with the given tag name exists, {@code false} otherwise or if an error occurs
     */
    public static boolean elementExistsInXml(String xml, String tagName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            Document doc = factory.newDocumentBuilder().parse(
                    new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            return elementExistsRecursive(doc.getDocumentElement(), tagName);

        } catch (IOException | SAXException | ParserConfigurationException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    /**
     * Recursively searches the DOM tree starting from the specified node to find
     * an element with the given tag name.
     *
     * @param node the starting node for the search
     * @param tagName the tag name to match
     * @return {@code true} if a matching element is found, {@code false} otherwise
     */
    private static boolean elementExistsRecursive(Node node, String tagName) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String name = node.getNodeName();
            if (name.equals(tagName) || node.getLocalName().equals(tagName)) {
                return true;
            }
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (elementExistsRecursive(children.item(i), tagName)) {
                return true;
            }
        }
        return false;
    }
}