package androMigrate.FindAndReplace;


import androMigrate.FileReader.JavaReader.JavaLineDetails;

public class CheckLine {

    public void checkLine(JavaLineDetails javaLineDetails){


            LineEditor newLine = new LineEditor();

            try {
               // newLine.removeLine(javaLineDetails.getFileP(),javaLineDetails.getLineNumber());
             //   newLine.insertStringInFile(javaLineDetails.getFileP(),javaLineDetails.getLineNumber(),"package com.example.nazoorahamed.myapplication;");
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
