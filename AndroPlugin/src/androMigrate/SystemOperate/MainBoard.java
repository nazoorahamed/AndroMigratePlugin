package androMigrate.SystemOperate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainBoard {
    public static List<File> JFiles;
    public static List<File> GradleFile;
    public static List<File> ManifFile;

    public void InitialteMigration(String fname){
        JFiles = new ArrayList<>();
        GradleFile = new ArrayList<>();
        ManifFile = new ArrayList<>();

        MigrationContainer mc = new MigrationContainer();
        mc.getAllFilePaths(fname);

    }

    public static void main(String[] args) throws Exception {

        String fname = "/Users/nazoorahamed/Desktop/MyApplication";
       // String fname    = "/Users/nazoorahamed/Desktop/2nd Year/2nd Semester/Mobile Development/AppointmentApp/Coursework2";
        JFiles = new ArrayList<>();
        GradleFile = new ArrayList<>();
        ManifFile = new ArrayList<>();

        MigrationContainer mc = new MigrationContainer();
        mc.getAllFilePaths(fname);
    }

}