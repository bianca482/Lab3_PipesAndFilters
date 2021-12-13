package at.fhv.sysarch.lab3.pipeline.push.filter;

public interface PushFilter<T> {
    void write(T input);
}
