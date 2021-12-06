package at.fhv.sysarch.lab3.pipeline.push.pipe;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.Filter;

public class ColoringPipe implements Pipe<Face>{

    private Filter<Face> successor;

    public ColoringPipe(Filter<Face> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Face value) {

    }
}
