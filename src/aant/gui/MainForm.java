package aant.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aant.engine.EAction;
import aant.engine.AntInterpreter;
import aant.engine.Map;
import aant.engine.MapLoader;
import aant.engine.TreeUtils;
import aant.engine.Node;


public class MainForm extends JFrame {

    public static int STEPS = 400;
    MainPanel mainPanel;
    Map userMap;
    Map workingMap;
    boolean interpretted;
    JSlider sliderSteps;
    JListSolution listSolution;
    int step = 0;
    Node best;

    public MainForm() {
        super("Artificial Ant");
        interpretted = false;
        this.setSize(600, 450);

        setResizable(false);
        this.setLayout(new BorderLayout());
        userMap = new Map();
        //reloadMap();
        loadMap("/santafe.map");
        mainPanel = new MainPanel(userMap, this);
        best = null;

        this.add(mainPanel, BorderLayout.WEST);
        JButton btnCesta = new JButton("Search!");
        btnCesta.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                hledejReseni();
            }
        });

        this.add(btnCesta, BorderLayout.NORTH);

        JButton btnStep = new JButton("STEP");
        btnStep.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        });


        sliderSteps = new JSlider(0, STEPS, 0);
        sliderSteps.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                step(sliderSteps.getValue());
            }
        });

        this.add(sliderSteps, BorderLayout.SOUTH);
        listSolution = new JListSolution();
        this.add(new JScrollPane(listSolution), BorderLayout.CENTER);

        reset();
        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);
    }

    public void step(int kroku) {
        if (best != null) {
            interpretted = true;
            Node koren1 = new Node(EAction.IF_FOOD_AHEAD, 2, "0");
            koren1.addOffspring(new Node(EAction.MOVE, 0, "1"));
            Node dalsi = new Node(EAction.PROG2, 2, "3");
            dalsi.addOffspring(new Node(EAction.RIGHT, 0, "2"));
            dalsi.addOffspring(new Node(EAction.MOVE, 0, "4"));
            koren1.addOffspring(dalsi);

            AntInterpreter antInterpreter = new AntInterpreter(new Map(userMap));
            String id = antInterpreter.run(best, kroku);
            listSolution.highlight(id);
            mainPanel.setData(antInterpreter.getData());
            mainPanel.repaint();
        }
    }

    public void hledejReseni() {
        reset();
        WaitDialog wf = new WaitDialog(this);
    }

    public void reset() {
        reloadMap();

        best = null;
        step = 0;
        reenableControl();
    }

    public void setCesta(Node cesta) {
        best = cesta;
        reenableControl();

        if (best != null) {
            TreeUtils treeUtils = new TreeUtils();
            listSolution.setData(treeUtils.getSource(best));

        }
    }

    public void reloadMap() {
        workingMap = new Map(userMap);
    }

    public void loadMap(String filename) {
        MapLoader map = new MapLoader(userMap);
        map.load(filename);
    }

    private void reenableControl() {
        sliderSteps.setValue(0);
        sliderSteps.setEnabled(best != null);
    }

    public void mapReset() {
        mainPanel.setData(userMap);
        interpretted = false;
        reset();
    }

    public boolean isInterpretted() {
        return interpretted;
    }
}
