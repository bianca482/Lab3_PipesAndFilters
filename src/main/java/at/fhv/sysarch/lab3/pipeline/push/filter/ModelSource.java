package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;

import java.util.List;

public class ModelSource implements Filter<List<Face>> {

    private Pipe<List<Face>> successor;

    @Override
    public void write(List<Face> faces) {
       this.successor.write(faces);
    }

    public void setSuccessor(Pipe<List<Face>> successor) {
        this.successor = successor;
    }
}
