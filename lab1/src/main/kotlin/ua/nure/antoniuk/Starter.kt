package ua.nure.antoniuk

import ua.nure.antoniuk.filtrators.impl.MedianFilter
import ua.nure.antoniuk.filtrators.impl.ParallelMedianFilter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("No input file")
        return
    }
    val isParallel = "-p\\d+".toRegex().matches(args[0])
    var threadCount = if (isParallel) args[0].substring(2).toInt() else 0
    val startIndex = if (isParallel) 1 else 0

    val inputFile = File(args[startIndex + 0])
    val outputFile = File(if (args.size > startIndex + 1) args[startIndex + 1] else "output${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS"))}.jpg")

    val executionTime = measureTimeMillis {
        val image = ImageIO.read(inputFile)
        if (isParallel) ParallelMedianFilter(threadCount).filter(image) else MedianFilter().filter(image)
        ImageIO.write(image, "jpg", outputFile)
    }

    println("Execution time: $executionTime ms")
}