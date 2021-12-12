package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;

import java.util.LinkedList;
import java.util.List;

public class BackfaceCulling implements Filter<List<Face>> {

    private Pipe<List<Face>> successor;
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
    public void write(List<Face> input) {

        List<Face> faces = new LinkedList<>();

        input.forEach(face -> {
            Vec3 n1 = new Vec3(face.getN1().getX(), face.getN1().getY(), face.getN1().getZ());
            // Skalarprodukt berechnen
            float dot = n1.dot(viewingDirVector);
            // Falls Skalarprodukt größer 0, dann wirf dieses Faces weg.
            if (dot <= 0) {
                faces.add(face);
            }
        });

        successor.write(faces);
    }

    public void setSuccessor(Pipe<List<Face>> successor) {
        this.successor = successor;
    }
}
