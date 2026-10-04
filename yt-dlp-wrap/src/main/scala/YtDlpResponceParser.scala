package enot

import tools.jackson.databind.{JsonNode, ObjectMapper, ObjectReader}


object YtDlpResponceParser {

  def extractJson(mixed : String) : String = {
    val i = mixed.indexOf("{")
    mixed.substring(i)
  }

  def extractOnly(json : String) : List[YtDlpRespRow] = {
    val mapper : ObjectMapper = new ObjectMapper()

    val root : JsonNode = mapper.readTree(json)

    val formats : JsonNode = root.get("formats")

    val objectReader : ObjectReader = mapper.readerForListOf(classOf[YtDlpRespRowJava])

    val resVal : java.util.ArrayList[YtDlpRespRowJava] = objectReader.readValue(formats)

    // TODO:

    List()
  }

  def parse(inputJson : String) : List[YtDlpRespRow] = {
    List(
      YtDlpRespRow.fromId(
        "136"
      ),
      YtDlpRespRow.fromId(
        "140"
      )
    )
  }

}
