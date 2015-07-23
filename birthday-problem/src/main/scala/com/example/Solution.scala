package com.example
import akka.actor._

object FindSolutionApp extends App {
  val system = ActorSystem("SolveProblem")
  val albert = system.actorOf(Props[Albert], name = "albert")
  val bernard = system.actorOf(Props[Bernard], name = "bernard") // start the action

  val dates = (15, "May") ::(16, "May") ::(19, "May") ::(17, "Jun") ::(18, "Jun") ::(14, "Jul") ::
    (16, "Jul") ::(14, "Aug") ::(15, "Aug") ::(17, "Aug") :: Nil

  println("App -> Start to Bernard")
  bernard ! StartBernard(16, dates, albert)

  println("App -> Start to Albert ")
  albert ! StartAlbert("Jul", dates, bernard)

  Thread.sleep(2000);
  system.shutdown()

}


class StartMessage(dates: List[(Int, String)])

case class StartBernard(day: Integer, dates: List[(Int, String)], albert: ActorRef) extends StartMessage(dates)

case class StartAlbert(month: String, dates: List[(Int, String)], bernard: ActorRef) extends StartMessage(dates)

case object No

case object BothNo

case object Found

case object FoundWithHelp


object getMonthsHavingUniqueDay {
  def apply(dates: List[(Int, String)]): Iterable[String] = {
    // map of day to List of Months having this day (foldLeft)
    val monthsListPerDay = (Map[Int, List[String]]() /: dates) {
      (map, x) => map + (x._1 -> (x._2 :: map.getOrElse(x._1, Nil)))
    }

    // filter only the ones which have 1 month in the list
    val filterOnlyUniqueDays = monthsListPerDay.filter(_._2.length == 1)

    // flat map of the remaining list of tuples for the second element (month)
    filterOnlyUniqueDays.flatMap(dayMonths => dayMonths._2)
  }
}

class Bernard extends Actor {
  var matchingDates: List[(Int, String)] = Nil
  var monthsOfUniqueDays: Iterable[String] = Nil

  def receive = {
    case StartBernard(day, dates, albertRef) => {
      println("Bernard received Start")
      // finds and saves the List of months cotaining unique days
      monthsOfUniqueDays = getMonthsHavingUniqueDay(dates)
      // finds and saves the matching dates given the input day
      matchingDates = dates.filter(_._1 == day)
      matchingDates.length match {
        case 1 => {
          println("Bernard found: " + matchingDates.map { case ((k, v)) => k + ", " + v }.head)
          println("Bernard -> Found to Albert")
          albertRef ! Found
          context.stop(self)
        }
        case 0 => {
          println("Invalid Input not existing day in input list ")
          println("Bernard -> No to Albert")
          albertRef ! No
        }
        case _ =>
      }
      context.become(waitResponseFormAlbert, true)
    }
  }

  def waitResponseFormAlbert: Receive = {
    case BothNo => {
      println("Bernard received BothNo")
      // means that no month belonging to unique day month list was given to Albert
      // so we need to filter out from the matching dates all the possible months containing unique days
      val filterOutUniqueMonths = matchingDates.filterNot(tuple => monthsOfUniqueDays.exists(_ == tuple._2))
      // in case length is 1 we found it
      filterOutUniqueMonths.length match {
        case 1 => {
          println("Bernard found: " + filterOutUniqueMonths.map { case ((k, v)) => k + ", " + v }.head)
          println("Bernard -> FoundWithHelp to App")
          sender ! FoundWithHelp
          context.stop(self)
        }
        case _ => {
          println("Bernard cannot find: " + filterOutUniqueMonths)
          println("Bernard -> No to App")
          sender ! No
        }
      }

    }

    case No =>
      println("Bernard received No")
  }
}

class Albert extends Actor {
  var matchingDates: List[(Int, String)] = Nil
  var allDates: List[(Int, String)] = Nil
  var sentOtherAlsoNo = false
  var inputMonth: String = ""

  def receive = {

    case StartAlbert(month, dates, bernard) => {
      println("Albert received Start")
      allDates = dates
      inputMonth = month
      // finds and saves the matching dates given the input month
      matchingDates = dates.filter(p => p._2 == month)
      matchingDates.length match {
        case 1 => {
          println("Albert found: " + matchingDates.map { case ((k, v)) => k + ", " + v }.head)
          println("Albert -> Found to Bernard")
          bernard ! Found
          context.stop(self)
        }
        case 0 => {
          println(s"Invalid Input not existing $month in input list ")
          println("Albert -> No to Bernard")
          bernard ! No
        }
        case _ => {
          println("Albert -> BothNo to Bernard")
          bernard ! BothNo
        }
      }
      //after the StartMessage processing, change receiving state
      context.become(waitResponseFormBernard, true)
    }


  }

  def waitResponseFormBernard: Receive = {
    case FoundWithHelp => {
      println("Albert received FoundWithHelp")
      val monthsOfUniqueDays = getMonthsHavingUniqueDay(allDates)
      // filter out months of unique days as Bernard found it but with help meaning it couldn't find it without help.
      val remainingValidDates = allDates.filterNot(tuple => monthsOfUniqueDays.exists(_ == tuple._2))
      val uniqueRemaining = remainingValidDates.groupBy(_._1).map { case (k, v) => (k, v.map(_._2)) }.filter(_._2.length == 1)
      // filter from matching dates only the remaining valid dates and if this is 1 then we found it
      matchingDates = matchingDates.filter(each => uniqueRemaining.exists(p => p._1 == each._1))
      if (matchingDates.length == 1)
        println("Albert found: " + matchingDates.map { case ((k, v)) => k + ", " + v }.head)
      else
        println("Albert cannot find it")
    }

    case Found => {
      println("Albert received Found")
      // should be unique since the other found it without help
      val found = allDates.groupBy(_._1).map { case (k, v) => (k, v.map(_._2)) }
        .filter(_._2.length == 1).filter(month => month._2.contains(inputMonth)).map { case (k, v) => k + ", " + v.head }

      if (found.size == 1) println(s"Albert found also ${found.head}")
    }

    case No =>
      println("Albert received No")

    case BothNo =>
      println("Albert received BothNo")

  }
}

