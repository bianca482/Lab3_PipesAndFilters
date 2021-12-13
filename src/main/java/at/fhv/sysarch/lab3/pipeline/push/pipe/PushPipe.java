package at.fhv.sysarch.lab3.pipeline.push.pipe;

public interface PushPipe<T> {
    void write(T value);
}
