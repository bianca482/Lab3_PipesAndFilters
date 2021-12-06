package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;

public class ModelViewTransformation implements Filter {

    private PipelineData pd;

    public ModelViewTransformation(PipelineData pd) {
        this.pd = pd;
    }
}
