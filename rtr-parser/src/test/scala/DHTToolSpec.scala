class DHTToolSpec extends munit.FunSuite {

  test("fromBtihToMagnet") {
    val res = DHTTool.fromBtihToMagnet("d3e0c8b6cf02f67aa80c1ffcdf32d5a408ffefb4","Джоджо Мойес - Ночная музыка (2022) MP3")
    assert(res == "magnet:?xt=urn:btih:d3e0c8b6cf02f67aa80c1ffcdf32d5a408ffefb4&dn=%D0%94%D0%B6%D0%BE%D0%B4%D0%B6%D0%BE+%D0%9C%D0%BE%D0%B9%D0%B5%D1%81+-+%D0%9D%D0%BE%D1%87%D0%BD%D0%B0%D1%8F+%D0%BC%D1%83%D0%B7%D1%8B%D0%BA%D0%B0+%282022%29+MP3")
  }

  test("extractMagnet") {
    assert(DHTTool.extractOnlyBtihCode(
      "magnet:?xt=urn:btih:5332f8f23d340c7e6a07c4a44b26f7eb54741c1e&dn=rutor.info&tr=udp://opentor.net:6969&tr=http://retracker.local/announce")
      == "5332f8f23d340c7e6a07c4a44b26f7eb54741c1e")
  }

}
