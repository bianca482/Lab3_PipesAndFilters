package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

import java.util.LinkedList;
import java.util.List;

public class ModelViewTransformation implements PushFilter<List<Face>> {

    private final PipelineData pd;
    private PushPipe<List<Face>> successor;
    private float rotation;

    public ModelViewTransformation(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(List<Face> input) {
        //Rotations-Matrix
        Mat4 rotation = Matrices.rotate(this.rotation, pd.getModelRotAxis());

        //Model-View Transformation
        Mat4 translation = pd.getModelTranslation().multiply(rotation);
        Mat4 viewTransform = pd.getViewTransform().multiply(translation);

        List<Face> faces = new LinkedList<>();

        input.forEach(face -> {
            Vec4 v1Trans = viewTransform.multiply(face.getV1());
            Vec4 v2Trans = viewTransform.multiply(face.getV2());
            Vec4 v3Trans = viewTransform.multiply(face.getV3());

            // Transformieren von Normalvektoren
            Vec4 n1Trans = viewTransform.multiply(face.getN1());
            Vec4 n2Trans = viewTransform.multiply(face.getN2());
            Vec4 n3Trans = viewTransform.multiply(face.getN3());

            faces.add(new Face(v1Trans, v2Trans, v3Trans, n1Trans, n2Trans, n3Trans));
        });

        successor.write(faces);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setSuccessor(PushPipe<List<Face>> successor){
        this.successor = successor;
    }
}
