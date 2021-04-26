package com.codingflower

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.annotations.Mode.*
import java.util.concurrent.TimeUnit

@BenchmarkMode(value = [AverageTime, SampleTime])
@Warmup(iterations = 2)
@Measurement(iterations = 3, batchSize = 1)
@State(Scope.Thread)
@Fork(value = 1, jvmArgs = ["-Xms2G", "-Xmx2G"])
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class BenchmarkProperties