package benoit.hackzone

import benoit.UnitSpec

class HackingSpec extends UnitSpec {

  case class Event(id:String, eventKey:String, payload:Option[String])

  val sentToOcf = Event("uuid-1", "SentToOCF", Some("sent-to-ocf-payload"))
  val callback  = Event("uuid-2", "Callback",  Some("callback-payload"))

  case class SentToOCFPayload(contents:String)
  case class CallbackPayload(contents:String)

  trait ReadPayload[T] {
    def read(event: Event): T
  }

  object EventPayloadApi {

    def read[A](e: Event)(implicit reader: ReadPayload[A]): A = reader.read(e)

    implicit val sentToOcfPayloadReads: ReadPayload[SentToOCFPayload] = new ReadPayload[SentToOCFPayload] {
      override def read(event: Event): SentToOCFPayload = new SentToOCFPayload(event.payload.get)
    }

    implicit val mt2: ReadPayload[CallbackPayload] = new ReadPayload[CallbackPayload] {
      override def read(event: Event): CallbackPayload = new CallbackPayload(event.payload.get)
    }
  }


  object ReadEventPayloadAPI {
    implicit class ReadPayloadOps(event: Event) {
      def readPayload[A](implicit reader: ReadPayload[A]) = {
        reader.read(event)
      }
    }
  }


  "this thing" - {

    "Mytype implicit resolution " in {

      import EventPayloadApi._
      import ReadEventPayloadAPI._

      println(sentToOcf.readPayload[SentToOCFPayload])
      println(callback.readPayload[CallbackPayload])
    }
  }
}
