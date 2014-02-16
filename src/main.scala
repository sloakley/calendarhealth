//first scala project
//just get the basics etc
// specify simple types? Let scala type-inference different classes/objs
// such as Calendars?


/*
 * NOTES
 * 	Janet.JSON wont be parsed correctly till I remove the // comments 
 *  readJSON() seems to handle all the non json files it reads correctly.
 *  	If it can't parse em, it creates an empty json object. It checks to see 
 *   	if the object is empty before attempting to 'parse' it. 
 *  Doesn't currently write to JSON upon program shutdown. Need to implement..
 */
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


class MyCalendar(year:Int) {
  // Create all twelve months, but with empty Day objects
  // months prior to the boot date will load from json
  // future months remain null? Saves memory
  var monthsOfTheYear = new Array[Month](12)
  println("Creating new Calendar")
  for (i <- 0 until monthsOfTheYear.length) {
    monthsOfTheYear(i) = new Month(i, year)  // FIX THIS. YEAR SHOULDNT BE A PARAM
  }  
}


class Person() {
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
        users{username}.yearsCalendars ++= Map(year -> new MyCalendar(year.toInt))             
        for (month <- jsonMap{year}.keys) 
          for (day <- jsonMap{year}{month}.keys) {
            users{username}.yearsCalendars{year}.monthsOfTheYear(
                month match { // case match within the function call parameters
                  case "January" => 0   case "February" => 1
                  case "March" => 2     case "April" => 3
                  case "May" => 4       case "June" => 5
                  case "July" => 6      case "August" => 7
                  case "September" => 8 case "October" => 9
                  case "November" => 10  case "December" => 11
                }).daysOfTheMonth(day.toInt).
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
	//var indCal = new MyCalendar() 
	println(new java.util.Date().toString())
	handleJSON("/Users/lucas/Projects/calendarhealth/users")
	println(users.keys)
	println(users{"Lucas"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(0).getHealth())
	users{"Lucas"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(0).update(false, 0.00, true, "")
	println(users{"Lucas"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(0).getHealth())
	println(users{"Jesus"})
	println(users{"Jesus"}.yearsCalendars) // should print a map
	println(users{"Jesus"}.yearsCalendars{"2013"}.monthsOfTheYear(10).daysOfTheMonth(0).getHealth())
	println(users{"Jesus"}.yearsCalendars{"2013"}.monthsOfTheYear(11).daysOfTheMonth(0).getHealth())

	println(users{"Jesus"}.yearsCalendars{"2014"}.monthsOfTheYear(0).daysOfTheMonth(1).getHealth())
	//should print out Jesus's exciting junk food ridden 420, in the year 1AD
	println(users{"Jesus"}.yearsCalendars{"1"}.monthsOfTheYear(3).daysOfTheMonth(20).getHealth())

  }
}