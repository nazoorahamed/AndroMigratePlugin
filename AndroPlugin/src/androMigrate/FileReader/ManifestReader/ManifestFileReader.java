package androMigrate.FileReader.ManifestReader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ManifestFileReader {

    public List<ManifestLineDetails> readDetails(File file){

        List<ManifestLineDetails> details = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int number=0;
                while ((line = br.readLine()) != null) {
                    number++;
                    ManifestLineDetails jr = new ManifestLineDetails(file,number,line);

                    details.add(jr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  details;
    }

    public ManifestDetails getManifestDetails(File file){

        List<ManifestLineDetails> detailsLine = readDetails(file);
        List<ManifestLineDetails> usesPermission = new ArrayList<>();
        List<ManifestLineDetails> services = new ArrayList<>();
        TreeMap<String,Integer> appLineDetails = new TreeMap<>();
        ManifestLineDetails usesSdk = null;

        for (ManifestLineDetails manifestLineDetails : detailsLine) {

            String line = manifestLineDetails.getCodeLine();
            int linenumber = manifestLineDetails.getLineNumber();

            if (line.contains("uses-sdk")) {
                System.out.println(manifestLineDetails.getLineNumber() + " : Manifest uses sdk True");
                usesSdk = new ManifestLineDetails(file, linenumber, manifestLineDetails.getCodeLine());
            }

            if (line.contains("uses-permission")) {
                System.out.println(manifestLineDetails.getLineNumber() + " : Manifest uses permission True");
                ManifestLineDetails jr = new ManifestLineDetails(file, linenumber, line);
                usesPermission.add(jr);
            }

            if (line.contains("service")) {
                System.out.println(manifestLineDetails.getLineNumber() + " : Manifest service True");
                ManifestLineDetails jr = new ManifestLineDetails(file, linenumber, line);
                services.add(jr);
            }

            if (line.contains("</application>")) {
                appLineDetails.put("appLine", manifestLineDetails.getLineNumber());
            }
        }
        return new ManifestDetails(file,usesSdk,usesPermission,services,detailsLine,appLineDetails);
    }
}
