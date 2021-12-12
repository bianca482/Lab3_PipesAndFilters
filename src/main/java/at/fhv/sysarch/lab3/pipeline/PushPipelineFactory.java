package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.filter.*;
import at.fhv.sysarch.lab3.pipeline.push.pipe.*;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.List;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // Push from the source (model)
        ModelSource source = new ModelSource();

        // Perform model-view transformation from model to VIEW SPACE coordinates
        ModelViewTransformation modelViewTransformation = new ModelViewTransformation(pd);
        Pipe<Face> modelPipe = new GenericPipe<>(modelViewTransformation);

        // Backface culling in VIEW SPACE
        BackfaceCulling backfaceCulling = new BackfaceCulling(pd);
        Pipe<Face> cullingPipe = new GenericPipe<>(backfaceCulling);

        // TODO 3. perform depth sorting in VIEW SPACE
        // DepthSorting depthSorting = new DepthSorting(pd);
        // Pipe<List<Face>> sortingPipe = new GenericPipe<>(depthSorting);

        // Add coloring (space unimportant)
        Coloring coloring = new Coloring(pd);
        Pipe<Face> colorPipe = new GenericPipe<>(coloring);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        PerspectiveProjection perspectiveProjection = new PerspectiveProjection(pd);
        Pipe<Face> perspectivePipe = new GenericPipe<>(perspectiveProjection);

        // Perform perspective division to screen coordinates
        ScreenSpaceTransform screenSpaceTransform = new ScreenSpaceTransform(pd);
        Pipe<Face> screenSpacePipe = new GenericPipe<>(screenSpaceTransform);

        // Feed into the sink (renderer)
        Filter<Pair<Face, Color>> sink = new ModelSink(pd, pd.getGraphicsContext());
        Pipe<Pair<Face, Color>> sinkPipe = new GenericPipe<>(sink);

        source.setSuccessor(modelPipe);
        modelViewTransformation.setSuccessor(cullingPipe);
        backfaceCulling.setSuccessor(perspectivePipe);
        perspectiveProjection.setSuccessor(screenSpacePipe);
        screenSpaceTransform.setSuccessor(colorPipe);
        coloring.setSuccessor(sinkPipe);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // Rotation variable
            private float rotationRadiantPerSecond = 1f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // Compute rotation in radians
                float rotationRadiant = modelViewTransformation.getRotation() + rotationRadiantPerSecond * fraction;
                modelViewTransformation.setRotation(rotationRadiant);

                // Trigger rendering of the pipeline
                source.write(model.getFaces());
            }
        };
    }
}