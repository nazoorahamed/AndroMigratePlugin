package androMigrate.FileReader.ManifestReader;

import java.io.File;
import java.util.List;

public class ManifestDetails {
    private File file;
    private ManifestLineDetails usesSdk;
    private List<ManifestLineDetails> usesPermission;
    private List<ManifestLineDetails> services;
    private List<ManifestLineDetails> codeDetails;

    public ManifestDetails(File pfile,ManifestLineDetails usesSdk, List<ManifestLineDetails> usesPermission, List<ManifestLineDetails> services, List<ManifestLineDetails> details) {
        this.file = pfile;
        this.usesSdk = usesSdk;
        this.usesPermission = usesPermission;
        this.services = services;
        this.codeDetails = details;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ManifestLineDetails getUsesSdk() {
        return usesSdk;
    }

    public void setUsesSdk(ManifestLineDetails usesSdk) {
        this.usesSdk = usesSdk;
    }

    public List<ManifestLineDetails> getUsesPermission() {
        return usesPermission;
    }

    public void setUsesPermission(List<ManifestLineDetails> usesPermission) {
        this.usesPermission = usesPermission;
    }

    public List<ManifestLineDetails> getServices() {
        return services;
    }

    public void setServices(List<ManifestLineDetails> services) {
        this.services = services;
    }

    public List<ManifestLineDetails> getCodeDetails() {
        return codeDetails;
    }

    public void setCodeDetails(List<ManifestLineDetails> codeDetails) {
        this.codeDetails = codeDetails;
    }
}
