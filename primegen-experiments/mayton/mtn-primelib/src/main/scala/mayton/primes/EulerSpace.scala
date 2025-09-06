package mayton.primes

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import mayton.primes.PrimeLib._

import javax.imageio.ImageIO

object EulerSpace {

  def main(args: Array[String]): Unit = {

    val SIZE = 1024

    val image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB)

    val g2d = image.createGraphics

    g2d.setColor(Color.WHITE)
    g2d.fillRect(0,0,SIZE,SIZE)

    for(x <- 0 until SIZE)
      image.setRGB(x, SIZE - φ(x), 0x00000000)

    ImageIO.write(
      image,
      "PNG",
      new FileOutputStream(s"out/euler-space-${SIZE}x${SIZE}.png"))

  }


}
