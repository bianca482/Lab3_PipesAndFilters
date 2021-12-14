package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.List;

public class BackfaceCulling implements PullFilter<List<Face>> {
    @Override
    public List<Face> read() {
        return null;
    }
}
