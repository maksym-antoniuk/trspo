package ua.nure.antoniuk

import ua.nure.antoniuk.filtrators.impl.GaussianBlurFilter
import ua.nure.antoniuk.filtrators.impl.MedianFilter
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
    val filter = args[0]
    val radius = args[1].substring(2).toInt()
    val isParallel = "-t\\d+".toRegex().matches(args[2])
    var threadCount = if (isParallel) args[2].substring(2).toInt() else 1
    val startIndex = if (isParallel) 1 else 0

    val inputFile = File(args[startIndex + 2])
    val outputFile = File(if (args.size > startIndex + 3) args[startIndex + 3] else "output${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS"))}.jpg")

    val image = ImageIO.read(inputFile)
    val executionTime = measureTimeMillis {
        val result = when (filter) {
            "median" -> MedianFilter(threadCount, radius).filter(image)
            "gaussian" -> GaussianBlurFilter(threadCount, radius).filter(image)
            else -> image
        }
        ImageIO.write(result, "jpg", outputFile)
    }

    println("Execution time: $executionTime ms")
}