package aant.gui;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;


public class JListSolutionModel extends AbstractListModel {

    ArrayList<SourceLine> lines;

    public JListSolutionModel(ArrayList<SourceLine> lines) {
        this.lines = lines;
    }

    public int getSize() {
        return lines.size();
    }

    public void setLines(ArrayList<SourceLine> lines) {
        this.lines = lines;

        fireContentsChanged(this, 0, getSize());
    }

    public Object getElementAt(int index) {
        return indent(lines.get(index).indent + 1) + "" + lines.get(index).text;
    }

    public void addListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public String indent(int pocet) {
        String res = "";
        for (int i = 0; i < pocet; i++) {
            res += "  ";
        }
        return res;
    }

    public ArrayList<SourceLine> getLines() {
        return lines;
    }
}
