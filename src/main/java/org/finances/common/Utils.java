package org.finances.common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class Utils {
    public static String getInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.next();
    }
    public static File getFile(String fileName) {
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        return new File(currentDir + "/" + fileName);
    }
}
