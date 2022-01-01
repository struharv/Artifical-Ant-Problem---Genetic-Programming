package aant.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

public class WaitDialog extends JDialog {

    MainForm mainForm;
    SearchThread searchThread = null;
    WaitPanel waitPanel;

    public WaitDialog(MainForm mainForm) {
        this.mainForm = mainForm;

        setSize(200, 200);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        this.setLayout(new BorderLayout());
        waitPanel = new WaitPanel();
        this.add(waitPanel, BorderLayout.CENTER);

        JButton btnStorno = new JButton("Cancel");
        btnStorno.addActionListener(e -> storno());

        this.add(btnStorno, BorderLayout.SOUTH);

        computeLocation();
        searchSolution();
        setVisible(true);
    }

    public void computeLocation() {
        setLocation(mainForm.getX() + mainForm.getWidth() / 2 - this.getWidth() / 2,
                mainForm.getY() + mainForm.getHeight() / 2 - this.getHeight() / 2);
    }

    public void storno() {
        if (searchThread != null) {
            searchThread.stop();
        }
        this.setVisible(false);
        this.dispose();
    }

    public void done() {
        this.setVisible(false);
        this.dispose();
    }

    public void searchSolution() {
        searchThread = new SearchThread(this, 500);
        Thread t = new Thread(searchThread);
        t.start();
    }

    public MainForm getMainForm() {
        return mainForm;
    }

    public WaitPanel getWaitPanel() {
        return waitPanel;
    }


}
