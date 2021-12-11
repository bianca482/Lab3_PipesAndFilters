package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.pipeline.push.pipe.Pipe;

public interface Filter<T> {
    //Successor
    //Write-Methode (bei Pull: read-Methode) hat einen Input (T) und einen Successor
    void write(T input);
}
