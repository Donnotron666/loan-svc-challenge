import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model.{Experiment, Experiments, LoanOffer, PhoneNumber}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, deserializationError}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object LocalDateTimeFormat extends JsonFormat[DateTime] {
    val pattern = "yyyy-MM-dd"
    def write(dateTime: DateTime) = JsString(dateTime.formatted(pattern))

    def read(value: JsValue): DateTime = value match {
      case JsString(_) => {
        val dts = value.convertTo[String]
        DateTime.parse(dts, DateTimeFormat.forPattern(pattern))
      }
      case _ => deserializationError("Date Time expected.")
    }
  }
  implicit val phoneNumberFormat = jsonFormat1(PhoneNumber)
  implicit val orderFormat = jsonFormat4(LoanOffer) // contains List[Item]
  implicit val itemFormat = jsonFormat4(Experiment)
  implicit val experimentsFormat = jsonFormat1(Experiments)
}
