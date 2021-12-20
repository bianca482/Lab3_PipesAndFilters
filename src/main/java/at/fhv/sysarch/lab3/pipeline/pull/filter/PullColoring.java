package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import javafx.scene.paint.Color;

public class PullColoring implements PullFilter<Pair<Face, Color>, Face> {

    private final Color modelColor;
    private PullPipe<Face> predecessor;

    public PullColoring(Color modelColor) {
        this.modelColor = modelColor;
    }

    @Override
    public Pair<Face, Color> read() {
        Face input = predecessor.read();

        if (input == null) {
            return null;
        }

        float r, g, b;
        r = (float) modelColor.getRed();
        g = (float) modelColor.getGreen();
        b = (float) modelColor.getBlue();

        Color color = new Color(r, g, b, 1.0);

        return new Pair<>(input, color);
    }

    @Override
    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}
