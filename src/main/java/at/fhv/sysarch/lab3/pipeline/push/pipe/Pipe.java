package at.fhv.sysarch.lab3.pipeline.push.pipe;

public interface Pipe<T> {
    void write(T value);
}
