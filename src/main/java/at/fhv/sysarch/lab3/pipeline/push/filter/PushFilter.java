package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.pipeline.push.pipe.PushPipe;

public interface PushFilter<T, W> {
    void write(T input);
    void setSuccessor(PushPipe<W> successor);
}
