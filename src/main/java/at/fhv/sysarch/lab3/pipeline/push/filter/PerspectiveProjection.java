package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PerspectiveProjection implements Filter<Face> {
    private PipelineData pd;
    private Pipe<Face> successor;

    public PerspectiveProjection(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face input) {
        Mat4 projTransform = pd.getProjTransform();

        Vec4 v1Trans = projTransform.multiply(input.getV1());
        Vec4 v2Trans = projTransform.multiply(input.getV2());
        Vec4 v3Trans = projTransform.multiply(input.getV3());

        successor.write(new Face(v1Trans, v2Trans, v3Trans, input.getN1(), input.getN2(), input.getN3()));
    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
