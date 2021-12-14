package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class FlatShading implements PullFilter<Pair<Face, Color>> {
    @Override
    public Pair<Face, Color> read() {
        return null;
    }
}
