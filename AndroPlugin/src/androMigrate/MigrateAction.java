package androMigrate;

import androMigrate.SystemOperate.MainBoard;
import com.intellij.history.LocalHistory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class MigrateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        e.getProject().save();
        System.out.println(e.getProject().getBasePath());
        MainBoard mainBoard = new MainBoard();
        Boolean isInitialized=  mainBoard.InitialteMigration(e.getProject().getBasePath());
        String getfirstletter = mainBoard.getString();
        e.getProject().save();

        if(isInitialized){
            Messages.showMessageDialog(e.getProject(),"Migration Completed !"+getfirstletter,"API Migration", Messages.getInformationIcon());
        }else {
            Messages.showMessageDialog(e.getProject(),"Migration Failed !"+getfirstletter,"API Migration", Messages.getInformationIcon());

        }
    }
}
