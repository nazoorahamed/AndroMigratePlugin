package androMigrate.SystemOperate;

import androMigrate.FileReader.GradleReader.GradleDetails;
import androMigrate.FileReader.JavaReader.JavaLineDetails;
import androMigrate.FileReader.ManifestReader.ManifestDetails;
import androMigrate.FindAndReplace.LineEditor;

import java.io.File;
import java.util.List;

public class MappingAPI {
    int API_LEVEL = 28;
    String METHOD = "startForeground";
    String STATUS = "Active";
    String NEW_METHOD_AVAILABLE = "null";
    String NEW_METHOD = "null";
    String METHOD_PARAMETER = "replace";
    String PARAMETER = "(Locale.Category.DISPLAY)";
    String CODE_SINPPET = "tryandcatch";
    String CATCH_LEVEL = "O";
    int NEW_API_LEVEL = 29;
    String GRADLE_CHANGE = "null";
    String GRADLE_CODE = "null";
    String MANIF_CHANGE = "add";
    String PERMISSION_NAME = "FOREGROUND_SERVICE";
    String PERMISISON = "    <uses-permission android:name=\"android.permission.FOREGROUND_SERVICE\"/>";
    String RUNTIME_REQ = "yes";
    String MANIF_APP_TYPE = "null";
    String MANIF_APP = "null";
    String COMMENT = "null";
    String REFER_LINK = "https://developer.android.com/about/versions/pie/android-9.0-changes-28#fg-svc";

    APICodeGenerator codeGenerator = new APICodeGenerator();
    LineEditor lineEditor = new LineEditor();
    GradleDetails MainGradDetails;
    ManifestDetails MainManifDetails;
    List<File> MainJavafiles;
    List<JavaLineDetails> CurrentJavaFile;

    public boolean MapAPI(GradleDetails gradleDetails, ManifestDetails manifestDetails, List<File> jFiles){
        MainGradDetails =gradleDetails;
        MainManifDetails = manifestDetails;
        for(int i=0;i<jFiles.size();i++){
            List<JavaLineDetails> javaLineDetails = codeGenerator.readJavaFile(jFiles.get(i));
            for (int k=0;k<javaLineDetails.size();k++){
                System.out.println("sooora"+javaLineDetails.get(k).getLastLineDetail().values() + " :: " + javaLineDetails.get(k).getFileP().getName());
                checkLine(MainGradDetails, MainManifDetails,jFiles,javaLineDetails.get(k).getCodeLine(),javaLineDetails.get(k).getLineNumber(),javaLineDetails.get(k));
            }
        }
        return true;
    }

    public void findMappingInstance(GradleDetails gradleDetails, ManifestDetails manifestDetails, List<File> jFiles,String line, int lineNumber,JavaLineDetails javaLineDetails) {

        if (line.trim().contains(METHOD)) {
            if (STATUS.equals("Active") && NEW_METHOD_AVAILABLE.equals("null")) {
                //Active code
                if (GRADLE_CHANGE.equals("yes") && !MANIF_CHANGE.equals("null")) {
                    //gradle change and manif code
                    //get gradle code GRADLE_CODE
                    if (MANIF_CHANGE.equals("add")) {
                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //change both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider and permission
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib and permission
                            }
                        } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib
                            }
                        } else if (!PERMISSION_NAME.equals("null")) {
                            //Add only the permisison
                        }
                    } else if (MANIF_CHANGE.equals("remove")) {
                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //remove both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //remove provider and permission
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //remove lib and permission
                            }
                        } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //remove provider
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //remove lib
                            }
                        } else if (!PERMISSION_NAME.equals("null")) {
                            //remove only the permisison
                        }
                    }
                } else if (GRADLE_CHANGE.equals("null") && !MANIF_CHANGE.equals("null")) {
                    //only manif change
                    if (MANIF_APP_TYPE.equals("PROVIDE")) {
                        //remove provider and permission
                    } else if (MANIF_APP_TYPE.equals("LIB")) {
                        //remove lib and permission
                    }

                } else if (GRADLE_CHANGE.equals("yes")) {
                    //only Gradle change
                }
            } else if (STATUS.equals("Active") && NEW_METHOD_AVAILABLE.equals("yes")) {
                //Active code
            } else if (STATUS.equals("Deprecated") && NEW_METHOD_AVAILABLE.equals("yes")) {
                // Deprecated code
                if(!METHOD_PARAMETER.equals("null")){
                    if(METHOD_PARAMETER.equals("same")){
                        //pass the same parameter which old code has
                    }else if(METHOD_PARAMETER.equals("replace")){
                        //pass the parameter with new
                    }else if(METHOD_PARAMETER.equals("addreplace")){
                        //added the old one with new paramert
                    }
                }else {

                }

            }


        } else {
            System.out.println("not matched");
        }

    }


    public void checkLine(GradleDetails gradleDetails, ManifestDetails manifestDetails,List<File> jFiles,String line, int lineNumber,JavaLineDetails javaLineDetails){

        if(line.contains(METHOD) && !NEW_METHOD_AVAILABLE.equals("null")){
            line.replaceAll(METHOD,NEW_METHOD);
        }
        if (GRADLE_CHANGE.equals("yes")){

        }
        if (MANIF_CHANGE.equals("add")){
            if(!PERMISISON.equals("null")){
                Boolean isPermissionAdded =true;
                for (int i = 0; i< MainManifDetails.getUsesPermission().size(); i++){
                    if(manifestDetails.getUsesPermission().get(i).getCodeLine().contains("FOREGROUND_SERVICE")){
                        isPermissionAdded =true;
                        break;
                    }else {
                        isPermissionAdded = false;
                    }
                }
                if(!isPermissionAdded){
                    try {
                        lineEditor.addNewLine(manifestDetails.getFile(),manifestDetails.getUsesPermission().get(0).getLineNumber(),PERMISISON);
                        lineEditor.addNewLine(manifestDetails.getFile(),manifestDetails.getUsesPermission().get(0).getLineNumber(),"<!-- Refer this link to know more "+REFER_LINK +"   -->");
                        MainGradDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
