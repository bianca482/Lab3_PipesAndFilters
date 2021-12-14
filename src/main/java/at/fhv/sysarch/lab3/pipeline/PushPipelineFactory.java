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
        PushModelSource source = new PushModelSource();

        // 1. Perform model-view transformation from model to VIEW SPACE coordinates
        PushModelViewTransformation pushModelViewTransformation = new PushModelViewTransformation(pd);
        PushPipe<List<Face>> modelPushPipe = new GenericPushPipe<>(pushModelViewTransformation);

        // 2. Backface culling in VIEW SPACE
        PushBackfaceCulling pushBackfaceCulling = new PushBackfaceCulling(pd);
        PushPipe<List<Face>> cullingPushPipe = new GenericPushPipe<>(pushBackfaceCulling);

        // 3. perform depth sorting in VIEW SPACE
        PushDepthSorting pushDepthSorting = new PushDepthSorting(pd);
        PushPipe<List<Face>> sortingPushPipe = new GenericPushPipe<>(pushDepthSorting);

        // 4. Add coloring (space unimportant)
        PushColoring pushColoring = new PushColoring(pd);
        PushPipe<Face> colorPushPipe = new GenericPushPipe<>(pushColoring);

        PushPerspectiveProjection pushPerspectiveProjection;
        PushPipe<Pair<Face, Color>> perspectivePushPipe;

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. Perform lighting in VIEW SPACE
            PushFlatShading pushFlatShading = new PushFlatShading(pd);
            PushPipe<Pair<Face, Color>> shadingPushPipe = new GenericPushPipe<>(pushFlatShading);

            pushColoring.setSuccessor(shadingPushPipe);

            // 5. Perform projection transformation on VIEW SPACE coordinates
            pushPerspectiveProjection = new PushPerspectiveProjection(pd);
            perspectivePushPipe = new GenericPushPipe<>(pushPerspectiveProjection);

            pushFlatShading.setSuccessor(perspectivePushPipe);
        } else {
            // 5. Perform projection transformation
            pushPerspectiveProjection = new PushPerspectiveProjection(pd);
            perspectivePushPipe = new GenericPushPipe<>(pushPerspectiveProjection);

            pushColoring.setSuccessor(perspectivePushPipe);
        }

        // Perform perspective division to screen coordinates
        PushScreenSpaceTransform pushScreenSpaceTransform = new PushScreenSpaceTransform(pd);
        PushPipe<Pair<Face, Color>> screenSpacePushPipe = new GenericPushPipe<>(pushScreenSpaceTransform);

        // Feed into the sink (renderer)
        PushFilter<Pair<Face, Color>> sink = new PushModelSink(pd, pd.getGraphicsContext());
        PushPipe<Pair<Face, Color>> sinkPushPipe = new GenericPushPipe<>(sink);

        source.setSuccessor(modelPushPipe);
        pushModelViewTransformation.setSuccessor(cullingPushPipe);
        pushBackfaceCulling.setSuccessor(sortingPushPipe);
        pushDepthSorting.setSuccessor(colorPushPipe);
        pushPerspectiveProjection.setSuccessor(screenSpacePushPipe);
        pushScreenSpaceTransform.setSuccessor(sinkPushPipe);

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
                float rotationRadiant = pushModelViewTransformation.getRotation() + rotationRadiantPerSecond * fraction;
                pushModelViewTransformation.setRotation(rotationRadiant);

                // Trigger rendering of the pipeline
                source.write(model.getFaces());
            }
        };
    }
}