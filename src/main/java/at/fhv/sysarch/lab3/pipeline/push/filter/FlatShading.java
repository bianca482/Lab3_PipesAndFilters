package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;

/*
To measure the angle between the light ray and the vertex use the normal vector
(which is provided for each vertex, see the Face class).
The angle between the two vectors can then easily be calculated with the dot product.

Therefore, you simply need to calculate the normalised normal vector between the face and the light position,
which you can obtain from PipelineData.getLightPos.
With this normal you compute the dot product between the face normal.
 */
public class FlatShading implements Filter<Face> {
    private PipelineData pd;
    private Pipe<Face> successor;

    public FlatShading(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face input) {
        float transV1 = input.getV1().dot(input.getN1());
        float transV2 = input.getV2().dot(input.getN2());
        float transV3 = input.getV3().dot(input.getN3());

        float result = pd.getLightPos().dot(new Vec3(transV1, transV2, transV3));

        successor.write(new Face(input.getV1().multiply(result), input.getV2().multiply(result), input.getV3().multiply(result), input.getN1(), input.getN2(), input.getN3()));

    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}