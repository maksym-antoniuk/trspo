package ua.nure.antoniuk

import java.awt.image.BufferedImage

fun BufferedImage.bordered(border: Int): BufferedImage {
    val image = BufferedImage(this.width + border * 2, this.height + border * 2, BufferedImage.TYPE_INT_RGB)
    for (i in 0 until this.width) {
        for (j in 0 until this.height) {
            image.setRGB(i + border, j + border, this.getRGB(i, j))
        }
    }
    for (i in 0 until border) {
        for (j in 0 until border) {
            image.setRGB(i, j, this.getRGB(0,0))
            image.setRGB(i + this.width + border, j, this.getRGB(this.width - 1, 0))
            image.setRGB(i, j + this.height + border, this.getRGB(0, this.height - 1))
            image.setRGB(i + this.width + border, j + this.height + border, this.getRGB(this.width - 1, this.height - 1))
        }
    }
    for (i in 0 until border) {
        for (j in border until image.width - border) {
            image.setRGB(j, i, this.getRGB(j - border, 0))
            image.setRGB(j, i + this.height + border, this.getRGB(j - border, this.height - 1))
        }
    }
    for (i in 0 until border) {
        for (j in border until image.height - border) {
            image.setRGB(i, j, this.getRGB(0, j - border))
            image.setRGB(i + this.width + border, j, this.getRGB(this.width - 1, j - border))
        }
    }
    return image
}

fun BufferedImage.cropped(border: Int): BufferedImage {
    val image = BufferedImage(this.width - border * 2, this.height - border * 2, BufferedImage.TYPE_INT_RGB)
    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            image.setRGB(x, y, this.getRGB(x + border, y + border))
        }
    }
    return image
}