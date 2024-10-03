package com.fastturtle.raahiReserve.configs;

public class ExecutorService implements ClonableObject<ExecutorService> {

    private long numberOfThreads;

    public ExecutorService(long numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public ExecutorService cloneObject() {
        return null;
    }
}
