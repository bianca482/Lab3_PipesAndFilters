package at.fhv.sysarch.lab3.pipeline.pull.filter;

public interface PullFilter<T> {
    T read();
}
