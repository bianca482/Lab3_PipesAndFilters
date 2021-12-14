package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
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

public class PullFlatShading implements PullFilter<Pair<Face, Color>> {
    private PipelineData pd;
    private PullPipe<Pair<Face, Color>> predecessor;

    public PullFlatShading(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {

        Pair<Face, Color> pair = predecessor.read();

        if (pair == null) {
            return null;
        }

        Face input = pair.fst();

        // Normalvektor berechnen: Anteile aus 4 dimensionalem Vektor in 3 dimensionalem Vektor speichern
        Vec3 normalVector = new Vec3(input.getN1().getX(), input.getN1().getY(), input.getN1().getZ());

        // Vierdimensionaler Vektor in drei dimensionalem Vektor speichern
        Vec3 vertexV3 = input.getV1().toVec3();

        // Richtungsvektor berechnen
        Vec3 directionVector = pd.getLightPos().subtract(vertexV3);

        // Kosinus Wert für den Winkel zwischen Normalvektor und Richtungsvektor berechnen
        double cosAlpha = (directionVector.dot(normalVector)) / (directionVector.getLength() * normalVector.getLength());

        // Geht der Kosinus Wert gegen 0, scheint das Licht senkrecht auf die Fläche, und die Fläche sollte maximale Helligkeit haben
        // Anpassen der Farbhelligkeit
        Color color = Color.BLACK.interpolate(pair.snd(), cosAlpha);

        return new Pair<>(input, color);
    }

    public void setPredecessor(PullPipe<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }
}
