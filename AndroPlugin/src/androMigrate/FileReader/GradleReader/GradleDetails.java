package androMigrate.FileReader.GradleReader;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

public class GradleDetails {
    private File file;
    private String targetSdk;
    private TreeMap<String,Integer> targetsdkline;
    private String compileSdk;
    private String minSdk;
    private List<GradleDependencies> dependencies;
    private List<GradleLineDetails> codeDetails;

    public GradleDetails(File filep, String targetSdk, TreeMap<String,Integer> targetsdkline, String compileSdk, String minSdk, List<GradleDependencies> dependencies, List<GradleLineDetails> details) {
        this.file = filep;
        this.targetSdk = targetSdk;
        this.targetsdkline = targetsdkline;
        this.compileSdk = compileSdk;
        this.minSdk = minSdk;
        this.dependencies = dependencies;
        this.codeDetails = details;
    }

    public TreeMap<String, Integer> getTargetsdkline() {
        return targetsdkline;
    }

    public void setTargetsdkline(TreeMap<String, Integer> targetsdkline) {
        this.targetsdkline = targetsdkline;
    }

    public String getTargetSdk() {
        return targetSdk;
    }

    public void setTargetSdk(String targetSdk) {
        this.targetSdk = targetSdk;
    }

    public String getCompileSdk() {
        return compileSdk;
    }

    public void setCompileSdk(String compileSdk) {
        this.compileSdk = compileSdk;
    }

    public String getMinSdk() {
        return minSdk;
    }

    public void setMinSdk(String minSdk) {
        this.minSdk = minSdk;
    }

    public List<GradleDependencies> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<GradleDependencies> dependencies) {
        this.dependencies = dependencies;
    }

    public List<GradleLineDetails> getCodeDetails() {
        return codeDetails;
    }

    public void setCodeDetails(List<GradleLineDetails> codeDetails) {
        this.codeDetails = codeDetails;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
