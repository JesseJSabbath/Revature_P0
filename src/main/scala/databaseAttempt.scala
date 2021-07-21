import java.sql.{Connection, DriverManager}
import scala.Console._
import scala.collection.mutable.ArrayBuffer
import scala.io.Source


object databaseAttempt {
   def main(args:Array[String])= {
   
      
 
   
	//connect to database named "moviesTest" on default port of localhost
   val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
   val driver = "com.mysql.cj.jdbc.Driver"
   val username = "root"
   val password = "venusawake"
   var connection: Connection = DriverManager.getConnection(url, username, password)
   printWelcome
   var library = initialQuery(url, driver, username, password, connection)
   printMenu
   //for(e <- library) println(e) 
  // library foreach{row => row foreach print; println}
   //for(e <- library) println(e)
   //print(library)
  
  
}

    def initialQuery(url:String, driver:String, userName:String, password:String, connection:Connection):List[List[Any]] = {
        var library = List[List[Any]]()
        try {
          Class.forName(driver)
          val statement = connection.createStatement
          val rs = statement.executeQuery("SELECT title, release_year, run_time, director1, director2, lang, genre  FROM movies ORDER BY title")
          val title = "TITLE"
          val director = "DIRECTOR"
          val year = "YEAR"
          val run_time = "RUN TIME"
          val language = "LANGUAGE"
          val genre = "GENRE"
          
          
          
          
          println(s"${MAGENTA}${BOLD}${UNDERLINED}%-36s%-6s%-16s%12s%20s%13s${RESET}".format(title,year,run_time,director,language, genre))
          while(rs.next) {
             val title = rs.getString("title")
             val year = rs.getInt("release_year")
             val run_time = rs.getInt("run_time")+"m"        
             val director1 = rs.getString("director1")
             val director2 = rs.getString("director2")
             val language = rs.getString("lang")
             val genre = rs.getString("genre")
             
             library = library :+ List(title, year, run_time, director1, director2, language, genre)
             
             println("%-35s %-5d %-10s %12s %-15s %-15s %-10s".format(title,year, run_time,director1,director2,language,genre))
             println
          }
        }  catch {
          case e: Exception => e.printStackTrace
        }

        connection.close
        library
    }
    
    def printDatabase(): Unit = {
        println()
    }
    
    def cat(filename: String) = using(Source.fromFile(filename)) { source => {
        for (line <- source.getLines) println(line)
    }}
    
    def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }
    
    def printWelcome() = {
       for(a <- 1 to 35) println
       cat("./src/resources/welcome")
       Thread.sleep(2000)
       cat("./src/resources/to")
       Thread.sleep(2000)

       cat("./src/resources/yomdb")
       Thread.sleep(1500)
       cat("./src/resources/yomdb2")

       Thread.sleep(2000)
       cat("./src/resources/yomdb3")
       
       Thread.sleep(3000)
       for(a <- 1 to 5) println
    }
    
    def printMenu() = {
       println
       println(s"${MAGENTA}Scroll up to view contents or choose an option below:${RESET}")
       println(s"${YELLOW}A:  Add Movie\nD:  Delete Movie\nU:  Update Movie\nS:  Stat Options${RESET}\n\n")
       println;println
    }
}

