package mayton

trait Job {

  def step(params : Map[String, String]) : Either[String, Map[String, String]]

}
