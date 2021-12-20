package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import javafx.scene.paint.Color;

public class PushColoring implements PushFilter<Face, Pair<Face, Color>> {

    private final Color modelColor;
    private PushPipe<Pair<Face, Color>> successor;

    public PushColoring(Color modelColor) {
        this.modelColor = modelColor;
    }

    @Override
    public void write(Face input) {
        float r, g, b;
        r = (float) modelColor.getRed();
        g = (float) modelColor.getGreen();
        b = (float) modelColor.getBlue();

        Color color = new Color(r, g, b, 1.0);

        Pair<Face, Color> faceColorPair = new Pair<>(input, color);

        successor.write(faceColorPair);
    }

    @Override
    public void setSuccessor(PushPipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
