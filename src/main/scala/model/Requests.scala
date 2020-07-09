package model {

  import org.joda.time.{DateTime, Interval}

  case class PhoneNumber(input: String)
  case class UserInfo(phone_number: PhoneNumber, credit_score:Float)

  case class LoanOffer(minScore: Double, amount: Int, fee: Int, term: Int)

  case class Experiment(name: String, startDate: DateTime, endDate: DateTime, offers: List[LoanOffer]) {
    def interval() = new Interval(startDate, endDate)
  }

  case class Experiments(experiments: List[Experiment])

  object ValidPhoneNumber {
    val NumberPattern = "^(\\d{13})?$".r

    def unapply(phn: String): Option[PhoneNumber] = {
      phn match {
        case NumberPattern(c) => Some(PhoneNumber(phn))
        case _ => None
      }
    }
  }
}