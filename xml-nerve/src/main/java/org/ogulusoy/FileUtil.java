package org.ogulusoy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Utility class for performing file-related operations.
 * @author Oguzhan Ulusoy
 */
public class FileUtil {
    /**
     * Reads the entire content of a file and returns it as a single String.
     *
     * @param filePath the path to the file to be read
     * @return the content of the file as a String
     * @throws IOException if an I/O error occurs reading from the file or a malformed input is detected
     */
    public static String readFileToString(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}