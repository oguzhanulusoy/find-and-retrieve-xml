package org.vf;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for checking if a specific child tag exists anywhere under a given parent tag
 * within an XML document represented as a String.
 * @author Oguzhan Ulusoy
 */
public class XmlDeepChildChecker {

    /**
     * Recursively checks if the given node has a descendant element with the specified tag name.
     *
     * @param node the starting node to search under
     * @param childTag the tag name of the descendant element to find
     * @return {@code true} if a descendant element with the specified tag name exists, {@code false} otherwise
     */
    public static boolean hasDescendantTag(Node node, String childTag) {
        NodeList children = node.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (child.getLocalName().equals(childTag) || child.getNodeName().equals(childTag)) {
                    return true;
                }
                if (hasDescendantTag(child, childTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether any element with the specified parent tag contains
     * a descendant element with the specified child tag within the provided XML content.
     *
     * @param parentTag the tag name of the parent element
     * @param childTag the tag name of the descendant child element to search for
     * @param xml the XML content as a String
     * @return {@code true} if any parent element contains a descendant with the child tag, {@code false} otherwise or if an error occurs
     */
    public static boolean hasChildTagUnderParent(String parentTag, String childTag, String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().parse(
                    new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            NodeList parents = doc.getElementsByTagNameNS("*", parentTag);

            for (int i = 0; i < parents.getLength(); i++) {
                Node parent = parents.item(i);
                if (hasDescendantTag(parent, childTag)) {
                    return true;
                }
            }

        } catch (IOException | SAXException | ParserConfigurationException exception) {
            System.out.println(exception.getMessage());
        }
        return false;
    }
}
