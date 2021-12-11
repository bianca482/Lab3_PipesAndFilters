package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;

/*
In general you simply need to compute the dot product between the vertex and its normal
and if it is larger than 0 the face has to be culled, that is not processed any further in the pipeline
 */

public class BackfaceCulling implements Filter<Face> {

    private Pipe<Face> successor;
    private PipelineData pd;

    public BackfaceCulling(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face input) {
        float transV1 = input.getN1().dot(input.getV1());
        float transV2 = input.getN2().dot(input.getV2());
        float transV3 = input.getN3().dot(input.getV3());

        //float transformedVertexes = (transV2 - transV1) % (transV3 - transV1) * transV1;
        float transformedVertexes = transV1 + transV2 + transV3;

        //float result = input.getV1().getNegated().dot(input.getN1());

//        float x1minusy2 = input.getV1().getX() - input.getV2().getY();
//        float x2minusy1 = input.getV2().getX() - input.getV1().getY();
//        float x2minusy3 = input.getV2().getX() - input.getV3().getY();
//        float x3minusy2 = input.getV3().getX() - input.getV2().getY();
//        float x3minusy1 = input.getV3().getX() - input.getV1().getY();
//        float x1minusy3 = input.getV1().getX() - input.getV3().getY();
//
//        //Fläche des Dreiecks
//        float result = (x1minusy2 + x2minusy1 + x2minusy3 + x3minusy2 + x3minusy1 + x1minusy3) / 2;


        //if (transformedVertexes <= 0) {
            successor.write(input);
        //}
    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
