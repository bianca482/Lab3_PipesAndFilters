package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.List;

public class PullModelSource implements PullFilter<Face> {

    public List<Face> data;
    int idx = -1;

    @Override
    public Face read() {
        if (idx + 1 < data.size()) {
            idx = idx + 1;
            return data.get(idx);
        } else {
            return null;
        }
    }

    public void UpdateData(List<Face> data) {
        this.data = data;
        idx = -1;
    }
}
