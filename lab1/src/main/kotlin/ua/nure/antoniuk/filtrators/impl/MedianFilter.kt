package ua.nure.antoniuk.filtrators.impl

import ua.nure.antoniuk.bordered
import ua.nure.antoniuk.cropped
import ua.nure.antoniuk.filtrators.ImageFilter
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import kotlin.concurrent.thread

class MedianFilter(private val threadCount: Int, private val radius: Int): ImageFilter {

    override fun filter(img: BufferedImage): BufferedImage {
        val tempImage = img.bordered(radius)
        val sampler = img.bordered(radius)

        val threads = mutableListOf<Thread>()
        val sizeWindow = radius * 2 + 1
        val countWindowPixels = sizeWindow * sizeWindow
        val halfCountWindowPixels = countWindowPixels / 2

        (radius until tempImage.width - radius).chunked(tempImage.width / threadCount).forEach {
            threads.add(thread {
                val pixel = Array<Color>(9) { _ -> Color.black }
                val r = IntArray(9)
                val g = IntArray(9)
                val b = IntArray(9)
                for (x in it) {
                    for (y in (radius until tempImage.height - radius)) {
                        var index = 0
                        for (i in 0 until sizeWindow) {
                            for (j in 0 until sizeWindow) {
                                pixel[index++] = Color(sampler.getRGB(x + (j - radius), y + (i - radius)))
                            }
                        }
                        for (i in 0 until countWindowPixels) {
                            r[i] = pixel[i].red
                            b[i] = pixel[i].blue
                            g[i] = pixel[i].green
                        }
                        Arrays.sort(r)
                        Arrays.sort(g)
                        Arrays.sort(b)
                        tempImage.setRGB(x, y, Color(r[halfCountWindowPixels], b[halfCountWindowPixels], g[halfCountWindowPixels]).rgb)
                    }
                }
            })
        }
        threads.forEach { it.join() }
        return tempImage.cropped(radius)
    }
}