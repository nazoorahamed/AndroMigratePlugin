package androMigrate.FileReader.GradleReader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class GradleFIleReader {

    public List<GradleLineDetails> readDetails(File file) {

        List<GradleLineDetails> grLineDetails = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int number=0;
                while ((line = br.readLine()) != null) {
                    number++;
                    GradleLineDetails jr = new GradleLineDetails(file,number,line);
                    grLineDetails.add(jr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Line Details : "+grLineDetails.size());
        return grLineDetails;
    }

    public GradleDetails getGradleDetails(File file){
        List<String> depends;
        List<GradleLineDetails> detailsLine = readDetails(file);
        List<GradleDependencies> dependenciesList = findDependencies(file,detailsLine);
        String targetSdk = null;
        String compileSdk = null;
        String minSdk = null;
        String buildTool = null;
        TreeMap<String,Integer> targerSdkLine = new TreeMap<String, Integer>();

        for (int i =0;i<detailsLine.size();i++){

            String line = detailsLine.get(i).getCodeLine();

            if (line.contains("targetSdkVersion")){
                System.out.println( detailsLine.get(i).getLineNumber()+" : True");
                targetSdk = findNumber(line);
                targerSdkLine.put("targetSdkVersion",detailsLine.get(i).getLineNumber());
            }else if (line.contains("compileSdkVersion")){
                System.out.println( detailsLine.get(i).getLineNumber()+" : True");
                compileSdk = findNumber(line);
                targerSdkLine.put("compileSdkVersion",detailsLine.get(i).getLineNumber());
            }else if (line.contains("minSdkVersion")){
                System.out.println( detailsLine.get(i).getLineNumber()+" : True");
                minSdk = findNumber(line);
                targerSdkLine.put("minSdkVersion",detailsLine.get(i).getLineNumber());
            }else if (line.contains("buildToolsVersion")){
                System.out.println( detailsLine.get(i).getLineNumber()+" : True");
                targerSdkLine.put("buildToolsVersion",detailsLine.get(i).getLineNumber());
            }else
            if(line.contains("defaultConfig")){
                System.out.println( detailsLine.get(i).getLineNumber()+1+" : True");
                targerSdkLine.put("defaultConfig",detailsLine.get(i).getLineNumber()+1);
            }
            if(line.contains("testInstrumentationRunner")){
                targerSdkLine.put("testInstrumentationRunner",detailsLine.get(i).getLineNumber());
            }
        }
        //depends = findDependencies(detailsLine);

        System.out.println(targetSdk+" : "+compileSdk+" : "+minSdk);


        return new GradleDetails(file,targetSdk,targerSdkLine,compileSdk,minSdk,dependenciesList,detailsLine);
    }

    public List<GradleDependencies> findDependencies(File file,List<GradleLineDetails> lines){
        int linenumber = 0;
        List<GradleDependencies> gl = new ArrayList<>();
        for (GradleLineDetails line : lines) {
            if (line.getCodeLine().contains("dependencies")) {
                linenumber = line.getLineNumber();
                System.out.println("ves : " + linenumber);
            }
        }
        if (linenumber != 0){
            while (!lines.get(linenumber).getCodeLine().contains("}")){
                linenumber++;
                GradleDependencies gd = new GradleDependencies(file,linenumber,lines.get(linenumber-1).getCodeLine());
                gl.add(gd);
            }
        }
        return gl;
    }

    public String findNumber(String string){
        string = string.replaceAll("[^0-9]+", " ");
        System.out.println(Arrays.asList(string.trim().split(" ")));
        String number = Arrays.asList(string.trim().split(" ")).get(0);
        return number;
    }
}