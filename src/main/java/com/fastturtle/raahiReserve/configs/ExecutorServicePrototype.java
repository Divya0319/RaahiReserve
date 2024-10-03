package com.fastturtle.raahiReserve.configs;

import java.util.concurrent.ExecutorService;

public interface ExecutorServicePrototype {

    ExecutorService clone();
}
