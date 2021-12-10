package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;

import java.util.List;

public class ModelSource implements Filter<List<Face>> {

    private Pipe<Face> successor;

    @Override
    public void write(List<Face> faces) {
        for (Face face : faces) {
            this.successor.write(face);
        }
    }

    public void setSuccessor(Pipe<Face> successor) {
        this.successor = successor;
    }
}
