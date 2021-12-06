package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;

import java.util.List;

public class ModelSource {

    public Pipe<Face> successor; //Generalisieren, Interface verwenden; ModelSource hat Nachfolger

    public void write(List<Face> faces) {
        for (Face face : faces) {
            this.successor.write(face);
        }
    }

}
