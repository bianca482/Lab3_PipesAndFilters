package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import com.hackoeur.jglm.Vec3;

import java.util.LinkedList;
import java.util.List;

public class PushBackfaceCulling implements PushFilter<List<Face>, List<Face>> {

    private PushPipe<List<Face>> successor;
    private final Vec3 viewingDirVector;

    public PushBackfaceCulling(Vec3 viewingCenter, Vec3 viewingEye) {
        this.viewingDirVector = viewingCenter.subtract(viewingEye);
        // viewing Center = Zu diesem Punkt schaut die Kamera
        // viewing Eye = Hier steht die Kamera
        // viewingDirVector = In diese Richtung schaut die Kamera
    }

    @Override
    public void write(List<Face> input) {
        List<Face> faces = new LinkedList<>();

        input.forEach(face -> {
            Vec3 n1 = face.getN1().toVec3();
            // Skalarprodukt berechnen
            float dot = n1.dot(viewingDirVector);
            // Falls Skalarprodukt größer 0, dann wirf dieses Faces weg -> nur verarbeiten, wenn <= 0.
            if (dot <= 0) {
                faces.add(face);
            }
        });

        successor.write(faces);
    }

    @Override
    public void setSuccessor(PushPipe<List<Face>> successor) {
        this.successor = successor;
    }
}
