package at.fhv.sysarch.lab3.pipeline.push.pipe;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.ModelSink;

public class PipeImpl<T> implements Pipe<T> {
    public ModelSink successor; //Nachgänger, eventuell Vorgänger. Write- gibt das an Successor weiter

    public void write(Face face) {
        this.successor.write(face);
    }

    @Override
    public void write(T value) {

    }
}
