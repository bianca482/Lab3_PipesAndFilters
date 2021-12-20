package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PushModelSink implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private final GraphicsContext context;
    private final RenderingMode renderingMode;

    public PushModelSink(RenderingMode renderingMode, GraphicsContext context) {
        this.context = context;
        this.renderingMode = renderingMode;
    }

    @Override
    public void write(Pair<Face, Color> faceColorPair) {
        Vec2 v1Trans = faceColorPair.fst().getV1().toScreen();
        Vec2 v2Trans = faceColorPair.fst().getV2().toScreen();
        Vec2 v3Trans = faceColorPair.fst().getV3().toScreen();

        if (renderingMode.equals(RenderingMode.POINT)) {
            context.setFill(faceColorPair.snd());

            context.fillOval(v1Trans.getX(), v1Trans.getY(), 1, 1);
            context.fillOval(v2Trans.getX(), v2Trans.getY(), 1, 1);
            context.fillOval(v3Trans.getX(), v3Trans.getY(), 1, 1);
        } else if (renderingMode.equals(RenderingMode.WIREFRAME)) {
            context.setStroke(faceColorPair.snd());

            context.strokePolygon(new double[]{v1Trans.getX(), v2Trans.getX(), v3Trans.getX()}, new double[]{v1Trans.getY(), v2Trans.getY(), v3Trans.getY()}, 3);
        } else if (renderingMode.equals(RenderingMode.FILLED)) {
            context.setFill(faceColorPair.snd());

            context.fillPolygon(new double[]{v1Trans.getX(), v2Trans.getX(), v3Trans.getX()}, new double[]{v1Trans.getY(), v2Trans.getY(), v3Trans.getY()}, 3);

            //Damit die Linien auch eingefärbt werden
            context.setStroke(faceColorPair.snd());
            context.strokePolygon(new double[]{v1Trans.getX(), v2Trans.getX(), v3Trans.getX()}, new double[]{v1Trans.getY(), v2Trans.getY(), v3Trans.getY()}, 3);
        }
    }

    @Override
    public void setSuccessor(PushPipe<Pair<Face, Color>> successor) {

    }
}
