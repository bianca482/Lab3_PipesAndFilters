package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

public class ModelViewTransformation implements Filter<Face> {

    private PipelineData pd;
    private Pipe<Face> succesor;
    public float rotation;

    public ModelViewTransformation(PipelineData pd, Pipe<Face> successor) {
        this.pd = pd;
        this.succesor = successor;
    }

    @Override
    public void write(Face face) {

        Mat4 rotation = Matrices.rotate(this.rotation, pd.getModelRotAxis());

        Mat4 translation = pd.getModelTranslation().multiply(rotation);
        Mat4 modelTransform = pd.getViewportTransform().multiply(translation);

        //Filter 1
        Vec4 v1Trans = modelTransform.multiply(face.getV1());
        Vec4 v2Trans = modelTransform.multiply(face.getV2());
        Vec4 v3Trans = modelTransform.multiply(face.getV3());

        face = new Face(v1Trans, v2Trans, v3Trans, v1Trans, v1Trans, v1Trans);
        succesor.write(face);
    }
}
