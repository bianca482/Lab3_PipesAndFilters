package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;

public class BackfaceCulling implements Filter<Face> {

    private Pipe<Face> successor;
    private PipelineData pd;
    private Vec3 viewingDirVector;

    public BackfaceCulling(PipelineData pd) {
        this.pd = pd;
        this.viewingDirVector = pd.getViewingCenter().subtract(pd.getViewingEye());
        // viewing Center = Zu diesem Punkt schaut die Kamera
        // viewing Eye = Hier steht die Kamera
        // viewingDirVector = In diese Richtung schaut die Kamera

    }

    @Override
    public void write(Face input) {

        Vec3 n1 = new Vec3(input.getN1().getX(), input.getN1().getY(), input.getN1().getZ());
        // Skalarprodukt berechnen
        float dot = n1.dot(viewingDirVector);
        // Falls Skalarprodukt größer 0, dann wirf dieses Faces weg.
        if (dot <= 0) {
            successor.write(input);
        }
    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
