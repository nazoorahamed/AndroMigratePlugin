package androMigrate;

import androMigrate.SystemOperate.MainBoard;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class MigrateAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Messages.showMessageDialog(e.getProject(),e.getProject().getBasePath(),"Migration", Messages.getInformationIcon());
        System.out.println(e.getProject().getBasePath());
        MainBoard mainBoard = new MainBoard();
        mainBoard.InitialteMigration(e.getProject().getBasePath());
    }
}
