package androMigrate.SystemOperate;



import androMigrate.FileReader.GradleReader.GradleDependencies;
import androMigrate.FileReader.GradleReader.GradleDetails;
import androMigrate.FileReader.ManifestReader.ManifestDetails;
import androMigrate.FileReader.ManifestReader.ManifestLineDetails;
import androMigrate.FindAndReplace.LineEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static androMigrate.SystemOperate.MappingAPI.ShowOnly;
import static androMigrate.SystemOperate.MappingAPI.showOnlyChanges;

public class SetupMigration {
    APICodeGenerator codeGenerator = new APICodeGenerator();
    LineEditor lineEditor = new LineEditor();
    MappingAPI mappingAPI = new MappingAPI();


    public boolean preProcessCode(ManifestDetails manifestDetails, GradleDetails gradleDetails, List<File> jFile, List<File> maniFile, List<File> gradFile) {
        showOnlyChanges = new ArrayList<>();
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
        return GradleMapping(gradleDetails, gradleDetails.getDependencies(), gradleDetails.getTargetsdkline());
    }

    public GradleDetails GradleMapping(GradleDetails gdDetails, List<GradleDependencies> dependencies, TreeMap<String, Integer> sdklinedetails) {

        GradleDetails gradleDetails = gdDetails;
        List<GradleDependencies> dependenciesList = dependencies;
        TreeMap<String, Integer> sdkLine = sdklinedetails;

        int targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
        int minSdk = Integer.parseInt(gradleDetails.getMinSdk());
        int compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
        System.out.println(targersdk + "::" + minSdk + "::" + compilesdk);

        if (targersdk < 29) {
            try {
                //addNewLine.removeLine(gdDetails.getFile(),sdklinedetails.get("targetSdkVersion"));
                if(ShowOnly){
                    showOnlyChanges.add("changes in "+gdDetails.getFile().getName()+" in line number "+sdklinedetails.get("targetSdkVersion")+", Changes  :"+" targetSdkVersion 29");
                }else {
                    lineEditor.replaceLine(gdDetails.getFile(), sdklinedetails.get("targetSdkVersion"), "        targetSdkVersion 29",true);
                    gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                    dependenciesList = gradleDetails.getDependencies();
                    sdkLine = gradleDetails.getTargetsdkline();
                    targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                    minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                    compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (compilesdk < 29) {
            try {

                if(ShowOnly){
                    showOnlyChanges.add("changes in "+gdDetails.getFile().getName()+" in line number "+sdklinedetails.get("compileSdkVersion")+", Changes  :"+" compileSdkVersion 29");
                }else {
                    lineEditor.replaceLine(gdDetails.getFile(), sdklinedetails.get("compileSdkVersion"), "    compileSdkVersion 29", true);
                    gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                    dependenciesList = gradleDetails.getDependencies();
                    sdkLine = gradleDetails.getTargetsdkline();
                    targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                    minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                    compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            try {

                if (sdklinedetails.get("buildToolsVersion") != null) {
                    if (!gdDetails.getCodeDetails().get(sdklinedetails.get("buildToolsVersion")).getCodeLine().contains("28.0.3")) {

                        if (ShowOnly) {
                            showOnlyChanges.add("changes in " + gdDetails.getFile().getName() + " in line number " + sdklinedetails.get("buildToolsVersion") + ", Changes  :" + " buildToolsVersion '28.0.3'");
                        } else {
                            lineEditor.replaceLine(gdDetails.getFile(), sdklinedetails.get("buildToolsVersion"), "     buildToolsVersion '28.0.3'", true);

                            gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                            dependenciesList = gradleDetails.getDependencies();
                            sdkLine = gradleDetails.getTargetsdkline();
                            targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                            minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                            compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        //check dependencies
        for (int i = 0; i < dependenciesList.size(); i++) {
            if (dependenciesList.get(i).getCodeLine().contains("com.android.support:appcompat-v7")) {
                if (ShowOnly) {
                    showOnlyChanges.add("changes in " + gdDetails.getFile().getName() + " in line number " + dependenciesList.get(i).getLineNumber() + ", Changes  :" + " implementation 'androidx.appcompat:appcompat:1.0.0'\"");
                } else {
                    System.out.println(dependenciesList.get(i).getLineNumber() + " yes depend");
                    lineEditor.replaceLine(gradleDetails.getFile(), dependenciesList.get(i).getLineNumber(), "    implementation 'androidx.appcompat:appcompat:1.0.0'", true);
                    gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                    dependenciesList = gradleDetails.getDependencies();
                    sdkLine = gradleDetails.getTargetsdkline();
                    targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                    minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                    compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
                }
            }
            if (dependenciesList.get(i).getCodeLine().contains("com.android.support.constraint:constraint-layout")) {
                if (ShowOnly) {
                    showOnlyChanges.add("changes in " + gdDetails.getFile().getName() + " in line number " + dependenciesList.get(i).getLineNumber() + ", Changes  :" + "  implementation 'androidx.constraintlayout:constraintlayout:1.1.3'\"");
                } else {
                    System.out.println(dependenciesList.get(i).getLineNumber() + " yes depend");
                    lineEditor.replaceLine(gradleDetails.getFile(), dependenciesList.get(i).getLineNumber(), "    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'", true);
                    gradleDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                    dependenciesList = gradleDetails.getDependencies();
                    sdkLine = gradleDetails.getTargetsdkline();
                    targersdk = Integer.parseInt(gradleDetails.getTargetSdk());
                    minSdk = Integer.parseInt(gradleDetails.getMinSdk());
                    compilesdk = Integer.parseInt(gradleDetails.getCompileSdk());
                }
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
                lineEditor.removeLine(manifestDetails.getFile(), usesSdk.getLineNumber(),true);
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
