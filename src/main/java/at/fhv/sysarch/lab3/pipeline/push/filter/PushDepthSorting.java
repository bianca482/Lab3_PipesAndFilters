package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
import com.hackoeur.jglm.Vec3;

import java.util.LinkedList;
import java.util.List;

/*
It is mandatory to implement depth sorting for a (mostly) correct visibility.
Depth sorting simply sorts the faces in view space according to their z (depth) value back to front, descending with the z value, that is the face with the highest z value is the first.
This has the effect that we render the faces back-to-front, which results in faces closer to the camera to occlude the faces farther away (aka Painters Algorithm).
You need a single z value of each face, for sorting purposes, therefore compute the average of all z values of a face which gives a good result compared to its computational effort (other options are min/max or simply picking a fixed vertex).
 */

public class PushDepthSorting implements PushFilter<List<Face>, Face> {

    private PushPipe<Face> successor;
    private final Vec3 viewingEye;

    public PushDepthSorting(Vec3 viewingEye) {
        this.viewingEye = viewingEye;
    }

    @Override
    public void write(List<Face> faces) {
        // Painter's algorithm
        // 1. Find the average depth of each face
        List<Pair<Float,Face>> faceList = new LinkedList<>();

        for(Face input : faces) {
            // erstellen von 3 Dimensionalen Vektoren, damit Rechnen später einfacher
            Vec3 vertex1 = input.getV1().toVec3();
            Vec3 vertex2 = input.getV2().toVec3();
            Vec3 vertex3 = input.getV3().toVec3();

            // Berechne Abstand von Camera zu den jeweiligen 3 Eckpunkten des Dreiecks (Face)
            float z1 = vertex1.subtract(viewingEye).getLength();
            float z2 = vertex2.subtract(viewingEye).getLength();
            float z3 = vertex3.subtract(viewingEye).getLength();

            // Berechne durchschnittlichen Abstand
            float z = (z1+z2+z3)/3;

            // füge Abstand und Dreieck in Liste
            faceList.add(new Pair<>(z, input));
        }

        // 2. Sort all faces based on average depths
        // Sortiere Liste anhand der Abstände. Minus wird benötigt, damit die Sortierung absteigend (desc) ist.
        faceList.sort((pair1, pair2) ->  (- pair1.fst().compareTo(pair2.fst())) );

        // 3. Draw the furthest surfaces away
        // Schreibe einzelne Faces mit absteigendem Abstand in nächste Pipe, damit diese dann gezeichnet werden können.
        for (Pair<Float, Face> floatFacePair : faceList) {
            successor.write(floatFacePair.snd());
        }
    }

    @Override
    public void setSuccessor (PushPipe<Face> successor) {
        this.successor = successor;
    }
}
