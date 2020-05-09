package androMigrate;

import androMigrate.SystemOperate.GenerateReport;
import androMigrate.SystemOperate.MainBoard;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static androMigrate.SystemOperate.MainBoard.baseReportFile;

public class ReportAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
//        e.getProject().save();
//        System.out.println(e.getProject().getBasePath());
//        MainBoard mainBoard = new MainBoard();
//        Boolean isInitialized=  mainBoard.InitialteMigration(e.getProject().getBasePath());
//        String getfirstletter = mainBoard.getString();
        baseReportFile = e.getProject().getBasePath()+"/MigrationReport.txt";
        GenerateReport report = new GenerateReport();
        File file = new File(baseReportFile);
        boolean isopen = report.open(file);

        if(!isopen){
            Messages.showMessageDialog(e.getProject(),"File not found!","AndroMigration", Messages.getInformationIcon());
        }
    }
}
