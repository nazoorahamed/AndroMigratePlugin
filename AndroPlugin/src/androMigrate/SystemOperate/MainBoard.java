package androMigrate.SystemOperate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class MainBoard {
    public static List<File> JFiles;
    public static List<File> GradleFile;
    public static List<File> ManifFile;
    public static String baseReportFile;

    public boolean InitialteMigration(String fname){
        JFiles = new ArrayList<>();
        GradleFile = new ArrayList<>();
        ManifFile = new ArrayList<>();

        MigrationContainer mc = new MigrationContainer();
        boolean isFilesareavailable =  mc.getAllFilePaths(fname);
        System.out.println("END");
        // insert three new rows
//        readfromFile("HttpURLConnection");
//        readAllmethods();

        try {
            baseReportFile = fname+"/MigrationReport.txt";
            File creatFile = new File(baseReportFile);
            creatFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(creatFile, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(isFilesareavailable){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        String fname = "/Users/nazoorahamed/Desktop/MyApplication";
       // String fname    = "/Users/nazoorahamed/Desktop/2nd Year/2nd Semester/Mobile Development/AppointmentApp/Coursework2";
        JFiles = new ArrayList<>();
        GradleFile = new ArrayList<>();
        ManifFile = new ArrayList<>();

        MigrationContainer mc = new MigrationContainer();
        mc.getAllFilePaths(fname);
//        createNewTable();
//        MainBoard app = new MainBoard();


    }
    private Connection connect(){
        Connection connection=null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");

        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    private Connection connectNext(){
        Connection connection=null;
        String path=this.getClass().getResource("data2.sqlite").getPath();

        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+path);

            if (connection!= null){
                System.out.println("not null");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }


    public void insert(String name, double capacity) {
        String sql = "INSERT INTO warehouseses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewTable() {
        // SQLite connection string
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:test.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouseses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");\n";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getString(){
        String out=null;
        String sql = "SELECT id, name, capacity FROM warehouseses";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
                out = rs.getString("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    public List<String> readAllmethods(){
        List<String> allmethods = new ArrayList<>();
        String sql = "SELECT METHOD FROM AndroMigrateDB ";
        try (Connection conn = this.connectNext();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                // let's see if we can read one of the tables
                while (rs.next()) {
                    allmethods.add(rs.getString(1));
                    System.out.println(rs.getString(1));
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allmethods;

    }

    public List<String> readfromFile(String method) {

        List<String> methodDetails = new ArrayList<>();

        // let's see if we can read one of the tables
        String sql = "SELECT * FROM AndroMigrateDB where METHOD="+"'"+method+"'";

        try (Connection conn = this.connectNext();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" +
                        rs.getString(2) + "\t" +
                        rs.getString(3) + "\t" +
                        rs.getString(4) + "\t" +
                        rs.getString(5) + "\t" +
                        rs.getString(6) + "\t" +
                        rs.getString(7) + "\t" +
                        rs.getString(8) + "\t" +
                        rs.getString(9) + "\t" +
                        rs.getInt(10) + "\t" +
                        rs.getString(11) + "\t" +
                        rs.getString(12) + "\t" +
                        rs.getString(13) + "\t" +
                        rs.getString(14) + "\t" +
                        rs.getString(15) + "\t" +
                        rs.getString(16) + "\t" +
                        rs.getString(17) + "\t" +
                        rs.getString(18) + "\t" +
                        rs.getString(19) + "\t" +
                        rs.getString(20));
                methodDetails.add(String.valueOf(rs.getInt(1)));
                methodDetails.add(String.valueOf(rs.getString(2)));
                methodDetails.add(String.valueOf(rs.getString(3)));
                methodDetails.add(String.valueOf(rs.getString(4)));
                methodDetails.add(String.valueOf(rs.getString(5)));
                methodDetails.add(String.valueOf(rs.getString(6)));
                methodDetails.add(String.valueOf(rs.getString(7)));
                methodDetails.add(String.valueOf(rs.getString(8)));
                methodDetails.add(String.valueOf(rs.getString(9)));
                methodDetails.add(String.valueOf(rs.getInt(10)));
                methodDetails.add(String.valueOf(rs.getString(11)));
                methodDetails.add(String.valueOf(rs.getString(12)));
                methodDetails.add(String.valueOf(rs.getString(13)));
                methodDetails.add(String.valueOf(rs.getString(14)));
                methodDetails.add(String.valueOf(rs.getString(15)));
                methodDetails.add(String.valueOf(rs.getString(16)));
                methodDetails.add(String.valueOf(rs.getString(17)));
                methodDetails.add(String.valueOf(rs.getString(18)));
                methodDetails.add(String.valueOf(rs.getString(19)));
                methodDetails.add(String.valueOf(rs.getString(20)));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return methodDetails;
    }



}