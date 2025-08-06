package org.vf;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for extracting a single tag's text value from XML content based on parent-child hierarchy.
 * @author Oguzhan Ulusoy
 */
public class XmlSingleValueExtractor {

    /**
     * Recursively searches the provided node and its descendants for an element with the specified tag name
     * and returns its text content.
     *
     * @param node the starting node to search
     * @param childTag the tag name of the child element to find
     * @return the trimmed text content of the first matching child element, or {@code null} if not found
     */
    public static String findTagValueRecursive(Node node, String childTag) {
        NodeList children = node.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getLocalName().equals(childTag) || child.getNodeName().equals(childTag)) {
                    return child.getTextContent().trim();
                }
                String nestedValue = findTagValueRecursive(child, childTag);
                if (nestedValue != null) {
                    return nestedValue;
                }
            }
        }
        return null;
    }

    /**
     * Parses the provided XML string and searches for a child element with the specified tag name
     * under any parent element with the given parent tag name. Returns the text content of the first match found.
     *
     * @param parentTag the tag name of the parent element
     * @param childTag the tag name of the child element to search for under the parent
     * @param xml the XML content as a String
     * @return the trimmed text content of the first matching child element, or {@code null} if not found or an error occurs
     */
    public static String getChildTagValueUnderParent(String parentTag, String childTag, String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().parse(
                    new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            NodeList parents = doc.getElementsByTagNameNS("*", parentTag);

            for (int i = 0; i < parents.getLength(); i++) {
                Node parent = parents.item(i);
                String value = findTagValueRecursive(parent, childTag);
                if (value != null) {
                    return value;
                }
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }
}
