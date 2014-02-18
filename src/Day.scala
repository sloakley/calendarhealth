class Day {
  /* A day object containing health information for a particular day
   healthformatics - 
  	 exercize: boolean
  	 food: double; NB. ratio of good:junk foods - holistic self grading
  	 booze: boolean
  */
  var instantiated: Boolean = false
  private var exercise: Boolean = false
  private var food: Double = 0.00
  private var booze: Boolean = false
  private var last_update: String = "";
  
  def update(newExercise: Boolean, newFood: Double, newBooze: Boolean, time: String) { 
    this.instantiated = true
    this.exercise = newExercise
    this.food = newFood
    this.booze = newBooze
    this.last_update = time match {
      case "" => new java.util.Date().toString() // purposely 
      case _ => time // anything
    }
  } 
  
  def getHealth(): List[Any] =
    return List(this.exercise, this.food, this.booze, this.last_update)
  
  
}