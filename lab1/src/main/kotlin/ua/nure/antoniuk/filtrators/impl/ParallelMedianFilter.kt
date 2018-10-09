package ua.nure.antoniuk.filtrators.impl

import java.awt.image.BufferedImage
import kotlin.concurrent.thread

class ParallelMedianFilter(private val threadCount: Int): AbstractMedianFilter() {

    override fun filter(img: BufferedImage) {
        if (img.width - 1 < threadCount) {
            filterPartImage(img, (1 until img.width - 1).toList(), (1 until img.height - 1).toList())
        } else {
            val threads = mutableListOf<Thread>()
            (1 until img.width - 1).chunked(img.width / threadCount).forEach {
                threads.add(thread {
                    filterPartImage(img, it, (1 until img.height - 1).toList())
                })
            }
            threads.forEach { it.join() }
        }
    }
}