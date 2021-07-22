import java.sql.{Connection, DriverManager}
import scala.Console._
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import java.sql.PreparedStatement
import java.sql.SQLException

object databaseAttempt {
   def main(args:Array[String])= {
   
      
 
   
	//connect to database named "moviesTest" on default port of localhost
   val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
   val driver = "com.mysql.cj.jdbc.Driver"
   val username = "root"
   val password = "venusawake"
   var connection: Connection = DriverManager.getConnection(url, username, password)
   //printWelcome
   printHeading
   var library = initialQuery(url, driver, username, password, connection)
   printMenu
   //for(e <- library) println(e) 
  // library foreach{row => row foreach print; println}
   //for(e <- library)(i <- e) print("%-35s %-5d %-10s %12s %-15s %-15s %-13s %-10s" format e)

   //val librarySorted = library.sortBy(_(1).asInstanceOf[Int])
   //print(librarySorted)
   var choice = scala.io.StdIn.readLine
   
   while(choice != "X" && choice != "x") { 
        
        mainMenu(choice)
        println
        println("Choose Again")
        choice = readLine
    }        
  
  
}

    def initialQuery(url:String, driver:String, userName:String, password:String, connection:Connection):List[List[Any]] = {
        var library = List[List[Any]]()
        try {
          Class.forName(driver)
          val statement = connection.createStatement
          val rs = statement.executeQuery("SELECT title, release_year, run_time, director1, director2, lang, genre, rating  FROM movies ORDER BY title")
          while(rs.next) {
             val title = rs.getString("title")
             val year = rs.getInt("release_year")
             val run_time = rs.getInt("run_time")+"m"        
             val director1 = rs.getString("director1")
             val director2 = rs.getString("director2")
             val language = rs.getString("lang")
             val genre = rs.getString("genre")
             val rating = rs.getString("rating")
             
             library = library :+ List(title, year, run_time, director1, director2, language, genre, rating)
             
             println("%-35s %-5d %-10s %12s %-15s %-15s %-13s %-10s".format(title,year, run_time,director1,director2,language,genre,rating))
             
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
        println("oooooo   oooooo     oooo oooooooooooo ooooo          .oooooo.     .oooooo.   ooo        ooooo oooooooooooo")
        println(" `888.    `888.     .8'  `888'     `8 `888'         d8P'  `Y8b   d8P'  `Y8b  `88.       .888' `888'     `8")
        println("  `888.   .8888.   .8'    888          888         888          888      888  888b     d'888   888         ")
        println("   `888  .8'`888. .8'     888oooo8     888         888          888      888  8 Y88. .P  888   888oooo8   ")
        println("    `888.8'  `888.8'      888    \"     888         888          888      888  8  `888'   888   888    \"   ")
        println("     `888'    `888'       888       o  888       o `88b    ooo  `88b    d88'  8    Y     888   888       o")
        println("      `8'      `8'       o888ooooood8 o888ooooood8  `Y8bood8P'   `Y8bood8P'  o8o        o888o o888ooooood8")
       
              Thread.sleep(2000)

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
       println(s"${YELLOW}A:  Add Movie\nD:  Delete Movie\nU:  Update Movie\nS:  Stat Options\nX:  Quit\n${RESET}\n\n")
       println;println
    }
    
    def mainMenu(action: String) = {
        action match {
            case "A" | "a" => addMovie
            case "D" | "d" => 
            case "U" | "u" => println("Update Movie")
            case "S" | "s" => println("Stats Page")
            case "X" | "x" => println("Exiting")
            case _ => println("Please Choose one of the options above")
        }
    }
    
    def addMovie = {
        println("Add Movie")
        print("Enter movie title: ")
        val title = scala.io.StdIn.readLine
        print("\nEnter movie release year: ")
        val year = scala.io.StdIn.readLine.toInt
        print("\nEnter movie run time: ")
        val run_time = scala.io.StdIn.readLine.toInt
        print("\nEnter director's first name: ")
        val director1 = scala.io.StdIn.readLine
        print("\nEnter director's last name: ")
        val director2 = scala.io.StdIn.readLine
        print("\nEnter movie's language: ")
        val lang = scala.io.StdIn.readLine
        print("\nEnter movie's genre: ")
        val genre = scala.io.StdIn.readLine
        print("\nEnter movie rating: ")
        val rating = scala.io.StdIn.readLine
        
        println("You entered the following movie data: ")
        println(s"Title: $title\nYear: $year\nRun Time: $run_time\nDirector Name: $director1 $director2\nLanguage: $lang\nGenre: $genre\nRating: $rating")
        println("Confirm Add? Y/N?")
        val choice = scala.io.StdIn.readLine
        choice match { 
            case "Y" | "y" => placeHolder(title,year,run_time,director1,director2,lang, genre,rating)
            case "N" | "n" => println; println; printMenu
            case _ => println ("Please enter Y or N")
        }
        
    }
    
    /*def addMovie(url:String, driver:String, userName:String, password:String, connection:Connection):Int = {
        6
    }*/
    
    def placeHolder(title:String,year:Int,run_time:Int,director1:String,director2:String,language:String, genre:String,rating:String) = {

    println("PLACEHOLDER")
        try {
            
            val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
            val driver = "com.mysql.cj.jdbc.Driver"
            val username = "root"
            val password = "venusawake"
            var connection: Connection = DriverManager.getConnection(url, username, password)
            Class.forName(driver)
            val statement = connection.createStatement
            val rs = statement.executeQuery("SELECT title, release_year, run_time, director1, director2, lang, genre, rating  FROM movies ORDER BY title")
            var query = "insert into movies ( title, release_year, run_time, director1, director2, lang, genre, rating ) values(?, ?, ?, ?, ?, ?, ?, ?)"
            
            val prepStatement:PreparedStatement  = connection.prepareStatement(query)
            prepStatement.setString(1, title)
            prepStatement.setInt(2, year)
            prepStatement.setInt(3, run_time)
            prepStatement.setString(4, director1)
            prepStatement.setString(5, director2)
            prepStatement.setString(6, language)
            prepStatement.setString(7, genre)
            prepStatement.setString(8, rating)
            
            prepStatement.execute
            prepStatement.close
            
            println; println; println("Movie has been added!")
            println("\n Reloading Database")
            Thread.sleep(1000)
            println;println;println
            printHeading
            initialQuery(url, driver, username, password, connection)
            
            printMenu
        }
        catch 
        {
            case e:SQLException => e.printStackTrace
        }
    }
    
    def printHeading() {
 
          val title = "TITLE"
          val director = "DIRECTOR"
          val year = "YEAR"
          val run_time = "RUN TIME"
          val language = "LANGUAGE"
          val genre = "GENRE"
          val rating = "RATING"
          println(s"${MAGENTA}${BOLD}${UNDERLINED}%-36s%-6s%-16s%12s%20s%13s %14s${RESET}".format(title,year,run_time,director,language, genre, rating))
    }
   }


