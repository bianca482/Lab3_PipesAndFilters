package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import javafx.scene.paint.Color;

public class Coloring implements PullFilter<Pair<Face, Color>> {

    private final PipelineData pd;
    private PullPipe<Face> predecessor;

    public Coloring(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {
        Color modelColor = pd.getModelColor();

        Face input = predecessor.read();
        if (input == null) {
            return null;
        }

        float r, g, b;
        r = (float) modelColor.getRed();
        g = (float) modelColor.getGreen();
        b = (float) modelColor.getBlue();

        Color color = new Color(r, g, b, 1.0);

        Pair<Face, Color> faceColorPair = new Pair<>(input, color);

        return faceColorPair;
    }

    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}
