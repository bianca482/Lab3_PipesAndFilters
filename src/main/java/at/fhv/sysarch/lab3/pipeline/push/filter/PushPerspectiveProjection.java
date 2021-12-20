package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PushPerspectiveProjection implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {
    private final Mat4 projTransform;
    private PushPipe<Pair<Face, Color>> successor;

    public PushPerspectiveProjection(Mat4 projTransform) {
        this.projTransform = projTransform;
    }

    @Override
    public void write(Pair<Face, Color> input) {
        Face face = input.fst();

        Vec4 v1Trans = projTransform.multiply(face.getV1());
        Vec4 v2Trans = projTransform.multiply(face.getV2());
        Vec4 v3Trans = projTransform.multiply(face.getV3());

        Pair<Face, Color> newInput = new Pair<>(new Face(v1Trans, v2Trans, v3Trans, face.getN1(), face.getN2(), face.getN3()), input.snd());

        successor.write(newInput);
    }

    @Override
    public void setSuccessor(PushPipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
