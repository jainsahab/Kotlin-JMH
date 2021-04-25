package com.codingflower

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.All)
@Warmup(iterations = 1)
@Measurement(iterations = 1, batchSize = 1)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class BenchmarkProperties {
}