package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

public class PullModelViewTransformation implements PullFilter<Face> {

    private final PipelineData pd;
    private PullPipe<Face> predecessor;
    private float rotation;

    public PullModelViewTransformation(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Face read() {
        Face face = predecessor.read();
        if (face == null) {
            return null;
        }

        //Rotations-Matrix
        Mat4 rotation = Matrices.rotate(this.rotation, pd.getModelRotAxis());

        //Model-View Transformation
        Mat4 translation = pd.getModelTranslation().multiply(rotation);
        Mat4 viewTransform = pd.getViewTransform().multiply(translation);

        Vec4 v1Trans = viewTransform.multiply(face.getV1());
        Vec4 v2Trans = viewTransform.multiply(face.getV2());
        Vec4 v3Trans = viewTransform.multiply(face.getV3());

        // Transformieren von Normalvektoren
        Vec4 n1Trans = viewTransform.multiply(face.getN1());
        Vec4 n2Trans = viewTransform.multiply(face.getN2());
        Vec4 n3Trans = viewTransform.multiply(face.getN3());

        face = new Face(v1Trans, v2Trans, v3Trans, n1Trans, n2Trans, n3Trans);

        return face;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}