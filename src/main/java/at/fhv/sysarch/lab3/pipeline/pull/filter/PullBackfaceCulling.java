package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Vec3;

public class PullBackfaceCulling implements PullFilter<Face> {

    private PullPipe<Face> predecessor;
    private PipelineData pd;
    private Vec3 viewingDirVector;

    public PullBackfaceCulling(PipelineData pd) {
        this.pd = pd;
        this.viewingDirVector = pd.getViewingCenter().subtract(pd.getViewingEye());
        // viewing Center = Zu diesem Punkt schaut die Kamera
        // viewing Eye = Hier steht die Kamera
        // viewingDirVector = In diese Richtung schaut die Kamera

    }

    @Override
    public Face read() {
        Face face = predecessor.read();

        if (face == null) {
            return null;
        }

        Vec3 n1 = new Vec3(face.getN1().getX(), face.getN1().getY(), face.getN1().getZ());
        // Skalarprodukt berechnen
        float dot = n1.dot(viewingDirVector);
        // Falls Skalarprodukt größer 0, dann wirf dieses Faces weg.
        if (dot > 0) {
            return null;
        }
        return face;
    }

    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}
