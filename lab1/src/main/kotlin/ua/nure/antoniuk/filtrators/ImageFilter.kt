package ua.nure.antoniuk.filtrators

import java.awt.image.BufferedImage

interface ImageFilter {
    fun filter(img: BufferedImage): BufferedImage
}