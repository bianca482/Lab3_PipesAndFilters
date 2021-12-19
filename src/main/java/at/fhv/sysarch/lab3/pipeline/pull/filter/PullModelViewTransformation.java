package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PullModelViewTransformation implements PullFilter<Face> {

    private PullPipe<Face> predecessor;
    private Mat4 viewTransform;

    @Override
    public Face read() {
        Face face = predecessor.read();

        if (face == null) {
            return null;
        }

        Vec4 v1Trans = viewTransform.multiply(face.getV1());
        Vec4 v2Trans = viewTransform.multiply(face.getV2());
        Vec4 v3Trans = viewTransform.multiply(face.getV3());

        // Transformieren von Normalvektoren
        Vec4 n1Trans = viewTransform.multiply(face.getN1());
        Vec4 n2Trans = viewTransform.multiply(face.getN2());
        Vec4 n3Trans = viewTransform.multiply(face.getN3());

        return new Face(v1Trans, v2Trans, v3Trans, n1Trans, n2Trans, n3Trans);
    }

    public void setViewTransform(Mat4 viewTransform) {
        this.viewTransform = viewTransform;
    }

    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}