package androMigrate.SystemOperate;
import androMigrate.FindAndReplace.LineEditor;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static androMigrate.SystemOperate.MainBoard.baseReportFile;


public class GenerateReport {
    private File report;
    private  String changeLine;
    private int Linenum;
    private String Action;

    public void AddToReportFile(File victimFile,String changedLine,int changeLineNum, String Action) {


        try {
//            File yourFile = new File(baseReportFile);
//            yourFile.createNewFile(); // if file already exists will do nothing
//           // FileOutputStream oFile = new FileOutputStream(yourFile, false);

            BufferedWriter output = null;
            try {
                File file = new File(baseReportFile);
                file.createNewFile();
                output = new BufferedWriter(new FileWriter(file,true));
                output.write("\n File :"+victimFile.getName()+",     changed Line :"+changedLine+"  Line number : "+changeLineNum+"  Action :"+Action);
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( output != null ) {
                    output.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class OSDetector
    {
        private static boolean isWindows = false;
        private static boolean isLinux = false;
        private static boolean isMac = false;

        static
        {
            String os = System.getProperty("os.name").toLowerCase();
            isWindows = os.contains("win");
            isLinux = os.contains("nux") || os.contains("nix");
            isMac = os.contains("mac");
        }

        public static boolean isWindows() { return isWindows; }
        public static boolean isLinux() { return isLinux; }
        public static boolean isMac() { return isMac; };

    }
    public  boolean open(File file)
    {
        try
        {
            if (OSDetector.isWindows())
            {
                Runtime.getRuntime().exec(new String[]
                        {"rundll32", "url.dll,FileProtocolHandler",
                                file.getAbsolutePath()});
                return true;
            } else if (OSDetector.isLinux() || OSDetector.isMac())
            {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
                        file.getAbsolutePath()});
                return true;
            } else
            {
                // Unknown OS, try with desktop
                if (Desktop.isDesktopSupported())
                {
                    Desktop.getDesktop().open(file);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
            return false;
        }
    }
}
