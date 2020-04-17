package androMigrate.SystemOperate;



import androMigrate.FileReader.GradleReader.GradleDependencies;
import androMigrate.FileReader.GradleReader.GradleDetails;
import androMigrate.FileReader.ManifestReader.ManifestDetails;
import androMigrate.FileReader.ManifestReader.ManifestLineDetails;
import androMigrate.FindAndReplace.LineEditor;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

public class SetupMigration {
    APICodeGenerator codeGenerator = new APICodeGenerator();
    LineEditor lineEditor = new LineEditor();
    MappingAPI mappingAPI = new MappingAPI();

    public boolean preProcessCode(ManifestDetails manifestDetails, GradleDetails gradleDetails, List<File> jFile, List<File> maniFile, List<File> gradFile) {
        GradleDetails preProcessedGradle = Gradlesetup(gradleDetails, jFile, maniFile, gradFile);
        ManifestDetails preProcessedManifest = ManifestSetup(manifestDetails);
        List<File> preProcessedJava = JavaCodeLineSetup(jFile);
        Boolean isAPIMapped =  mappingAPI.MapAPI(preProcessedGradle,preProcessedManifest,preProcessedJava);

        if(isAPIMapped){
            return true;
        }else {
            return false;
        }
    }

    public GradleDetails Gradlesetup(GradleDetails gradleDetails, List<File> jFile, List<File> maniFile, List<File> gradFile) {
        System.out.println("Pre processing :" + gradleDetails.getTargetsdkline().headMap("29"));
        System.out.println(" line line " + gradleDetails.getTargetsdkline());


        for (int i = 0; i < gradleDetails.getDependencies().size(); i++) {
            System.out.println("depends" + gradleDetails.getDependencies().get(i).getCodeLine() + gradleDetails.getDependencies().get(i).getLineNumber());
        }
        return GradleMapping(gradleDetails, gradleDetails.getDependencies(), gradleDetails.getTargetsdkline());
    }

    public GradleDetails GradleMapping(GradleDetails gdDetails, List<GradleDependencies> dependencies, TreeMap<String, Integer> sdklinedetails) {

        GradleDetails gradleDetails = gdDetails;
        List<GradleDependencies> dependenciesList = dependencies;
        TreeMap<String, Integer> sdkLine = sdklinedetails;

        int targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
        int minSdk = Integer.parseInt(gradleDetails.getMinSdk());
        ;
        int compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
        System.out.println(targersdk + "::" + minSdk + "::" + compilesdk);

        if (targersdk < 29) {
            try {
                //addNewLine.removeLine(gdDetails.getFile(),sdklinedetails.get("targetSdkVersion"));
                lineEditor.replaceLine(gdDetails.getFile(), sdklinedetails.get("targetSdkVersion"), "        targetSdkVersion 29");

                gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                dependenciesList = gradleDetails.getDependencies();
                sdkLine = gradleDetails.getTargetsdkline();
                targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                ;
                compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (compilesdk < 29) {
            try {

                lineEditor.replaceLine(gdDetails.getFile(), sdklinedetails.get("compileSdkVersion"), "    compileSdkVersion 29");

                gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                dependenciesList = gradleDetails.getDependencies();
                sdkLine = gradleDetails.getTargetsdkline();
                targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //check dependencies
        for (int i = 0; i < dependenciesList.size(); i++) {
            if (dependenciesList.get(i).getCodeLine().contains("com.android.support:appcompat-v7")) {
                System.out.println(dependenciesList.get(i).getLineNumber() + " yes depend");
                lineEditor.replaceLine(gradleDetails.getFile(), dependenciesList.get(i).getLineNumber(), "    implementation 'androidx.appcompat:appcompat:1.0.0'");
                gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                dependenciesList = gradleDetails.getDependencies();
                sdkLine = gradleDetails.getTargetsdkline();
                targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
            }
            if (dependenciesList.get(i).getCodeLine().contains("com.android.support.constraint:constraint-layout")) {
                System.out.println(dependenciesList.get(i).getLineNumber() + " yes depend");
                lineEditor.replaceLine(gradleDetails.getFile(), dependenciesList.get(i).getLineNumber(), "    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'");
                gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                dependenciesList = gradleDetails.getDependencies();
                sdkLine = gradleDetails.getTargetsdkline();
                targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
            }
        }
        return  gradleDetails;
    }

    public ManifestDetails ManifestSetup(ManifestDetails mainf) {

        ManifestDetails manifestDetails = mainf;
        ManifestLineDetails usesSdk = manifestDetails.getUsesSdk();
        List<ManifestLineDetails> usesPermission = manifestDetails.getUsesPermission();
        List<ManifestLineDetails> services = manifestDetails.getServices();
        List<ManifestLineDetails> codeDetails = manifestDetails.getCodeDetails();

        if (usesSdk == null) {
            System.out.println("no sdk line found");
        } else {
            try {
                lineEditor.removeLine(manifestDetails.getFile(), usesSdk.getLineNumber());
                manifestDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                usesSdk = manifestDetails.getUsesSdk();
                usesPermission = manifestDetails.getUsesPermission();
                services = manifestDetails.getServices();
                codeDetails = manifestDetails.getCodeDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return manifestDetails;
    }

    public List<File> JavaCodeLineSetup(List<File> jFile) {
        for (int i = 0; i < jFile.size(); i++) {
            codeGenerator.readJavaFile(jFile.get(i));
            System.out.println(" ababba :" + jFile.get(i).getAbsolutePath());
        }
        return jFile;
    }

}
