package at.fhv.sysarch.lab3.pipeline.pull.pipe;

public interface PullPipe<T> {
    T read();
}