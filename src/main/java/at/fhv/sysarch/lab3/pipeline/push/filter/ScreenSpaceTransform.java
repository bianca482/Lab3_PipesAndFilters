package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class ScreenSpaceTransform implements Filter<Face> {
    private PipelineData pd;
    private Pipe<Face> successor;

    public ScreenSpaceTransform(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face input) {
        Mat4 viewportTransform = pd.getViewportTransform();

        //Jede Komponente des Vektors durch W dividieren
        float dividedV1X = input.getV1().getX() / input.getV1().getW();
        float dividedV1Y = input.getV1().getY() / input.getV1().getW();
        float dividedV1Z = input.getV1().getZ() / input.getV1().getW();

        float dividedV2X = input.getV2().getX() / input.getV2().getW();
        float dividedV2Y = input.getV2().getY() / input.getV2().getW();
        float dividedV2Z = input.getV2().getZ() / input.getV2().getW();

        float dividedV3X = input.getV3().getX() / input.getV3().getW();
        float dividedV3Y = input.getV3().getY() / input.getV3().getW();
        float dividedV3Z = input.getV3().getZ() / input.getV3().getW();

        Vec4 newVector1 = new Vec4(dividedV1X, dividedV1Y, dividedV1Z, 1);
        Vec4 newVector2 = new Vec4(dividedV2X, dividedV2Y, dividedV2Z, 1);
        Vec4 newVector3 = new Vec4(dividedV3X, dividedV3Y, dividedV3Z, 1);

        //Die neuen Vektoren mit der Viewport-Transformationsmatrix multiplizieren
        Vec4 v1Trans = viewportTransform.multiply(newVector1);
        Vec4 v2Trans = viewportTransform.multiply(newVector2);
        Vec4 v3Trans = viewportTransform.multiply(newVector3);

        successor.write(new Face(v1Trans, v2Trans, v3Trans, input.getN1(), input.getN2(), input.getN3()));
    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
