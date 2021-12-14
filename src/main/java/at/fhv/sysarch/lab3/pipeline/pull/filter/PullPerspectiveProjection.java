package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullPerspectiveProjection implements PullFilter<Pair<Face, Color>> {

    private PipelineData pd;
    private PullPipe<Pair<Face, Color>> predecessor;

    public PullPerspectiveProjection(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {

        Mat4 projTransform = pd.getProjTransform();
        Pair <Face, Color> input = predecessor.read();
        if (input == null){
            return null;
        }

        Face face = input.fst();

        Vec4 v1Trans = projTransform.multiply(face.getV1());
        Vec4 v2Trans = projTransform.multiply(face.getV2());
        Vec4 v3Trans = projTransform.multiply(face.getV3());

        Pair<Face, Color> newInput = new Pair<>(new Face(v1Trans, v2Trans, v3Trans, input.fst().getN1(), face.getN2(), face.getN3()), input.snd());

        return newInput;
    }

    public void setPredecessor(PullPipe<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }
}
