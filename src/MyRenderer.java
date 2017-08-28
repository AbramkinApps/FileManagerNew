import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

public class MyRenderer extends DefaultTreeCellRenderer implements FileManager.DownloadIcon {



    private Icon file = new ImageIcon("images/archives.png");
    private Icon openFolder = new ImageIcon("images/openfolder.png");
    private Icon closeFolder = new ImageIcon("images/closefolder.png");
    private Icon hour = new ImageIcon("images/hour.png");

    File file1;

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value, boolean selected, boolean expanded,
                                                  boolean isLeaf, int row, boolean focused) {
        Component c = super.getTreeCellRendererComponent(tree, value,
                selected, expanded, isLeaf, row, focused);

        FileManager fm = new FileManager();
        fm.registerCallBack(this);

        File f1 = (File) value;

        if (isLeaf)
            setIcon(file);
        else if (!expanded)
            setIcon(closeFolder);
        else {
            setIcon(openFolder);}

        if (file1 ==f1) {setIcon(hour);}
        return c;
        }

    @Override
    public void changeIcon(File f) {


        file1 =f;

    }
}