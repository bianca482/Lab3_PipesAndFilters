package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

public class ModelViewTransformation implements Filter<Face> {

    private final PipelineData pd;
    private final Pipe<Face> successor;
    public float rotation;

    public ModelViewTransformation(PipelineData pd, Pipe<Face> successor) {
        this.pd = pd;
        this.successor = successor;
    }

    @Override
    public void write(Face input) {
        //Rotations-Matrix
        Mat4 rotation = Matrices.rotate(this.rotation, pd.getModelRotAxis());

        //Model-View Transformation
        Mat4 translation = pd.getModelTranslation().multiply(rotation).translate(pd.getModelPos());
        Mat4 viewTransform = pd.getViewTransform().multiply(translation);
        Mat4 modelTransform = pd.getViewportTransform().multiply(viewTransform);

        Vec4 v1Trans = modelTransform.multiply(input.getV1());
        Vec4 v2Trans = modelTransform.multiply(input.getV2());
        Vec4 v3Trans = modelTransform.multiply(input.getV3());

        input = new Face(v1Trans, v2Trans, v3Trans, v1Trans, v1Trans, v1Trans); //Wieso 4x v1Trans?

        successor.write(input);
    }
}
