import java.time.LocalDate

class RtrSpecification extends munit.FunSuite {

  test("extractId") {
    assert(RutorUtils.extractId("http://d.rutor.info/download/886309").isDefined)
    assert(RutorUtils.extractId("http://d.rutor.info/download/886309").get == "886309")
    assert(RutorUtils.extractId("http://d.rutor.info/download/").isEmpty)
  }

  test("extractDate") {
    assert(RutorUtils.extractDate("30&nbsp;Авг&nbsp;22").isDefined)
    assert(RutorUtils.extractDate("30&nbsp;Авг&nbsp;22").get == LocalDate.of(2022, 8, 30))
    assert(RutorUtils.extractDate("14&nbsp;Янв&nbsp;20").get == LocalDate.of(2020, 1, 14))
  }

}
