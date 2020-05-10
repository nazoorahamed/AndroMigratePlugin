package androMigrate;

import androMigrate.SystemOperate.MainBoard;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import static androMigrate.SystemOperate.MappingAPI.ShowOnly;
import static androMigrate.SystemOperate.MappingAPI.showOnlyChanges;

public class CodeChangesAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowOnly = true;
        e.getProject().save();
        System.out.println(e.getProject().getBasePath());
        MainBoard mainBoard = new MainBoard();
        Boolean isInitialized=  mainBoard.InitialteMigration(e.getProject().getBasePath());
        String getfirstletter = mainBoard.getString();
        e.getProject().save();

        if(new showChangesWrapper(showOnlyChanges).showAndGet()){
            //
        }
//
    //    if(isInitialized){
//            if(!showOnlyChanges.isEmpty()){
//                Messages.showMessageDialog(e.getProject(),"Migration Not empty !"+getfirstletter,"API Migration", Messages.getInformationIcon());
//                System.out.println(showOnlyChanges.get(0));
//            }else {
//                Messages.showMessageDialog(e.getProject(),"Migration is empty !"+getfirstletter,"API Migration", Messages.getInformationIcon());
//            }
//
//        }else {
//            Messages.showMessageDialog(e.getProject(),"Migration Failed !"+getfirstletter,"API Migration", Messages.getInformationIcon());
//
//        }
    }
}
