package androMigrate.SystemOperate;

import androMigrate.FileReader.GradleReader.GradleDetails;
import androMigrate.FileReader.ManifestReader.ManifestDetails;

import java.io.File;
import java.util.List;

public class MappingAPI {
    int APILevel = 23;
    String method = "HttpURLConnection";
    String status = "Active";
    String NEW_METHOD_AVAILABLE = "Yes";
    String NEW_METHOD = "getNetworkDevice";
    int NEW_API_LEVEL = 29;
    String GRADLE_CHANGE = "TRUE";
    String GRADLE_CODE = "useLibrary 'org.apache.http.legacy'";
    String MANIF_CHANGE = "No";
    String PERMISISON = "    <uses-permission android:name=\"android.permission.VIBRATE\" />";
    String Comment = "Standard AlarmManager alarms (including setExact() and setWindow()) are deffered to the next maintainance window. Check https://developer.android.com/training/monitoring-device-state/doze-standby";


    public boolean MapAPI(GradleDetails gradleDetails, ManifestDetails manifestDetails, List<File> jFiles){
        return true;
    }
}
