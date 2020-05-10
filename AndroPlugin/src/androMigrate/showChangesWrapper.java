package androMigrate;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

import static androMigrate.SystemOperate.MappingAPI.showOnlyChanges;

public class showChangesWrapper extends DialogWrapper {
    List<String> tablestrings;
    JTable jTable;
    JBTable bttable;
    JScrollPane scrollPane;
    JList list;


    protected showChangesWrapper(List<String> strings) {
        super(true);
        this.tablestrings = strings;
        init();
        setTitle("Migration Changes");

    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {

        JPanel dialogPanel = new JPanel(new BorderLayout());
        DefaultTableModel mode = new DefaultTableModel();
        mode.addColumn("line");
        bttable = new JBTable(mode);
        Vector col = new Vector();
        col.add("super");
        col.add("nice");
        Vector raw = new Vector();
        raw.add("raw1");
        raw.add("raw2");


        String addall="";
        for (int i=0;i<tablestrings.size();i++){
            addall = addall+"\n"+tablestrings.get(i);
        }

        JLabel label = new JLabel(addall);
        label.setPreferredSize(new Dimension(100, 100));

        String week[]= { "Monday","Tuesday","Wednesday",
                "Thursday","Friday","Saturday","Sunday"};

        //create list
        list= new JList(tablestrings.toArray());

        DefaultTableModel model = (DefaultTableModel) bttable.getModel();
        model.addRow(new Object[]{"Column 1"});
        dialogPanel.add(list,BorderLayout.CENTER);

        return dialogPanel;
    }
}
