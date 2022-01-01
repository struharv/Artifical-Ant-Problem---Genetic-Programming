package aant.gui;

import aant.engine.Evolution;
import aant.engine.TreeUtils;


public class SearchThread implements Runnable{
    boolean terminated;
    int generations;
    int cntGenerace;
    WaitDialog waitDialog;

    public SearchThread(WaitDialog waitDialog, int generations) {
        this.terminated = false;
        this.generations = generations;
        this.waitDialog = waitDialog;
    }

    public void run() {
        cntGenerace = 0;
        Evolution evolution = new Evolution(waitDialog.getMainForm().workingMap);
        evolution.setSteps(MainForm.STEPS);
        evolution.createPopulation();

        while (!terminated && cntGenerace < generations){
            cntGenerace++;

            waitDialog.getWaitPanel().setTextProgress("generation: "+cntGenerace);
            waitDialog.getWaitPanel().repaint();
            evolution.nextGeneration();
        }

        if (cntGenerace >= generations) {
            waitDialog.getMainForm().setCesta(evolution.getBest().node);
            TreeUtils treeUtils = new TreeUtils();
            waitDialog.done();
        } else {
            waitDialog.getMainForm().setCesta(null);
        }
    }

    void stop() {
        terminated = true;
    }

}
