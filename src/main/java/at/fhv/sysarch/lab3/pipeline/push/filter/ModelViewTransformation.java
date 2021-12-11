package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

public class ModelViewTransformation implements Filter<Face> {

    private final PipelineData pd;
    private Pipe<Face> successor;
    private float rotation;

    public ModelViewTransformation(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face input) {
        //Rotations-Matrix
        Mat4 rotation = Matrices.rotate(this.rotation, pd.getModelRotAxis());

        //Model-View Transformation
        Mat4 translation = pd.getModelTranslation().multiply(rotation);
        Mat4 viewTransform = pd.getViewTransform().multiply(translation);

        Vec4 v1Trans = viewTransform.multiply(input.getV1());
        Vec4 v2Trans = viewTransform.multiply(input.getV2());
        Vec4 v3Trans = viewTransform.multiply(input.getV3());

        input = new Face(v1Trans, v2Trans, v3Trans, input.getN1(), input.getN2(), input.getN3());

        successor.write(input);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setSuccessor(Pipe<Face> successor){
        this.successor = successor;
    }
}
