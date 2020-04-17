package androMigrate.FileReader.ManifestReader;

import java.io.File;

public class ManifestLineDetails {
    private File file;
    private int lineNumber;
    private String codeLine;

    public ManifestLineDetails(File filep, int lineNumber, String codeLine) {
        this.file = filep;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
