package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class Coloring implements Filter<Face> {

    private final PipelineData pd;
    private final Pipe<Face> successor;

    public Coloring(PipelineData pd, Pipe<Face> successor) {
        this.pd = pd;
        this.successor = successor;
    }

    @Override
    public void write(Face input) {
        Color modelColor = pd.getModelColor();

        float r, g, b;
        r = (float) modelColor.getRed();
        g = (float) modelColor.getGreen();
        b = (float) modelColor.getBlue();

        Vec4 v1Color = new Vec4(input.getV1().getX(), input.getV1().getY(), input.getV1().getZ(), r);
        Vec4 v2Color = new Vec4(input.getV2().getX(), input.getV2().getY(), input.getV2().getZ(), g);
        Vec4 v3Color = new Vec4(input.getV3().getX(), input.getV3().getY(), input.getV3().getZ(), b);

        successor.write(new Face(v1Color, v2Color, v3Color, input));
    }
}
