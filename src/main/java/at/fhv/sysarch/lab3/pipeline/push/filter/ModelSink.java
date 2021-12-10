package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ModelSink implements Filter<Pair<Face, Color>> {

    private final GraphicsContext context;
    private PipelineData pd;

    public ModelSink(PipelineData pd, GraphicsContext context) {
        this.context = context;
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> faceColorPair) {
        Vec4 v1Trans = faceColorPair.fst().getV1();
        Vec4 v2Trans = faceColorPair.fst().getV2();
        Vec4 v3Trans = faceColorPair.fst().getV3();

        context.setStroke(faceColorPair.snd());

        context.strokeLine(v1Trans.getX(), v1Trans.getY(), v2Trans.getX(), v2Trans.getY());
        context.strokeLine(v1Trans.getX(), v1Trans.getY() , v3Trans.getX(), v3Trans.getY());
        context.strokeLine(v2Trans.getX(), v2Trans.getY() , v3Trans.getX(), v3Trans.getY());
   }
}
