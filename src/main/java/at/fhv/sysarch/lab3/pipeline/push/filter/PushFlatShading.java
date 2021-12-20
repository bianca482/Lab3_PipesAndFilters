package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;
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
public class PushFlatShading implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {
    private Vec3 lightPos;
    private PushPipe<Pair<Face, Color>> successor;

    public PushFlatShading(Vec3 lightPos) {
        this.lightPos = lightPos;
    }

    @Override
    public void write(Pair<Face, Color> pair) {
        Face input = pair.fst();

        // Normalvektor berechnen: Anteile aus 4 dimensionalem Vektor in 3 dimensionalem Vektor speichern
        Vec3 normalVector = input.getN1().toVec3();

        // Vierdimensionaler Vektor in drei dimensionalem Vektor speichern
        Vec3 vertexV3 = input.getV1().toVec3();

        // Richtungsvektor berechnen
        Vec3 directionVector = lightPos.subtract(vertexV3);

        // Kosinus Wert für den Winkel zwischen Normalvektor und Richtungsvektor berechnen
        double brightness = (directionVector.dot(normalVector)) / (directionVector.getLength() * normalVector.getLength());

        // Geht der Kosinus Wert (= brightness) gegen 0, scheint das Licht senkrecht auf die Fläche, und die Fläche sollte maximale Helligkeit haben
        // Anpassen der Farbhelligkeit
        Color color = Color.BLACK.interpolate(pair.snd(), brightness);

        successor.write(new Pair<>(input, color));
    }

    @Override
    public void setSuccessor(PushPipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
