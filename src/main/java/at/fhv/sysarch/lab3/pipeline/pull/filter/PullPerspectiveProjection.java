package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullPerspectiveProjection implements PullFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private final Mat4 projTransform;
    private PullPipe<Pair<Face, Color>> predecessor;

    public PullPerspectiveProjection(Mat4 projTransform) {
        this.projTransform = projTransform;
    }

    @Override
    public Pair<Face, Color> read() {
        Pair <Face, Color> input = predecessor.read();

        if (input == null){
            return null;
        }

        Face face = input.fst();

        Vec4 v1Trans = projTransform.multiply(face.getV1());
        Vec4 v2Trans = projTransform.multiply(face.getV2());
        Vec4 v3Trans = projTransform.multiply(face.getV3());

        return new Pair<>(new Face(v1Trans, v2Trans, v3Trans, face.getN1(), face.getN2(), face.getN3()), input.snd());
    }

    @Override
    public void setPredecessor(PullPipe<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }
}
