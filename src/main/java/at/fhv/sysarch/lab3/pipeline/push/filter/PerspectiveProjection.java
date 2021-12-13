package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PerspectiveProjection implements Filter<Pair<Face, Color>> {
    private PipelineData pd;
    private Pipe<Pair<Face, Color>> successor;

    public PerspectiveProjection(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> input) {
        Mat4 projTransform = pd.getProjTransform();

        Vec4 v1Trans = projTransform.multiply(input.fst().getV1());
        Vec4 v2Trans = projTransform.multiply(input.fst().getV2());
        Vec4 v3Trans = projTransform.multiply(input.fst().getV3());

        Pair<Face, Color> newInput = new Pair<>(new Face(v1Trans, v2Trans, v3Trans, input.fst().getN1(), input.fst().getN2(), input.fst().getN3()), input.snd());

        successor.write(newInput);
    }

    public void setSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
