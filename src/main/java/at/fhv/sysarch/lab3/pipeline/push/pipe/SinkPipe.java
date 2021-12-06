package at.fhv.sysarch.lab3.pipeline.push.pipe;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.ModelSink;

public class SinkPipe implements Pipe<Face> {

    private ModelSink modelSink;

   public SinkPipe(ModelSink modelSink){
       this.modelSink = modelSink;
   }

    @Override
    public void write(Face value) {
        modelSink.write(value);
    }
}
