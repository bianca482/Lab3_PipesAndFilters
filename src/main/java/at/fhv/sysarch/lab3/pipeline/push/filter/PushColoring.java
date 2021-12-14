package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import javafx.scene.paint.Color;

public class PushColoring implements PushFilter<Face> {

    private final PipelineData pd;
    private PushPipe<Pair<Face, Color>> successor;

    public PushColoring(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face input) {
        Color modelColor = pd.getModelColor();

        float r, g, b;
        r = (float) modelColor.getRed();
        g = (float) modelColor.getGreen();
        b = (float) modelColor.getBlue();

        Color color = new Color(r, g, b, 1.0);

        Pair<Face, Color> faceColorPair = new Pair<>(input, color);

        successor.write(faceColorPair);
    }

    public void setSuccessor(PushPipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
