package mayton.sftp

import org.xbill.DNS.{DClass, Message, Name, PTRRecord, Record, Section, SimpleResolver, Type}
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object DnsZioPipeline extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ???

}
