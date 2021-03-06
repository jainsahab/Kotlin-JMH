package com.codingflower

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.annotations.Mode.*
import java.util.concurrent.TimeUnit

@Warmup(iterations = 1)
@Measurement(iterations = 2, batchSize = 1)
@State(Scope.Thread)
@Fork(value = 1, jvmArgs = ["-Xms2G", "-Xmx2G"])
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class BenchmarkProperties