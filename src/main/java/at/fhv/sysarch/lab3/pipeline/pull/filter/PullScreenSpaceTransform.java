package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullScreenSpaceTransform implements PullFilter<Pair<Face, Color>> {
    private PipelineData pd;
    private PullPipe<Pair<Face, Color>> predecessor;

    public PullScreenSpaceTransform(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read(){
        Mat4 viewportTransform = pd.getViewportTransform();
        Pair<Face, Color> input = predecessor.read();
        if (input == null){
            return null;
        }

        Face face = input.fst();

        //Jede Komponente des Vektors durch W dividieren
        float dividedV1X = face.getV1().getX() / face.getV1().getW();
        float dividedV1Y = face.getV1().getY() / face.getV1().getW();
        float dividedV1Z = face.getV1().getZ() / face.getV1().getW();

        float dividedV2X = face.getV2().getX() / face.getV2().getW();
        float dividedV2Y = face.getV2().getY() / face.getV2().getW();
        float dividedV2Z = input.fst().getV2().getZ() / input.fst().getV2().getW();

        float dividedV3X = face.getV3().getX() / face.getV3().getW();
        float dividedV3Y = face.getV3().getY() / face.getV3().getW();
        float dividedV3Z = face.getV3().getZ() / face.getV3().getW();

        Vec4 newVector1 = new Vec4(dividedV1X, dividedV1Y, dividedV1Z, 1);
        Vec4 newVector2 = new Vec4(dividedV2X, dividedV2Y, dividedV2Z, 1);
        Vec4 newVector3 = new Vec4(dividedV3X, dividedV3Y, dividedV3Z, 1);

        //Die neuen Vektoren mit der Viewport-Transformationsmatrix multiplizieren
        Vec4 v1Trans = viewportTransform.multiply(newVector1);
        Vec4 v2Trans = viewportTransform.multiply(newVector2);
        Vec4 v3Trans = viewportTransform.multiply(newVector3);

        Pair<Face, Color> newInput = new Pair<>(new Face(v1Trans, v2Trans, v3Trans, face.getN1(), face.getN2(), face.getN3()), input.snd());

        return newInput;
    }

    public void setPredecessor(PullPipe<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }
}
