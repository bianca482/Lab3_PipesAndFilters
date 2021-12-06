package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;

public class ModelSink {

    private GraphicsContext context;
    private PipelineData pd;
    public float rotation = 0.1f;

    public ModelSink(PipelineData pd, GraphicsContext context) {
        this.context = context;
        this.pd = pd;
    }

    public void write(Face face) {
        //Input für Filter 1
        Mat4 rotation = Matrices.rotate(this.rotation, pd.getModelRotAxis());

        Mat4 translation = pd.getModelTranslation().multiply(rotation);
        Mat4 modelTransform = pd.getViewportTransform().multiply(translation);
        //

        //Filter 1
        Vec4 v1Trans = modelTransform.multiply(face.getV1());
        Vec4 v2Trans = modelTransform.multiply(face.getV2());
        Vec4 v3Trans = modelTransform.multiply(face.getV3());

        //Projection -> Größe anpassen
        context.strokeLine(v1Trans.getX()/10, v1Trans.getY()/10, v2Trans.getX()/10, v2Trans.getY()/10);
        context.strokeLine(v1Trans.getX()/10, v1Trans.getY()/10, v3Trans.getX()/10, v3Trans.getY()/10);
        context.strokeLine(v2Trans.getX()/10, v2Trans.getY()/10, v3Trans.getX()/10, v3Trans.getY()/10);

//        context.strokeLine(face.getV1().getX() * 100, face.getV1().getY() * 100, face.getV2().getX() * 100, face.getV2().getY() * 100);
//        context.strokeLine(face.getV2().getX() * 100, face.getV2().getY() * 100, face.getV3().getX() * 100, face.getV3().getY() * 100);
    }
}
