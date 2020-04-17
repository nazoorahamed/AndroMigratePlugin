package androMigrate.FileReader.JavaReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaFileReader {

    public List<JavaLineDetails> readDetails(File file) {
        List<JavaLineDetails> lineDetails;
        lineDetails = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int number=0;
                    while ((line = br.readLine()) != null) {
                        // process the line.
                        number++;
                        JavaLineDetails jr = new JavaLineDetails(file,number,line);

                        lineDetails.add(jr);

                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineDetails;

    }
}
