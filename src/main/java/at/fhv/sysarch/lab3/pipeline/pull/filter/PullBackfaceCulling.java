package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import com.hackoeur.jglm.Vec3;

public class PullBackfaceCulling implements PullFilter<Face, Face> {

    private PullPipe<Face> predecessor;
    private final Vec3 viewingDirVector;

    public PullBackfaceCulling(Vec3 viewingCenter, Vec3 viewingEye) {
        this.viewingDirVector = viewingCenter.subtract(viewingEye);
        // viewing Center = Zu diesem Punkt schaut die Kamera
        // viewing Eye = Hier steht die Kamera
        // viewingDirVector = In diese Richtung schaut die Kamera
    }

    @Override
    public Face read() {
        Face face = predecessor.read();

        while (face != null) {
            Vec3 n1 = face.getN1().toVec3();
            // Skalarprodukt berechnen
            float dot = n1.dot(viewingDirVector);
            // Falls Skalarprodukt größer 0, dann wirf dieses Faces weg.
            if (dot <= 0) {
                return face;
            }
            face = predecessor.read();
        }
        return null;
    }

    @Override
    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}
