package aant.gui;

public class SourceLine {
    String id;
    int indent;
    String text;

    public SourceLine(String id, int indent, String text) {
        this.id = id;
        this.indent = indent;
        this.text = text;
    }

}
