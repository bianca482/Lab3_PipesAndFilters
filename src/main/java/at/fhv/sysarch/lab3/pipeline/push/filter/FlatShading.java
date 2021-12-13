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

        // Normalvektor berechnen: Anteile aus 4 dimensionalem Vektor in 3 dimensionalem Vektor speichern
        Vec3 normalVector = new Vec3(input.getN1().getX(), input.getN1().getY(), input.getN1().getZ());

        // Vierdimensionaler Vektor in drei dimensionalem Vektor speichern
        Vec3 vertexV3 = new Vec3(input.getV1().toVec3());

        // Richtungsvektor berechnen
        Vec3 directionVector = pd.getLightPos().subtract(vertexV3);

        // Kosinus Wert für den Winkel zwischen Normalvektor und Richtungsvektor berechnen
        double cosAlpha = (directionVector.dot(normalVector)) / (directionVector.getLength() * normalVector.getLength());
        // Da wir den Winkel zwischen Normalvektor und Richtungsvektor berechnet haben, rechnen wir noch '1-'
        double brightness = 1 - cosAlpha;

        // Geht der Kosinus Wert gegen 0, scheint das Licht senkrecht auf die Fläche, und die Fläche sollte maximale Helligkeit haben
        // Anpassen der Farbhelligkeit
        // Color color = Color.BLACK.interpolate(pair.snd(), brightness);
        Color color = pair.snd().darker().interpolate(pair.snd(), brightness);

        //Position der Beleuchtungsquelle -> davon die Normale berechnen und mit Color.interpolate() mit Wert der Beleuchtungsposition

        successor.write(new Pair<Face, Color>(input, color));
    }

    public void setSuccessor(Pipe<Pair<Face, Color>> successor) {
        this.successor = successor;
    }
}
