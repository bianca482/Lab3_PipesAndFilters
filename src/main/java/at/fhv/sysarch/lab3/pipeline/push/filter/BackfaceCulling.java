package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/*
In general you simply need to compute the dot product between the vertex and its normal
and if it is larger than 0 the face has to be culled, that is not processed any further in the pipeline
 */

public class BackfaceCulling implements Filter<Face> {

    private Pipe<Face> successor;

    @Override
    public void write(Face input) {
        float transV1 = input.getV1().dot(input.getN1());
        float transV2 = input.getV2().dot(input.getN2());
        float transV3 = input.getV3().dot(input.getN3());

        //float transformedVertexes = (transV2 - transV1) % (transV3 - transV1) * transV1;
        float transformedVertexes = transV1 + transV2 + transV3;

        //if (transformedVertexes <= 0) {
            successor.write(input);
        //}
    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
