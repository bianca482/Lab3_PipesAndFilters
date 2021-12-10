package at.fhv.sysarch.lab3.pipeline.push.pipe;

import at.fhv.sysarch.lab3.pipeline.push.filter.Filter;

public class GenericPipe<T> implements Pipe<T> {

    private Filter<T> output;

    public GenericPipe(Filter<T> output) {
        this.output = output;
    }

    @Override
    public void write(T value) {
        output.write(value);
    }
}
