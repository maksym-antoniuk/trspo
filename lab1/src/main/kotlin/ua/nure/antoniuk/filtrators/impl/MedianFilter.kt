package ua.nure.antoniuk.filtrators.impl

import java.awt.image.BufferedImage

class MedianFilter: AbstractMedianFilter() {

    override fun filter(img: BufferedImage) {
        filterPartImage(img, (1 until img.width - 1).toList(), (1 until img.height - 1).toList())
    }
}