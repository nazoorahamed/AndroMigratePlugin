package androMigrate.FileReader.JavaReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class JavaFileReader {

    public List<JavaLineDetails> readDetails(File file) {
        List<JavaLineDetails> lineDetails;
        lineDetails = new ArrayList<>();
        TreeMap<String, Integer> lastLineDetail = new TreeMap<>();


        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int number = 0;
                int lastLine = 0;
                while ((line = br.readLine()) != null) {
                    number++;
                    if (line.trim().equals("}")) {
                        lastLine = number;
                    }
                }
                lastLineDetail.put("LastLine", lastLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int numbers = 0;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    numbers++;
                    JavaLineDetails jr = new JavaLineDetails(file, numbers, line, lastLineDetail);

                    lineDetails.add(jr);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineDetails;

    }
}
