package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;

import java.util.LinkedList;
import java.util.List;

/*
It is mandatory to implement depth sorting for a (mostly) correct visibility.
Depth sorting simply sorts the faces in view space according to their z (depth) value back to front, descending with the z value, that is the face with the highest z value is the first.
This has the effect that we render the faces back-to-front, which results in faces closer to the camera to occlude the faces farther away (aka Painters Algorithm).
You need a single z value of each face, for sorting purposes, therefore compute the average of all z values of a face which gives a good result compared to its computational effort (other options are min/max or simply picking a fixed vertex).
 */

public class DepthSorting implements Filter<List<Face>> {

    public DepthSorting(PipelineData pd) {
        this.pd = pd;
    }

    private Pipe<Face> successor;
    private PipelineData pd;

    @Override
    public void write(List<Face> faces) {
        // TODO Dürfen wir Liste mit allen Faces übergeben? Dies müssten wir dann auch bei den vorhergehenden Filtern übergeben.
        // Falls nein, wie sollen wir sortieren, wenn wir noch nicht wissen, was später kommt und wann alle Faces eines Models durch sind?
        // Zum Sortieren müssen wir ja alle Faces haben, bevor wir sie sortieren und sortiert weitergeben können.


        // Painter's algorithm

        // 1. Find the average depth of each face
        List<Pair<Float,Face>> faceList = new LinkedList<>();
        for(Face input : faces){
            // erstellen von 3 Dimensionalen Vektoren, damit Rechnen später einfacher
            Vec3 vertex1 = new  Vec3(input.getV1().getX(), input.getV1().getY(),input.getV1().getZ());
            Vec3 vertex2 = new  Vec3(input.getV2().getX(), input.getV2().getY(),input.getV2().getZ());
            Vec3 vertex3 = new  Vec3(input.getV3().getX(), input.getV3().getY(),input.getV3().getZ());

            // Berechne Abstand von Camera zu den jeweiligen 3 Eckpunkten des Dreiecks (Face)
            float z1 = vertex1.subtract(pd.getViewingEye()).getLength();
            float z2 = vertex2.subtract(pd.getViewingEye()).getLength();
            float z3 = vertex3.subtract(pd.getViewingEye()).getLength();

            // Berechne durchschnittlichen Abstand
            float z = (z1 +z2+z3)/3;

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

    public void setSuccessor (Pipe < Face > successor) {
        this.successor = successor;
    }
}
