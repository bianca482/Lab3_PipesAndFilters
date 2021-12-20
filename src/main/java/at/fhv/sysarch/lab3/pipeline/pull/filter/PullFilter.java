package at.fhv.sysarch.lab3.pipeline.pull.filter;

import at.fhv.sysarch.lab3.pipeline.pull.pipe.PullPipe;

public interface PullFilter<T, W> {
    T read();
    void setPredecessor(PullPipe<W> predecessor);
}
