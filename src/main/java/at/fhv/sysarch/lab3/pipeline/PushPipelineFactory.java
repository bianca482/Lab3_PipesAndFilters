package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.push.filter.ModelViewTransformation;
import at.fhv.sysarch.lab3.pipeline.push.pipe.ModelViewTransformationPipe;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PipeImpl;
import at.fhv.sysarch.lab3.pipeline.push.pipe.SinkPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: push from the source (model)
        ModelSource source = new ModelSource();

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        // TODO 2. perform backface culling in VIEW SPACE

        // TODO 3. perform depth sorting in VIEW SPACE

        // TODO 4. add coloring (space unimportant)

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        // TODO 6. perform perspective division to screen coordinates

        // TODO 7. feed into the sink (renderer)
        ModelSink sink = new ModelSink(pd, pd.getGraphicsContext());

        PipeImpl connector = new PipeImpl();
        connector.successor = sink;

        source.successor = connector;

        SinkPipe sinkPipe = new SinkPipe(sink);

        ModelViewTransformation modelViewTransformation = new ModelViewTransformation(pd, sinkPipe);

        ModelViewTransformationPipe modelCoordinatePipe = new ModelViewTransformationPipe(modelViewTransformation);

        source.successor = modelCoordinatePipe;

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            private float rotationRadiantPerSecond = 1f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians (abhängig von fraction) -erledigt
                float rotationRadiant = modelViewTransformation.rotation + rotationRadiantPerSecond * fraction;
                modelViewTransformation.rotation = rotationRadiant;

                // TODO create new model rotation matrix using pd.modelRotAxis
                Mat4 rotation = Matrices.rotate(0.4f, pd.getModelRotAxis());

                // TODO compute updated model-view tranformation
                Mat4 translation = pd.getModelTranslation().multiply(rotation);
                Mat4 modelTransform = pd.getViewportTransform().multiply(translation);

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline
                pd.getGraphicsContext().setStroke(Color.RED);
                source.write(model.getFaces());
            }
        };
    }
}