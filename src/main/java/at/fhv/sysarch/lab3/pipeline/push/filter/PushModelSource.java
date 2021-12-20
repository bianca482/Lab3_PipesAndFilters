package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;

import java.util.List;

public class PushModelSource implements PushFilter<List<Face>, List<Face>> {

    private PushPipe<List<Face>> successor;

    @Override
    public void write(List<Face> faces) {
       this.successor.write(faces);
    }

    @Override
    public void setSuccessor(PushPipe<List<Face>> successor) {
        this.successor = successor;
    }
}
