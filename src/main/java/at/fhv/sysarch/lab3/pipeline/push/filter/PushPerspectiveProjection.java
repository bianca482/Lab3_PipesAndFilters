package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PushPerspectiveProjection implements PushFilter<Pair<Face, Color>> {
    private PipelineData pd;
    private PushPipe<Pair<Face, Color>> successor;

    public PushPerspectiveProjection(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> input) {
        Mat4 projTransform = pd.getProjTransform();

        Face face = input.fst();

        Vec4 v1Trans = projTransform.multiply(face.getV1());
        Vec4 v2Trans = projTransform.multiply(face.getV2());
        Vec4 v3Trans = projTransform.multiply(face.getV3());

        Pair<Face, Color> newInput = new Pair<>(new Face(v1Trans, v2Trans, v3Trans, input.fst().getN1(), face.getN2(), face.getN3()), input.snd());

        successor.write(newInput);
    }

    public void setSuccessor(PushPipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}