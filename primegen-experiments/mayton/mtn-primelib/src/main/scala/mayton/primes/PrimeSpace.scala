package mayton.primes

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import java.nio.file.Files

import javax.imageio.ImageIO

object PrimeSpace {

  def main(args: Array[String]): Unit = {

    val SIZE = 1024
    val PIXEL = 4

    val CYAN = 0x12c2e8
    val RED = 0xd22d96

    val image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB)

    val g2d = image.createGraphics

    for(i <- 0 to SIZE / PIXEL) {
      for(j <- 0 to SIZE / PIXEL) {
        g2d.setColor(if (PrimeLib.mutuallyPrime(i,j)) Color.RED else Color.CYAN)
        g2d.fillRect(i * PIXEL, j * PIXEL, PIXEL, PIXEL)
      }
    }

    ImageIO.write(
      image,
      "PNG",
      new FileOutputStream(s"out/mutually-prime-${SIZE}x${SIZE}.png"))

  }

}
