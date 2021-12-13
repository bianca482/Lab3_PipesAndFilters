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

        // 1. Perform model-view transformation from model to VIEW SPACE coordinates
        ModelViewTransformation modelViewTransformation = new ModelViewTransformation(pd);
        PushPipe<List<Face>> modelPushPipe = new GenericPushPipe<>(modelViewTransformation);

        // 2. Backface culling in VIEW SPACE
        BackfaceCulling backfaceCulling = new BackfaceCulling(pd);
        PushPipe<List<Face>> cullingPushPipe = new GenericPushPipe<>(backfaceCulling);

        // 3. perform depth sorting in VIEW SPACE
        DepthSorting depthSorting = new DepthSorting(pd);
        PushPipe<List<Face>> sortingPushPipe = new GenericPushPipe<>(depthSorting);

        // 4. Add coloring (space unimportant)
        Coloring coloring = new Coloring(pd);
        PushPipe<Face> colorPushPipe = new GenericPushPipe<>(coloring);

        PerspectiveProjection perspectiveProjection;
        PushPipe<Pair<Face, Color>> perspectivePushPipe;

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. Perform lighting in VIEW SPACE
            FlatShading flatShading = new FlatShading(pd);
            PushPipe<Pair<Face, Color>> shadingPushPipe = new GenericPushPipe<>(flatShading);

            coloring.setSuccessor(shadingPushPipe);

            // 5. Perform projection transformation on VIEW SPACE coordinates
            perspectiveProjection = new PerspectiveProjection(pd);
            perspectivePushPipe = new GenericPushPipe<>(perspectiveProjection);

            flatShading.setSuccessor(perspectivePushPipe);
        } else {
            // 5. Perform projection transformation
            perspectiveProjection = new PerspectiveProjection(pd);
            perspectivePushPipe = new GenericPushPipe<>(perspectiveProjection);

            coloring.setSuccessor(perspectivePushPipe);
        }

        // Perform perspective division to screen coordinates
        ScreenSpaceTransform screenSpaceTransform = new ScreenSpaceTransform(pd);
        PushPipe<Pair<Face, Color>> screenSpacePushPipe = new GenericPushPipe<>(screenSpaceTransform);

        // Feed into the sink (renderer)
        PushFilter<Pair<Face, Color>> sink = new ModelSink(pd, pd.getGraphicsContext());
        PushPipe<Pair<Face, Color>> sinkPushPipe = new GenericPushPipe<>(sink);

        source.setSuccessor(modelPushPipe);
        modelViewTransformation.setSuccessor(cullingPushPipe);
        backfaceCulling.setSuccessor(sortingPushPipe);
        depthSorting.setSuccessor(colorPushPipe);
        perspectiveProjection.setSuccessor(screenSpacePushPipe);
        screenSpaceTransform.setSuccessor(sinkPushPipe);

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