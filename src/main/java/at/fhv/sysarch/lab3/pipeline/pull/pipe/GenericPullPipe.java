package at.fhv.sysarch.lab3.pipeline.pull.pipe;

import at.fhv.sysarch.lab3.pipeline.pull.filter.PullFilter;

public class GenericPullPipe<T> implements PullPipe<T> {

    private PullFilter<T> input;

    public GenericPullPipe(PullFilter<T> input) {
        this.input = input;
    }

    @Override
    public T read() {
        return input.read();
    }
}
