package androMigrate.SystemOperate;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MigrationContainer {
    public static List<File> JFiles;
    public static List<File> GradleFile;
    public static List<File> ManifFile;

    public void getAllFilePaths(String path){
        JFiles = new ArrayList<>();
        GradleFile = new ArrayList<>();
        ManifFile = new ArrayList<>();
        listF(path);
        System.out.println("===================================");
        System.out.println("Gradle files : "+GradleFile.size());
        System.out.println("Java Files : "+JFiles.size());
        System.out.println("Manifest files : "+ManifFile.size());

        APICodeGenerator codeGenerator = new APICodeGenerator();
        codeGenerator.getSourceFiles(JFiles,GradleFile,ManifFile);
    }

    public List<File> listF(String path) {

        File directory = new File(path);
        List<File> resultList = new ArrayList<>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                String type =  getFileExtension(file.getAbsolutePath());
                String filename = file.getName();
                //System.out.println(type);
                if (filename.equals("AndroidManifest.xml") && file.getAbsolutePath().contains("src") ){
                    System.out.println("Manifest is out");
                    ManifFile.add(file);
                    System.out.println(file.getAbsolutePath());
                }
                if(type.equals("java") && file.getAbsolutePath().contains("src")){
                    System.out.println("java file");
                    JFiles.add(file);
                    System.out.println(file.getAbsolutePath());
                }
                if(type.equals("gradle") && file.getAbsolutePath().contains("build.gradle") && file.getAbsolutePath().contains("app")){
                    System.out.println("gradle file");
                    System.out.println(file.getAbsolutePath());
                    GradleFile.add(file);
                }
            } else if (file.isDirectory()) {
                resultList.addAll(listF(file.getAbsolutePath()));
            }
        }
        return resultList;
    }

    public String getFileExtension(String fullName) {
        // checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}
