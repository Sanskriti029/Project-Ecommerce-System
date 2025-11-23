package utils;

import java.io.*;
import java.util.*;

/**
 * FileHandler - utility class for file operations
 * Handles reading and writing data to text files
 */
public class FileHandler {

    // Read file and return list of lines
    public List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        
        // Create data directory if it doesn't exist
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(filename);
        
        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            System.err.println(e.getMessage());
        }

        return lines;
    }

    // Write list of lines to file
    public boolean writeFile(String filename, List<String> lines) {
        // Create data directory if it doesn't exist
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing file: " + filename);
            System.err.println(e.getMessage());
            return false;
        }
    }

    // Append line to file
    public boolean appendToFile(String filename, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error appending to file: " + filename);
            System.err.println(e.getMessage());
            return false;
        }
    }

    // Check if file exists
    public boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    // Delete file
    public boolean deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    // Create empty file
    public boolean createFile(String filename) {
        try {
            File file = new File(filename);
            return file.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating file: " + filename);
            return false;
        }
    }
}