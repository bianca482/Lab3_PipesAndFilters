package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;

public class ModelSink {

    private GraphicsContext context;
    private PipelineData pd;
    //public float rotation = 0.1f;

    public ModelSink(PipelineData pd, GraphicsContext context) {
        this.context = context;
        this.pd = pd;
    }

    public void write(Face face) {
        Vec4 v1Trans = face.getV1();
        Vec4 v2Trans = face.getV2();
        Vec4 v3Trans = face.getV3();

        //Projection -> Größe anpassen
        float scaleFactor = 4;
        context.strokeLine(v1Trans.getX() / scaleFactor, v1Trans.getY() / scaleFactor, v2Trans.getX() / scaleFactor, v2Trans.getY() / scaleFactor);
        context.strokeLine(v1Trans.getX() / scaleFactor, v1Trans.getY() / scaleFactor, v3Trans.getX() / scaleFactor, v3Trans.getY() / scaleFactor);
        context.strokeLine(v2Trans.getX() / scaleFactor, v2Trans.getY() / scaleFactor, v3Trans.getX() / scaleFactor, v3Trans.getY() / scaleFactor);

//        context.strokeLine(face.getV1().getX() * 100, face.getV1().getY() * 100, face.getV2().getX() * 100, face.getV2().getY() * 100);
//        context.strokeLine(face.getV2().getX() * 100, face.getV2().getY() * 100, face.getV3().getX() * 100, face.getV3().getY() * 100);
    }
}
