//first scala project
//just get the basics etc
// specify simple types? Let scala type-inference different classes/objs
// such as Calendars?

/* projected handling 
     UI - Front end calendar web ui
     			\    /
      			 \  /
      			  ||
      			 /  \
      		  Server cnxn
      		  /        \
*/


//import com.github.nscala_time.time.Imports._
import java.text.DateFormat
import java.util.TimeZone
import java.io.File
import java.io.FileReader
import scala.util.parsing.json
import scala.collection.mutable.ArrayBuffer
import java.util.GregorianCalendar
import scala.util.Random
import scala.util.matching.Regex
//import scala.collection.parallel.Foreach
//import rapture.io


class MyCalendar {
  // Create all twelve months, but with empty Day objects
  // months prior to the boot date will load from json
  // future months remain null? Saves memory
  var monthsOfTheYear = new Array[Month](12)
  println("Creating new Calendar")
  for (i <- 0 until monthsOfTheYear.length) {
    monthsOfTheYear(i) = new Month(i, 2014)
  }  
}


class Person {
  var yearsCalendars = Map[String, MyCalendar]() //ArrayBuffer[MyCalendar]() 
  println("Creating new Person")
}


object main {
   
  def getFilesPaths(path:String): Array[java.io.File] = new File(path).listFiles() 
  
  def readJSON(fl:String) {
    println("parsing json of "+fl)
    println(fl.toString())
    var readString = scala.io.Source.fromFile(fl.toString()).mkString
    var parsed = json.JSON.parseRaw(readString)
    if (!parsed.isEmpty) { // check to see if it's a json file
      val jsonMap = // this holds all the entire map of the individual's json file
      		json.JSON.parseFull(parsed.get.toString()).get.
       		asInstanceOf[Map[String,Map[String, Map[String, Map[String, String]]]]]
      //get the username from the filename "/a/b/c/name.JSON"
      var username = (fl.substring(fl.lastIndexOf("/")+1,fl.lastIndexOf(".JSON"))).toString
      //add the username to the user map
      users++=Map(username -> new Person())
      
      for (year <- jsonMap.keys) {
        users{username}.yearsCalendars ++= Map(year -> new MyCalendar())             
        for (month <- jsonMap{year}.keys) 
          for (day <- jsonMap{year}{month}.keys) {
            users{username}.yearsCalendars{year}.monthsOfTheYear(
                month match { // case match within the function call parameters
                  case "January" => 0   case "February" => 1
                  case "March" => 2     case "April" => 0
                  case "May" => 0       case "June" => 0
                  case "July" => 0      case "August" => 0
                  case "September" => 0 case "October" => 0
                  case "November" => 0  case "December" => 0
                }).daysOfTheMonth(day.toInt-1).
                update(jsonMap{year}{month}{day}{"exercise"}.toLowerCase.toBoolean, 
                    jsonMap{year}{month}{day}{"food"}.toLowerCase.toDouble, 
                    jsonMap{year}{month}{day}{"booze"}.toLowerCase.toBoolean, 
                    jsonMap{year}{month}{day}{"last_update"})
                // update the particular user's map
          } 
      }
    }
  }
  
  def handleJSON(path:String) {
    var filesPaths:Array[java.io.File] = getFilesPaths(path)
    for (fp <- filesPaths) {
      readJSON(fp.toString())
    }
  }
  
  var users = Map[String, Person]()

  def main(args: Array[String]) {
	println("ayy lmao")
	var indCal = new MyCalendar() 
	println(new java.util.Date().toString())
	handleJSON("/Users/lucas/Projects/calendarhealth/users")
	println(users.keys)
	println(users{"Lucas"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(0).getHealth())
	users{"Lucas"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(0).update(false, 0.00, true, "")
	println(users{"Lucas"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(0).getHealth())
  }
}