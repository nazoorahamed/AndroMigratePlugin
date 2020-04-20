package androMigrate.FileReader.JavaReader;

import java.io.File;
import java.util.TreeMap;

public class JavaLineDetails {
    private File fileP;
    private int lineNumber;
    private String codeLine;
    private TreeMap<String,Integer> lastLineDetail;

    public JavaLineDetails(File file, int lineNumber, String codeLine,TreeMap<String,Integer> lastLineDetail) {
        this.fileP = file;
        this.lineNumber = lineNumber;
        this.codeLine = codeLine;
        this.lastLineDetail = lastLineDetail;
    }

    public TreeMap<String, Integer> getLastLineDetail() {
        return lastLineDetail;
    }

    public void setLastLineDetail(TreeMap<String, Integer> lastLineDetail) {
        this.lastLineDetail = lastLineDetail;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getCodeLine() {
        return codeLine;
    }

    public void setCodeLine(String codeLine) {
        this.codeLine = codeLine;
    }

    public File getFileP() {
        return fileP;
    }

    public void setFileP(File fileP) {
        this.fileP = fileP;
    }
}
