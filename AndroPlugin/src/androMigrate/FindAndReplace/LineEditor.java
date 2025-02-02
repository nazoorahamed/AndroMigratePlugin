package androMigrate.FindAndReplace;

import androMigrate.SystemOperate.GenerateReport;

import java.io.*;

public class LineEditor {
    GenerateReport report = new GenerateReport();
    public void addNewLine (File inFile, int lineno, String lineToBeInserted, boolean ismethod)
            throws Exception {
        // temp file
        File outFile = new File("/Users/nazoorahamed/Documents/University Files/4th Year/w1654449code/AndroPlugin/src/androMigrate/TemptFile/tempt.java");

        // input
        FileInputStream fis = new FileInputStream(inFile);
        BufferedReader in = new BufferedReader
                (new InputStreamReader(fis));


        // output
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);

        String thisLine = "";
        int i = 1;
        while ((thisLine = in.readLine()) != null) {
            if (i == lineno) out.println(lineToBeInserted);
            out.println(thisLine);
            i++;
        }
        if(ismethod){
            report.AddToReportFile(inFile,lineToBeInserted,lineno,"Line Added");
        }


        out.flush();
        out.close();
        in.close();
        inFile.delete();
        outFile.renameTo(inFile);
    }
    public  void replaceLine(File file,int lineno,String line, boolean ismethod){
        try {
            removeLine(file,lineno,ismethod);
            addNewLine(file, lineno, line,ismethod);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void removeLine(File inFile, int lineno, boolean ismethod) throws Exception{
        File outFile = new File("/Users/nazoorahamed/Documents/University Files/4th Year/w1654449code/AndroPlugin/src/androMigrate/TemptFile/tempt.java");

        // input
        FileInputStream fis = new FileInputStream(inFile);
        BufferedReader in = new BufferedReader
                (new InputStreamReader(fis));

        // output
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);


        String lineToRemove = "//          Thread.wait(1500);";
        String currentLine;
        int i = 1;
        while((currentLine = in.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(i == lineno) {
                i++;
            } else {
                out.write(currentLine + System.getProperty("line.separator"));
                i++;
            }
        }
        if (ismethod){
            report.AddToReportFile(inFile,currentLine,lineno,"Line Removed");
        }
        out.flush();
        out.close();
        in.close();

        inFile.delete();

        outFile.renameTo(inFile);

    }
}
