def cp(is : InputStream, os : OutputStream, size : Long) : Unit = {
  val buf : Array[Byte] = Array.ofDim[Byte](128 * 1024 * 1024)
  var cnt = size
  while(cnt > 0) {
    var res = is.read(buf)
    os.write(buf, 0, res)
    cnt = cnt - res
  }
  os.close()
}

def cpn(i : String, o : String, size : Long) : Unit = cp(new FileInputStream(i), new FileOutputStream(o), size)