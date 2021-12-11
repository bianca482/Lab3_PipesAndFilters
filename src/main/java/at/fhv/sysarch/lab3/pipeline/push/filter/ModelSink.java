package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec2;
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

        Vec2 v1Screen = v1Trans.toScreen();
        Vec2 v2Screen = v2Trans.toScreen();
        Vec2 v3Screen = v3Trans.toScreen();

        RenderingMode renderingMode = pd.getRenderingMode();

        if (renderingMode.equals(RenderingMode.POINT)) {
            context.setStroke(faceColorPair.snd());

            context.strokeLine(v1Trans.getX(), v1Trans.getY(), v2Trans.getX(), v2Trans.getY());
            context.strokeLine(v1Trans.getX(), v1Trans.getY() , v3Trans.getX(), v3Trans.getY());
            context.strokeLine(v2Trans.getX(), v2Trans.getY() , v3Trans.getX(), v3Trans.getY());
        } else if (renderingMode.equals(RenderingMode.WIREFRAME)) {
            context.setStroke(faceColorPair.snd());

            context.strokePolygon(new double[]{v1Screen.getX(), v2Screen.getX(), v3Screen.getX()}, new double[]{v1Screen.getY(), v2Screen.getY(), v3Screen.getY()}, 3);
        } else if (renderingMode.equals(RenderingMode.FILLED)) {
            context.setFill(faceColorPair.snd());

            context.fillPolygon(new double[]{v1Screen.getX(), v2Screen.getX(), v3Screen.getX()}, new double[]{v1Screen.getY(), v2Screen.getY(), v3Screen.getY()}, 3);
        }
   }
}
