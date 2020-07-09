import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import model.{PhoneNumber, UserInfo, ValidPhoneNumber}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}


object Main extends App with JsonSupport {
  implicit val system: ActorSystem = ActorSystem("example-interview")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  lazy val offers: OffersController = new OffersController()
  val routes = path("loans" / "offers") {
    parameters('phone_number.as[String], 'credit_score.as[Float]) { (pns, cs) =>
      get {
        pns match {
          case ValidPhoneNumber(_) => complete(offers.getOffers(UserInfo(PhoneNumber(pns), cs)))
          case _ => complete(StatusCodes.BadRequest)
        }
      }
    }
  }


  val binding = Http().bindAndHandle(
    routes,
    "127.0.0.1",
    9000
  )
  Await.result(system.whenTerminated, Duration.Inf)
}
