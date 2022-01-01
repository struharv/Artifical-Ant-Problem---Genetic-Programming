package aant.gui;

import java.util.ArrayList;
import javax.swing.JList;

public class JListSolution extends JList {


    public void setData(ArrayList<SourceLine> lines) {
        this.setModel(new JListSolutionModel(lines));
    }

    public void highlight(String id) {
        JListSolutionModel model = (JListSolutionModel) this.getModel();
        for (int i = 0; i < model.getLines().size(); i++) {
            if (model.getLines().get(i).id.equals(id)) {
                this.setSelectedIndex(i);
                ensureIndexIsVisible(i);
                break;
            }
        }
    }
}
