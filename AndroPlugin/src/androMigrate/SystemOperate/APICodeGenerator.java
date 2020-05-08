package androMigrate.SystemOperate;


import androMigrate.FileReader.GradleReader.GradleDetails;
import androMigrate.FileReader.GradleReader.GradleFIleReader;
import androMigrate.FileReader.JavaReader.JavaFileReader;
import androMigrate.FileReader.JavaReader.JavaLineDetails;
import androMigrate.FileReader.ManifestReader.ManifestDetails;
import androMigrate.FileReader.ManifestReader.ManifestFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class APICodeGenerator {

    public boolean getSourceFiles(List<File> jFile,List<File> gradleFile,List<File> manifFile){

        SetupMigration setupMigration = new SetupMigration();
        Boolean isPreprocessed = setupMigration.preProcessCode(readManifestFile(manifFile.get(0)),readGradleFile(gradleFile.get(0)),jFile,manifFile,gradleFile);

        if(isPreprocessed){
            return true;
        }else {
            return false;
        }
    }

    public ManifestDetails readManifestFile(File file){
        ManifestDetails manifestDetails =null;
        //read manifest files

            ManifestFileReader mr = new ManifestFileReader();
            manifestDetails = mr.getManifestDetails(file);

        return manifestDetails;
    }

    public GradleDetails readGradleFile(File file){
        GradleDetails gradleDetails = null;
        // Read gradle files

            GradleFIleReader gd = new GradleFIleReader();
            gradleDetails = gd.getGradleDetails(file);

        return gradleDetails;
    }

    public List<JavaLineDetails> readJavaFile(File file){
        // read java files
            JavaFileReader jr = new JavaFileReader();
            List<JavaLineDetails> jrd =  jr.readDetails(file);

            try {
                long lines = Files.lines(file.toPath()).count();
                System.out.println("Number of Lines: " + lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return jrd;
    }


}
