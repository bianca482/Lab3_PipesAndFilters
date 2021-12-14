package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;

/*
It is mandatory to implement depth sorting for a (mostly) correct visibility.
Depth sorting simply sorts the faces in view space according to their z (depth) value back to front, descending with the z value, that is the face with the highest z value is the first.
This has the effect that we render the faces back-to-front, which results in faces closer to the camera to occlude the faces farther away (aka Painters Algorithm).
You need a single z value of each face, for sorting purposes, therefore compute the average of all z values of a face which gives a good result compared to its computational effort (other options are min/max or simply picking a fixed vertex).
 */

public class PullDepthSorting implements PullFilter<Face> {

    private PullPipe<Face> predecessor;
    private PipelineData pd;

    public PullDepthSorting(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Face read() {
        return null;
    }

    public void setPredecessor(PullPipe<Face> predecessor) {
        this.predecessor = predecessor;
    }
}
