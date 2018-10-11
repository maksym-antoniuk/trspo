package ua.nure.antoniuk.filtrators.impl

import ua.nure.antoniuk.bordered
import ua.nure.antoniuk.cropped
import ua.nure.antoniuk.filtrators.ImageFilter
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import kotlin.concurrent.thread

class GaussianBlurFilter(private val threadCount: Int, private val radius: Int): ImageFilter {

    override fun filter(img: BufferedImage): BufferedImage {
        val tempImage = img.bordered(radius)
        val sampler = img.bordered(radius)

        val k = radius * 2 + 1
        val k2 = k * k

        val kernel = createKernel()

        val threads = mutableListOf<Thread>()
        (radius until tempImage.width - radius).chunked(img.width / threadCount).forEach {
            threads.add(thread {
                val pixel = Array<Color>(k2) { _ -> Color.black }
                val r = FloatArray(k2)
                val g = FloatArray(k2)
                val b = FloatArray(k2)
                for (x in it) {
                    for (y in radius until tempImage.height - radius) {
                        var index = 0
                        for (i in 0 until k) {
                            for (j in 0 until k) {
                                pixel[index++] = Color(sampler.getRGB(x + (j - radius), y + (i - radius)))
                            }
                        }
                        for (i in 0 until k2) {
                            r[i] = pixel[i].red * kernel[i]
                            b[i] = pixel[i].blue * kernel[i]
                            g[i] = pixel[i].green * kernel[i]
                        }
                        tempImage.setRGB(x, y, Color(r.sum()/255, g.sum()/255, b.sum()/255).rgb)
                    }
                }
            })
        }
        threads.forEach { it.join() }

        return tempImage.cropped(radius)
    }

    private fun createKernel(): List<Float> {
        val k = radius * 2 + 1
        val kernel2D = List(k) { _ -> FloatArray(k)}
        val sigma = radius / 2.0f
        var sum = 0.0f

        for (row in kernel2D.indices) {
            for (col in kernel2D.indices) {
                val x = gaussian(row.toFloat(), radius.toFloat(), sigma) * gaussian(col.toFloat(), radius.toFloat(), sigma)
                kernel2D[row][col] = x
                sum += x
            }
        }

        for (row in kernel2D.indices) {
            for (col in 0 until kernel2D[row].size) {
                kernel2D[row][col] /= sum
            }
        }
        return kernel2D.asSequence().flatMap { it.asSequence() }.toList()
    }

    private fun gaussian(x: Float, m: Float, sigma: Float): Float {
        return Math.exp(-((x - m) / sigma * ((x - m) / sigma)) / 2.0).toFloat()
    }
}