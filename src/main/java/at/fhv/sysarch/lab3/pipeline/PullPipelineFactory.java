package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.filter.*;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.GenericPullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // Pull from the source (model)
        PullModelSource pullModelSource = new PullModelSource();

        // 1. Perform model-view transformation from model to VIEW SPACE coordinates
        PullModelViewTransformation pullModelViewTransformation = new PullModelViewTransformation(pd);
        PullPipe<Face> modelViewPipe = new GenericPullPipe<>(pullModelSource);

        // 2. Backface culling in VIEW SPACE
        PullBackfaceCulling pullBackfaceCulling = new PullBackfaceCulling(pd);
        PullPipe<Face> cullingPipe = new GenericPullPipe<>(pullModelViewTransformation);

        // TODO 3. Depth sorting in VIEW SPACE
        PullDepthSorting pullDepthSorting = new PullDepthSorting(pd);
        PullPipe<Face> depthSortingPipe = new GenericPullPipe<>(pullBackfaceCulling);

        // 4. Add coloring (space unimportant)
        PullColoring pullColoring = new PullColoring(pd);
        PullPipe<Face> colorPipe = new GenericPullPipe<>(pullDepthSorting);

        PullPerspectiveProjection pullPerspectiveProjection;
        PullPipe<Pair<Face, Color>> perspectiveProjectionPipe;

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. Perform lighting in VIEW SPACE
            PullFlatShading pullFlatShading = new PullFlatShading(pd);
            PullPipe<Pair<Face, Color>> flatShadingPipe = new GenericPullPipe<>(pullColoring);

            pullFlatShading.setPredecessor(flatShadingPipe);

            // 5. Perform projection transformation on VIEW SPACE coordinates
            pullPerspectiveProjection = new PullPerspectiveProjection(pd);
            perspectiveProjectionPipe = new GenericPullPipe<>(pullFlatShading);

            pullPerspectiveProjection.setPredecessor(perspectiveProjectionPipe);
        } else {
            // 5. Perform projection transformation
            pullPerspectiveProjection = new PullPerspectiveProjection(pd);
            perspectiveProjectionPipe = new GenericPullPipe<>(pullColoring);

            pullPerspectiveProjection.setPredecessor(perspectiveProjectionPipe);

        }

        // 6. Perform perspective division to screen coordinates
        PullScreenSpaceTransform pullScreenSpaceTransform = new PullScreenSpaceTransform(pd);
        PullPipe<Pair<Face, Color>> screenPipe = new GenericPullPipe<>(pullPerspectiveProjection);

        // 7. Feed into the sink (renderer)
        PullModelSink pullModelSink = new PullModelSink(pd, pd.getGraphicsContext());
        PullPipe<Pair<Face, Color>> sinkPullPipe = new GenericPullPipe<>(pullScreenSpaceTransform);

        pullModelViewTransformation.setPredecessor(modelViewPipe);
        pullBackfaceCulling.setPredecessor(cullingPipe);
        pullDepthSorting.setPredecessor(depthSortingPipe);
        pullColoring.setPredecessor(colorPipe);
        pullScreenSpaceTransform.setPredecessor(screenPipe);
        pullModelSink.setPredecessor(sinkPullPipe);

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
                // Wenn Rendering aufgerufen wird, Faces für modelSource updaten
                pullModelSource.UpdateData(model.getFaces());

                // Compute rotation in radians
                float rotationRadiant = pullModelViewTransformation.getRotation() + rotationRadiantPerSecond * fraction;
                pullModelViewTransformation.setRotation(rotationRadiant);

                // Trigger rendering of the pipeline
                pullModelSink.read();

                // TODO create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate

                // TODO compute updated model-view tranformation

                // TODO update model-view filter


            }
        };
    }
}