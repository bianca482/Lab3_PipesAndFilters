package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

/*
To measure the angle between the light ray and the vertex use the normal vector
(which is provided for each vertex, see the Face class).
The angle between the two vectors can then easily be calculated with the dot product.

Therefore, you simply need to calculate the normalised normal vector between the face and the light position,
which you can obtain from PipelineData.getLightPos.
With this normal you compute the dot product between the face normal.
 */
public class FlatShading implements Filter<Pair<Face, Color>> {
    private PipelineData pd;
    private Pipe<Pair<Face, Color>> successor;

    public FlatShading(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Pair<Face, Color> pair) {

        Face input = pair.fst();

        // Normalvektor  anteile aus 4 dimensionalem Vektor in 3 dimensionalem Vektor speichern
        Vec3 normalVector = new Vec3(input.getN1().getX(), input.getN1().getY(), input.getN1().getZ());

        // Vierdimensionaler Vektor in drei dimensionalen Vektor speichern
        Vec3 vertexV3 = new Vec3(input.getV1().toVec3());

        // Richtungsvektor berechnen
        Vec3 directionVector = pd.getLightPos().subtract(vertexV3);

        // Kosinus Wert für den Winkel zwischen Normalvektor und Richtungsvektor
        double cosAlpha = (directionVector.dot(normalVector)) / (directionVector.getLength() * normalVector.getLength());

        double brightness = 1 - cosAlpha;




//        float transV1 = input.getV1().dot(input.getN1());
//        float transV2 = input.getV2().dot(input.getN2());
//        float transV3 = input.getV3().dot(input.getN3());
//
//        float result = pd.getLightPos().dot(new Vec3(transV1, transV2, transV3));
//
//        successor.write(new Face(input.getV1().multiply(result), input.getV2().multiply(result), input.getV3().multiply(result), input.getN1(), input.getN2(), input.getN3()));

        //Position der Beleuchtungsquelle -> davon die Normale berechnen und mit Color.interpolate() mit Wert der Beleuchtungsposition

        successor.write(pair);
    }

    public void setSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
