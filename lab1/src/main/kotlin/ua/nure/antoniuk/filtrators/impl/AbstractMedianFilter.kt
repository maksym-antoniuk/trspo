package ua.nure.antoniuk.filtrators.impl

import ua.nure.antoniuk.filtrators.ImageFilter
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

abstract class AbstractMedianFilter: ImageFilter {

    protected fun filterPartImage(img: BufferedImage, widthRange: List<Int>, heightRange: List<Int>) {
        val pixel = arrayOfNulls<Color>(9)
        val r = IntArray(9)
        val g = IntArray(9)
        val b = IntArray(9)
        for (i in widthRange) {
            for (j in heightRange) {
                pixel[0] = Color(img.getRGB(i - 1, j - 1))
                pixel[1] = Color(img.getRGB(i - 1, j))
                pixel[2] = Color(img.getRGB(i - 1, j + 1))
                pixel[3] = Color(img.getRGB(i, j + 1))
                pixel[4] = Color(img.getRGB(i + 1, j + 1))
                pixel[5] = Color(img.getRGB(i + 1, j))
                pixel[6] = Color(img.getRGB(i + 1, j - 1))
                pixel[7] = Color(img.getRGB(i, j - 1))
                pixel[8] = Color(img.getRGB(i, j))
                for (k in 0..8) {
                    r[k] = pixel[k]!!.red
                    b[k] = pixel[k]!!.blue
                    g[k] = pixel[k]!!.green
                }
                Arrays.sort(r)
                Arrays.sort(g)
                Arrays.sort(b)
                img.setRGB(i, j, Color(r[4], b[4], g[4]).rgb)
            }
        }
    }
}