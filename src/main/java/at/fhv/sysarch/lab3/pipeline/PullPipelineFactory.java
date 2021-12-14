package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.pull.filter.ModelSource;
import at.fhv.sysarch.lab3.pipeline.pull.filter.ModelViewTransformation;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.GenericPullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import javafx.animation.AnimationTimer;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // Pull from the source (model)
        ModelSource modelSource = new ModelSource();

        // 1. Perform model-view transformation from model to VIEW SPACE coordinates
        ModelViewTransformation modelViewTransformation = new ModelViewTransformation(pd);
        PullPipe<Face> modelViewPipe = new GenericPullPipe<>(modelSource);

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

        modelViewTransformation.setPredecessor(modelViewPipe);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // Wenn Rendering aufgerufen wird, Faces für modelSource updaten
                modelSource.UpdateData(model.getFaces());

                // TODO compute rotation in radians

                // TODO create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate

                // TODO compute updated model-view tranformation

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline
            }
        };
    }
}