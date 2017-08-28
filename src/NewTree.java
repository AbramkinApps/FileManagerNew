
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class NewTree {


    public static JTree createNewTree(File file) {

        File root = file;

        if (root==null) {root=getMyComp()[0];}

        FileSystemModel fileSystemDataModel = new FileSystemModel(root);
        JTree tree = new JTree(fileSystemDataModel);
        tree.setRootVisible(false);
        MyRenderer renderer = new MyRenderer();
        tree.setCellRenderer(renderer);
        return tree;

    }

    public static File[] getMyComp() {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] roots = fileSystemView.getFiles(fileSystemView.getRoots()[0], true);
        File[] myComp = fileSystemView.getFiles(roots[0], true);
        return myComp;
    }

}