package androMigrate.FindAndReplace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ReplaceLine {

    public void replaceSelected(File fileP, String replaceWith, String type) {
        try {
            // input the file content to the StringBuffer "input"
            String fileName = "/Users/nazoorahamed/Desktop/4th Year/1st Semester/Concurrent Programing/Tutorials/Thread States/src/MyThread.java";

            BufferedReader file = new BufferedReader(new FileReader(fileP.getAbsolutePath()));
            System.out.println(" So  :"+ fileP.getAbsolutePath());
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');

            }
            file.close();
            String inputStr = inputBuffer.toString();

            System.out.println(inputStr); // display the original file for debugging

            // logic to replace lines in the string (could use regex here to be generic)
//            if (type.equals("0")) {
            inputStr = inputStr.replace("State of thread1 while", "wait");
//            } else if (type.equals("1")) {
            inputStr = inputStr.replace(replaceWith + "1", "public void run() {");
//            }

            // display the new file for debugging
            System.out.println("----------------------------------\n" + inputStr);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(fileName);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }
}
