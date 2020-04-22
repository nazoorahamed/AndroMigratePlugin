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
        for(int i=0;i<jFiles.size();i++){
            List<JavaLineDetails> javaLineDetails = codeGenerator.readJavaFile(jFiles.get(i));
            for (int k=0;k<javaLineDetails.size();k++){
               // System.out.println("sooora"+javaLineDetails.get(k).getLastLineDetail().values() + " :: " + javaLineDetails.get(k).getFileP().getName());
                findMappingInstance(MainGradDetails, MainManifDetails,jFiles,javaLineDetails.get(k).getCodeLine(),javaLineDetails.get(k).getLineNumber(),javaLineDetails.get(k));
            }
        }
        return true;
    }

    public void findMappingInstance(GradleDetails gradleDetails, ManifestDetails manifestDetails, List<File> jFiles,String line, int lineNumber,JavaLineDetails javaLineDetails) {

        if (line.trim().contains(METHOD)) {
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
                                changeNonMethodAddProviderAndPermission(gradleDetails,manifestDetails,GRADLE_CODE,MANIF_APP_TYPE,MANIF_APP,PERMISSION_NAME,PERMISISON);

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
                    if (MANIF_CHANGE.equals("add")) {
                        if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                            //change both permission and manifest body
                            if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                //add provider and permission
                                if (RUNTIME_REQ.equals("yes")) {
                                    //add permission with runtime and add
                                } else {
                                    //add permission without runtime add
                                }
                            } else if (MANIF_APP_TYPE.equals("LIB")) {
                                //Add lib and permission
                                if (RUNTIME_REQ.equals("yes")) {
                                    //add permission with runtime and add
                                } else {
                                    //add permission without runtime add
                                }
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

                } else if (GRADLE_CHANGE.equals("yes")) {
                    //only Gradle change

                }
            } else if (STATUS.equals("Active") && NEW_METHOD_AVAILABLE.equals("yes")) {
                //Active code and method available


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
                                    if (RUNTIME_REQ.equals("yes")) {
                                        //add permission with runtime and add LIB
                                    } else {
                                        //add permission without runtime
                                    }

                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //Add lib and permission

                                    if (RUNTIME_REQ.equals("yes")) {
                                        //add permission with runtime and add
                                    } else {
                                        //add permission without runtime add
                                    }
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
                        if (MANIF_CHANGE.equals("add")) {
                            if (!PERMISSION_NAME.equals("null") && !MANIF_APP_TYPE.equals("null")) {
                                //change both permission and manifest body
                                if (MANIF_APP_TYPE.equals("PROVIDE")) {
                                    //add provider and permission
                                    if (RUNTIME_REQ.equals("yes")) {
                                        //add permission with runtime and add
                                    } else {
                                        //add permission without runtime add
                                    }
                                } else if (MANIF_APP_TYPE.equals("LIB")) {
                                    //Add lib and permission
                                    if (RUNTIME_REQ.equals("yes")) {
                                        //add permission with runtime and add
                                    } else {
                                        //add permission without runtime add
                                    }
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

                    } else if (GRADLE_CHANGE.equals("yes")) {
                        //only Gradle change

                    }
                } else if (!METHOD_PARAMETER.equals("null")) {

                }
            }
        } else {
            System.out.println("not matched");
        }

    }

    public void changeNonMethodAddProviderAndPermission(GradleDetails gradleDetails,ManifestDetails manifestDetails, String gradleCode, String manifType, String manifApp, String PermissionName, String Permission) {
        MainManifDetails = manifestDetails;
        MainGradDetails = gradleDetails;
        try {

            if (!gradleCode.equals("null")) {
                Boolean isGradleLineAdded= true;
                for(int i=0;i<MainGradDetails.getCodeDetails().size();i++){
                    if(MainGradDetails.getCodeDetails().get(i).getCodeLine().contains(gradleCode)){
                        isGradleLineAdded = true;
                        break;
                    }else {
                        isGradleLineAdded = false;
                    }
                }

                if(!isGradleLineAdded){
                    int defaultConfigLine = MainGradDetails.getTargetsdkline().get("defaultConfig");
                    lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine, "        "+gradleCode);
                    lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine,"         Refer this link to know more " + REFER_LINK +"   */");
                    lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine,COMMENT);
                    lineEditor.addNewLine(MainGradDetails.getFile(), defaultConfigLine,"/*        Code Generated by AndroMigrate          ");
                    MainGradDetails = codeGenerator.readGradleFile(gradleDetails.getFile());
                }
            }
            if (!Permission.equals("null")) {
                Boolean isPermissionAdded = true;
                for (int i = 0; i < MainManifDetails.getUsesPermission().size(); i++) {
                    if (MainManifDetails.getUsesPermission().get(i).getCodeLine().contains(PermissionName)) {
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

            if(manifType.equals("PROVIDE")){
                int Applicationline = MainManifDetails.getAppLineDetails().get("appLine");

                Boolean isProvideAdded = true;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
