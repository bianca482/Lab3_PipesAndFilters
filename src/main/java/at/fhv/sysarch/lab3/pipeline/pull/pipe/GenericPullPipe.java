package at.fhv.sysarch.lab3.pipeline.pull.pipe;

import at.fhv.sysarch.lab3.pipeline.pull.filter.PullFilter;

public class GenericPullPipe<T, W> implements PullPipe<T> {

    private PullFilter<T, W> input;

    public GenericPullPipe(PullFilter<T, W> input) {
        this.input = input;
    }

    @Override
    public T read() {
        return input.read();
    }
}
