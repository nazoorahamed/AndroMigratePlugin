package androMigrate.SystemOperate;

import androMigrate.FileReader.GradleReader.GradleDetails;
import androMigrate.FileReader.JavaReader.JavaLineDetails;
import androMigrate.FileReader.ManifestReader.ManifestDetails;
import androMigrate.FindAndReplace.LineEditor;
import org.apache.commons.lang3.StringUtils;

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
    String GRADLE_CHANGE = "yes";
    String GRADLE_CODE = "use 'apache.http'";
    String MANIF_CHANGE = "add";
    String PERMISSION_NAME = "FOREGROUND_SERVICE";
    String PERMISISON = "    <uses-permission android:name=\"android.permission.FOREGROUND_SERVICE\"/>";
    String RUNTIME_REQ = "yes";
    String MANIF_APP_TYPE = "PROVIDE";
    String MANIF_APP = "<uses-library android:name=\"org.apache.http.legacy\" android:required=\"false\"/>";
    String COMMENT = "Apps that target Android 9 or higher and use foreground services must request the FOREGROUND_SERVICE permission. This is a normal permission";
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
        MainJavafiles = jFiles;
        for(int i=0;i<MainJavafiles.size();i++){
            CurrentJavaFile = codeGenerator.readJavaFile(MainJavafiles.get(i));
            for (int k=0;k<CurrentJavaFile.size();k++){
                String currentLine = CurrentJavaFile.get(k).getCodeLine().trim();
                if(currentLine.contains("(")&&currentLine.contains(")")){
                    String[] splitParameter = currentLine.split("(?=\\()");
                   // System.out.println(splitParameter[0]);
                    currentLine = splitParameter[0];
                }

               // System.out.println("sooora"+javaLineDetails.get(k).getLastLineDetail().values() + " :: " + javaLineDetails.get(k).getFileP().getName());
                findMappingInstance(MainGradDetails, MainManifDetails,MainJavafiles.get(i),currentLine,CurrentJavaFile.get(k),CurrentJavaFile);

            }
        }
        return true;
    }

    public void findMappingInstance(GradleDetails gradleDetails, ManifestDetails manifestDetails, File jFiles,String line,JavaLineDetails javaLineDetails,List<JavaLineDetails> javaLineDetailslines) {

        if (line.trim().equals(METHOD)) {
            if ((STATUS.equals("Active") || STATUS.equals("Deprecated")) && NEW_METHOD_AVAILABLE.equals("null")) {
                //Active code
                if (GRADLE_CHANGE.equals("yes") && !MANIF_CHANGE.equals("null")) {
                    //gradle change and manif code
                    //get gradle code GRADLE_CODE
                    if (MANIF_CHANGE.equals("add")) {
                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //change both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider and permission
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,true,false,true,true);

                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib and permission
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,true,true,true);

                            }
                        } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,true,false,false,true);

                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,true,false,true);

                            }
                        } else if (!PERMISSION_NAME.equals("null")) {
                            //Add only the permisison
                            changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,false,true,true);

                        }
                    } else if (MANIF_CHANGE.equals("remove")) {
                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //remove both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //remove provider and permission
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,true,false,true);

                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //remove lib and permission
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,false,true,true);

                            }
                        } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //remove provider
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,true,false,false);
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //remove lib
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,false,true,false);

                            }
                        } else if (!PERMISSION_NAME.equals("null")) {
                            //remove only the permission
                            changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,false,false,true);

                        }
                    }
                } else if (GRADLE_CHANGE.equals("null") && !MANIF_CHANGE.equals("null")) {
                    //only manif change
                    if (MANIF_CHANGE.equals("add")) {
                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //change both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider and permission
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,true,false,true,false);
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib and permission
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,true,true,false);
                            }
                        } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,true,false,false,false);
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib
                                changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,true,false,false);
                            }
                        } else if (!PERMISSION_NAME.equals("null")) {
                            //Add only the permission
                            changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,false,true,false);
                        }
                    } else if (MANIF_CHANGE.equals("remove")) {

                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //remove both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //remove provider and permission
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,true,false,true);
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //remove lib and permission
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,false,true,true);
                            }
                        } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //remove provider
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,true,false,false);
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //remove lib
                                changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,false,true,false);
                            }
                        } else if (!PERMISSION_NAME.equals("null")) {
                            //remove only the permission
                            changeNonMethodRemoveGradleManifAndPermission(gradleDetails,manifestDetails,false,false,true);
                        }
                    }
                } else if (GRADLE_CHANGE.equals("yes")) {
                    //only Gradle change
                    changeNonMethodAddGradleManifAndPermission(gradleDetails,manifestDetails,false,false,false,true);
                }

            } else if (STATUS.equals("Active") && NEW_METHOD_AVAILABLE.equals("yes")) {
                //Active code and method available
                //Current research never found any use case for this condition and will be implemented whenever the data is found

            } else if (STATUS.equals("Deprecated") && NEW_METHOD_AVAILABLE.equals("yes")) {
                // Deprecated code
                if (!METHOD_PARAMETER.equals("null") && !CODE_SINPPET.equals("null")) {

                    if (GRADLE_CHANGE.equals("yes") && !MANIF_CHANGE.equals("null")) {
                        //gradle change and manif code
                        //get gradle code GRADLE_CODE
                        if (MANIF_CHANGE.equals("add")) {
                            if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                //change both permission and manifest body
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //add provider and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,true,true,true);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //Add lib and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,true,true,true);

                                }
                            } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //add provider
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,false,true,true);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //Add lib
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,false,true,true);

                                }
                            } else if (!PERMISSION_NAME.equals("null")) {
                                //Add only the permisison
                                changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,false,true,true,true);
                            }

                        } else if (MANIF_CHANGE.equals("remove")) {
                            if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                //remove both permission and manifest body
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //remove provider and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,true,false,false);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //remove lib and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,true,false,false);

                                }
                            } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //remove provider
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,false,false,false);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //remove lib
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,false,false,false);

                                }
                            } else if (!PERMISSION_NAME.equals("null")) {
                                //remove only the permisison
                                changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,false,true,false,false);

                            }
                        }
                    } else if (GRADLE_CHANGE.equals("null") && !MANIF_CHANGE.equals("null")) {
                        //only manif change
                        if (MANIF_CHANGE.equals("add")) {
                            if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                //change both permission and manifest body
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //add provider and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,true,false,true);


                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //Add lib and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,true,false,true);
                                }
                            } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //add provider
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,false,false,true);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //Add lib
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,false,false,true);

                                }
                            } else if (!PERMISSION_NAME.equals("null")) {
                                //Add only the permisison
                                changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,false,true,false,true);
                            }
                        } else if (MANIF_CHANGE.equals("remove")) {
                            if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                //remove both permission and manifest body
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //remove provider and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,true,false,false);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //remove lib and permission
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,true,false,false);

                                }
                            } else if (PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //remove provider
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,true,false,false,false,false);

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //remove lib
                                    changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,true,false,false,false);

                                }
                            } else if (!PERMISSION_NAME.equals("null")) {
                                //remove only the permisison
                                changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,false,true,false,false);
                            }
                        }

                    } else if (GRADLE_CHANGE.equals("yes")) {
                        //only Gradle change
                        changeDeprecatedMethod(gradleDetails,manifestDetails,javaLineDetailslines, javaLineDetails ,false,false,false,true,true);

                    }
                } else if (!METHOD_PARAMETER.equals("null")) {
                    //Current research never found any use case for this condition and will be implemented whenever the data is found
                }
            }else if(NEW_METHOD_AVAILABLE.equals("null")&& (GRADLE_CHANGE.equals("null") && MANIF_CHANGE.equals("null"))){
                if(!COMMENT.equals("null") && !REFER_LINK.equals("null")){
                    //add only comment
                    AddOnlyComment(javaLineDetails,javaLineDetailslines);
                }
            }
        }
    }
    public void AddOnlyComment(JavaLineDetails details,List<JavaLineDetails> javaLineDetails) {
        CurrentJavaFile = javaLineDetails;
        try {
            lineEditor.addNewLine(details.getFileP(),details.getLineNumber(),COMMENT);
            lineEditor.addNewLine(details.getFileP(),details.getLineNumber(),"<!-- Refer this link to know more " + REFER_LINK + "   -->");
            lineEditor.addNewLine(details.getFileP(), details.getLineNumber(), "<!--        Code Generated by AndroMigrate           -->");
            CurrentJavaFile = codeGenerator.readJavaFile(details.getFileP());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeDeprecatedMethod(GradleDetails gradleDetails, ManifestDetails manifestDetails, List<JavaLineDetails> javaLineDetails, JavaLineDetails details, boolean provider, boolean lib, boolean Permission, boolean isGradleChange, boolean isAdd){
        MainManifDetails = manifestDetails;
        MainGradDetails = gradleDetails;
        CurrentJavaFile = javaLineDetails;

        String currentLine = details.getCodeLine().trim();
        String[] splitParameter = currentLine.split("(?=\\()");
        System.out.println(splitParameter[0]);
        System.out.println(splitParameter[1]);

        String sameParameter = StringUtils.substringBetween(currentLine, "(", ")");

        try {

            if(METHOD_PARAMETER.equals("same")){
                String newLine = NEW_METHOD.trim()+"("+sameParameter+")"+";";
                lineEditor.replaceLine(details.getFileP(),details.getLineNumber(),newLine);
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), COMMENT);
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), "<!-- Refer this link to know more " + REFER_LINK + "   -->");
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), "<!--        Code Generated by AndroMigrate           -->");
                CurrentJavaFile = codeGenerator.readJavaFile(details.getFileP());
            }
            else if(METHOD_PARAMETER.equals("replace")){
                String newLine = NEW_METHOD.trim()+"("+PARAMETER+")"+";";
                lineEditor.replaceLine(details.getFileP(),details.getLineNumber(),newLine);
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), COMMENT);
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), "<!-- Refer this link to know more " + REFER_LINK + "   -->");
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), "<!--        Code Generated by AndroMigrate           -->");
                CurrentJavaFile = codeGenerator.readJavaFile(details.getFileP());

            }
            else if(METHOD_PARAMETER.equals("addreplace")){
                String parameter = PARAMETER.replace("addreplace",sameParameter);
                String newLine = NEW_METHOD.trim()+"("+parameter+")"+";";
                lineEditor.replaceLine(details.getFileP(),details.getLineNumber(),newLine);
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), COMMENT);
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), "<!-- Refer this link to know more " + REFER_LINK + "   -->");
                lineEditor.addNewLine(MainManifDetails.getFile(), details.getLineNumber(), "<!--        Code Generated by AndroMigrate           -->");
                CurrentJavaFile = codeGenerator.readJavaFile(details.getFileP());
            }
            if(isAdd){
                changeNonMethodAddGradleManifAndPermission(MainGradDetails,MainManifDetails,provider,lib,Permission,isGradleChange);
            }else {
                changeNonMethodRemoveGradleManifAndPermission(MainGradDetails,MainManifDetails,provider,lib,Permission);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeNonMethodRemoveGradleManifAndPermission(GradleDetails gradleDetails,ManifestDetails manifestDetails,boolean provider,boolean lib, boolean Permission){
        MainManifDetails = manifestDetails;
        MainGradDetails = gradleDetails;
        try {
            if(Permission){
                int removeLine = 0;
                if (!PERMISISON.equals("null")) {
                    boolean isPermissionAdded = false;
                    for (int i = 0; i < MainManifDetails.getUsesPermission().size(); i++) {
                        if (MainManifDetails.getUsesPermission().get(i).getCodeLine().contains(PERMISSION_NAME)) {
                            isPermissionAdded = true;
                            removeLine = MainManifDetails.getUsesPermission().get(i).getLineNumber();
                            break;
                        }
                    }
                    if (isPermissionAdded && removeLine !=0) {

                        lineEditor.removeLine(MainManifDetails.getFile(),removeLine);
                        lineEditor.addNewLine(MainGradDetails.getFile(), removeLine,COMMENT);
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "<!-- Refer this link to know more " + REFER_LINK + "   -->");
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "<!--        Code Generated by AndroMigrate           -->");
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    }
                }
            }

            if(provider){
                if(MANIF_APP_TYPE.equals("PROVIDE")){
                    int removeLine = 0;
                    boolean isProvideAdded = false;
                    for (int i = 0; i < MainManifDetails.getCodeDetails().size(); i++) {
                        if (MainManifDetails.getCodeDetails().get(i).getCodeLine().contains(MANIF_APP)) {
                            removeLine = MainManifDetails.getCodeDetails().get(i).getLineNumber();
                            isProvideAdded = true;
                            break;
                        }
                    }
                    if(isProvideAdded && removeLine !=0){
                        lineEditor.removeLine(MainManifDetails.getFile(),removeLine);
                        lineEditor.addNewLine(MainGradDetails.getFile(), removeLine,COMMENT);
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "    <!-- Refer this link to know more " + REFER_LINK + "   -->");
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "    <!--        Code Generated by AndroMigrate           -->");
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    }
                }
            }

            if(lib){
                if(MANIF_APP_TYPE.equals("LIB")){

                    int removeLine = 0;
                    boolean isLibAdded = false;
                    for (int i = 0; i < MainManifDetails.getCodeDetails().size(); i++) {
                        if (MainManifDetails.getCodeDetails().get(i).getCodeLine().contains(MANIF_APP)) {
                            removeLine = MainManifDetails.getCodeDetails().get(i).getLineNumber();
                            isLibAdded = true;
                            break;
                        }
                    }
                    if(!isLibAdded){
                        lineEditor.removeLine(MainManifDetails.getFile(),removeLine);
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "    "+COMMENT);
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "    <!-- Refer this link to know more " + REFER_LINK + "   -->");
                        lineEditor.addNewLine(MainManifDetails.getFile(), removeLine, "    <!--        Code Generated by AndroMigrate           -->");
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeNonMethodAddGradleManifAndPermission(GradleDetails gradleDetails,ManifestDetails manifestDetails,boolean provider,boolean lib, boolean Permisison,boolean isgradleChange) {
        MainManifDetails = manifestDetails;
        MainGradDetails = gradleDetails;
        try {

            if(isgradleChange){
                if (!GRADLE_CODE.equals("null")) {
                    boolean isGradleLineAdded= true;
                    for(int i=0;i<MainGradDetails.getCodeDetails().size();i++){
                        if(MainGradDetails.getCodeDetails().get(i).getCodeLine().contains(GRADLE_CODE)){
                            isGradleLineAdded = true;
                            break;
                        }else {
                            isGradleLineAdded = false;
                        }
                    }

                    if(!isGradleLineAdded){
                        int defaultConfigLine = MainGradDetails.getTargetsdkline().get("defaultConfig");
                        lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine, "        "+GRADLE_CODE);
                        lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine,"         Refer this link to know more " + REFER_LINK +"   */");
                        lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine,COMMENT);
                        lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine,"/*        Code Generated by AndroMigrate          ");
                        MainGradDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                    }
                }
            }

            if(Permisison){
                if (!PERMISISON.equals("null")) {
                    boolean isPermissionAdded = true;
                    for (int i = 0; i < MainManifDetails.getUsesPermission().size(); i++) {
                        if (MainManifDetails.getUsesPermission().get(i).getCodeLine().contains(PERMISSION_NAME)) {
                            isPermissionAdded = true;
                            break;
                        } else {
                            isPermissionAdded = false;
                        }
                    }
                    if (!isPermissionAdded) {

                        lineEditor.addNewLine(MainManifDetails.getFile(), MainManifDetails.getUsesPermission().get(0).getLineNumber(), PERMISISON);
                        lineEditor.addNewLine(MainManifDetails.getFile(), MainManifDetails.getUsesPermission().get(0).getLineNumber(), "<!-- Refer this link to know more " + REFER_LINK + "   -->");
                        lineEditor.addNewLine(MainManifDetails.getFile(), MainManifDetails.getUsesPermission().get(0).getLineNumber(), "<!--        Code Generated by AndroMigrate           -->");
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    }
                }
            }

            if(provider){
                if(MANIF_APP_TYPE.equals("PROVIDE")){
                    int Applicationline = MainManifDetails.getAppLineDetails().get("appLine");

                    boolean isProvideAdded = true;
                    for (int i = 0; i < MainManifDetails.getCodeDetails().size(); i++) {
                        if (MainManifDetails.getCodeDetails().get(i).getCodeLine().contains(MANIF_APP)) {
                            isProvideAdded = true;
                            break;
                        } else {
                            isProvideAdded = false;
                        }
                    }
                    if(!isProvideAdded){
                        lineEditor.addNewLine(MainManifDetails.getFile(), Applicationline, "    "+MANIF_APP);
                        lineEditor.addNewLine(MainManifDetails.getFile(), Applicationline, "    <!-- Refer this link to know more " + REFER_LINK + "   -->");
                        lineEditor.addNewLine(MainManifDetails.getFile(), Applicationline, "    <!--        Code Generated by AndroMigrate           -->");
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    }
                }
            }

            if(lib){
                if(MANIF_APP_TYPE.equals("LIB")){
                    int Applicationline = MainManifDetails.getAppLineDetails().get("appLine");

                    boolean isLibAdded = true;
                    for (int i = 0; i < MainManifDetails.getCodeDetails().size(); i++) {
                        if (MainManifDetails.getCodeDetails().get(i).getCodeLine().contains(MANIF_APP)) {
                            isLibAdded = true;
                            break;
                        } else {
                            isLibAdded = false;
                        }
                    }
                    if(!isLibAdded){
                        lineEditor.addNewLine(MainManifDetails.getFile(), Applicationline, "    "+MANIF_APP);
                        lineEditor.addNewLine(MainManifDetails.getFile(), Applicationline, "    <!-- Refer this link to know more " + REFER_LINK + "   -->");
                        lineEditor.addNewLine(MainManifDetails.getFile(), Applicationline, "    <!--        Code Generated by AndroMigrate           -->");
                        MainManifDetails = codeGenerator.readManifestFile(manifestDetails.getFile());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
