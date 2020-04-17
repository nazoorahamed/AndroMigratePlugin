package androMigrate.FileReader.JavaReader;

import java.io.File;

public class JavaLineDetails {
    private File fileP;
    private int lineNumber;
    private String codeLine;

    public JavaLineDetails(File file, int lineNumber, String codeLine) {
        this.fileP = file;
        this.lineNumber = lineNumber;
        this.codeLine = codeLine;
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
