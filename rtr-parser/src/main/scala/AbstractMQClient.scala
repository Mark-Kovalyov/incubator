abstract class AbstractMQClient[T] :

  @throws(classOf[Exception])
  def send(entity: T) : Unit

