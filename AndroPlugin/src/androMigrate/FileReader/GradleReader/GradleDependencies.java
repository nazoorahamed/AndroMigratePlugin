package androMigrate.FileReader.GradleReader;

import java.io.File;

public class GradleDependencies {
    private File filep;
    private int lineNumber;
    private String codeLine;

    public GradleDependencies(File filep, int lineNumber, String codeLine) {
        this.filep = filep;
        this.lineNumber = lineNumber;
        this.codeLine = codeLine;
    }

    public File getFilep() {
        return filep;
    }

    public void setFilep(File filep) {
        this.filep = filep;
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
}
