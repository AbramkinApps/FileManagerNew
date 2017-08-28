import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileManager {

    static JTree jTree;
    static File selectedItem;

    interface DownloadIcon {
        void changeIcon(File f);
    }

    static DownloadIcon downloadIcon;

    public void registerCallBack(DownloadIcon downloadIcon){
        this.downloadIcon = downloadIcon;
    }

    public static void main(String[] a) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }


        JFrame jFrame = new JFrame("File System Tree");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        selectedItem = NewTree.getMyComp()[0];
        jTree = NewTree.createNewTree(null);
        JScrollPane jScrollPane = new JScrollPane(jTree);
        JComboBox jComboBox = new JComboBox(NewTree.getMyComp());

        TreeWillExpandListener treeExpandListener = new TreeWillExpandListener() {

            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {

                File f = (File) event.getPath().getLastPathComponent();
                downloadIcon.changeIcon(f);

                SwingWorker<Boolean, Void> swingWorker = new SwingWorker() {
                    @Override
                    protected Boolean doInBackground() throws Exception {

                        Thread.sleep(2000);

                        return true;
                    }

                };
                swingWorker.execute();
                try {
                    swingWorker.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void treeWillCollapse(TreeExpansionEvent event) {

                downloadIcon.changeIcon(null);

            }
        };

        jTree.addTreeWillExpandListener(treeExpandListener);


        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                jScrollPane.getViewport().remove(0);
                selectedItem = (File)box.getSelectedItem();
                jTree = NewTree.createNewTree((File)box.getSelectedItem());
                jTree.addTreeWillExpandListener(treeExpandListener);

                jScrollPane.getViewport().add(jTree);
            }
        });

        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("Choose:");
        JButton jButton = new JButton("New folder",new ImageIcon("images/add.png"));
        jButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                File selectedFile;
                if (jTree.getLastSelectedPathComponent()!=null) {
                    selectedFile = ((File) jTree.getLastSelectedPathComponent()).getAbsoluteFile();}
                else {selectedFile=selectedItem;}

                if (selectedFile.isDirectory()) {
                    String s = String.valueOf(System.currentTimeMillis());
                    File folder = new File(selectedFile+File.separator+"New folder ("+s.substring(s.length()-2)+")");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    jTree.updateUI();
                }
                else {
                    String s = String.valueOf(System.currentTimeMillis());
                    File parentFolder =  ((File) jTree.getLastSelectedPathComponent()).getParentFile();
                    File folder = new File(parentFolder+File.separator+"New folder ("+s.substring(s.length()-2)+")");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    jTree.updateUI();

                };
            }
        });
        jPanel.add(jLabel);
        jPanel.add(jComboBox);
        jPanel.add(jButton);
        jFrame.add(jPanel);
        jFrame.add(jScrollPane);
        jFrame.setSize(500, 800);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.pack();
    }

}