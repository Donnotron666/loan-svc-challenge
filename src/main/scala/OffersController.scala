import model.{Experiment, Experiments, LoanOffer, UserInfo}
import org.joda.time.{DateTime, Interval, Period}
import spray.json.JsonParser

import scala.concurrent.ExecutionContext
import scala.io.Source

class OffersController(implicit val executionContext: ExecutionContext) extends JsonSupport {

  val experiments: Experiments = loadExperiments()
  def currentExperiment() : Option[Experiment] = experiments.experiments.find( e => e.interval.contains(DateTime.now()))

  def getOffers(userInfo: UserInfo) : Option[Seq[LoanOffer]] = {
    currentExperiment.map { exp =>
      exp.offers.filter(_.minScore <= userInfo.credit_score)
    }
  }


  def loadExperiments(): Experiments = {
    val exps = JsonParser(Source.fromInputStream(getClass().getResourceAsStream("experiments.json")).mkString)
      .convertTo[Experiments]

    //validate that there aren't concurrent offers
    for((exp, i) <- exps.experiments.zipWithIndex) {
      for((subExp, si) <- exps.experiments.zipWithIndex) {
        if(si > i) {
            assert(!subExp.interval.overlaps(exp.interval))
        }
      }
    }
    exps
  }
}
