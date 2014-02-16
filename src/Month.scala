import java.util.Calendar
import java.util.GregorianCalendar


class Month(month: Int, year: Int) {
  /*Only load previous days when needed? Too much memory consumption
   loading all previous calendars? 
   what about large scale performance? More efficient method than Calendar objs?
   Contains the Day objects for all the days of the month
   Should parse json for days previous to the current date, 
   and leave future days null. 
  */
  val calendarObj = new GregorianCalendar(year, month, 1)
  val monthMaxDays:Int = calendarObj.getActualMaximum(Calendar.DAY_OF_MONTH);
  var daysOfTheMonth = new Array[Day](monthMaxDays)
  println(monthMaxDays + " days in the month of "+(month+1) )
  for (i <- 0 until daysOfTheMonth.length) {
    daysOfTheMonth(i) = new Day()
  }  
}