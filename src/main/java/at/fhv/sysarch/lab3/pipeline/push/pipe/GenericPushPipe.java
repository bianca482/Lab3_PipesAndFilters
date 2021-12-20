package at.fhv.sysarch.lab3.pipeline.push.pipe;

import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;

public class GenericPushPipe<T, W> implements PushPipe<T> {

    private PushFilter<T, W> output;

    public GenericPushPipe(PushFilter<T, W> output) {
        this.output = output;
    }

    @Override
    public void write(T value) {
        output.write(value);
    }
}
