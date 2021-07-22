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

    printWelcome
    printHeading
    var library = initialQuery(url, driver, username, password, connection)
    printMenu
    var choice = scala.io.StdIn.readLine

    while(choice != "X" && choice != "x") { 
        
        mainMenu(choice)
        println
        choice = readLine
    }       
    
    println;println;println
    printGoodbye
    Thread.sleep(2000)
  
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
    
    
    
    def printWelcome() = {
        for(a <- 1 to 35) println
        
        println(s"${YELLOW}")
        println("oooooo   oooooo     oooo oooooooooooo ooooo          .oooooo.     .oooooo.   ooo        ooooo oooooooooooo")
        println(" `888.    `888.     .8'  `888'     `8 `888'         d8P'  `Y8b   d8P'  `Y8b  `88.       .888' `888'     `8")
        println("  `888.   .8888.   .8'    888          888         888          888      888  888b     d'888   888         ")
        println("   `888  .8'`888. .8'     888oooo8     888         888          888      888  8 Y88. .P  888   888oooo8   ")
        println("    `888.8'  `888.8'      888    \"     888         888          888      888  8  `888'   888   888    \"   ")
        println("     `888'    `888'       888       o  888       o `88b    ooo  `88b    d88'  8    Y     888   888       o")
        println("      `8'      `8'       o888ooooood8 o888ooooood8  `Y8bood8P'   `Y8bood8P'  o8o        o888o o888ooooood8")
 
            println;println;println;println
            Thread.sleep(2000)
        
        println("    .             ")
        println("  .o8             ")
        println(".o888oo  .ooooo.  ")
        println("  888   d88' `88b ")
        println("  888   888   888 ")
        println("  888 . 888   888 ")
        println("  \"888\" `Y8bod8P' ")
        
            println;println;println;println  
            Thread.sleep(2000)

        println("8b        d8      ,ad8888ba,      88b           d88    88888888ba,      88888888ba   ")
        println(" Y8,    ,8P      d8\"'    `\"8b     888b         d888    88      `\"8b     88      \"8b  ")
        println("  Y8,  ,8P      d8'        `8b    88`8b       d8'88    88        `8b    88      ,8P  ")
        println("   \"8aa8\"       88          88    88 `8b     d8' 88    88         88    88aaaaaa8P'  ")
        println("    `88'        88          88    88  `8b   d8'  88    88         88    88\"\"\"\"\"\"8b,  ")
        println("     88         Y8,        ,8P    88   `8b d8'   88    88         8P    88      `8b  ")
        println("     88          Y8a.    .a8P     88    `888'    88    88      .a8P     88      a8P  ")
        println("     88           `\"Y8888Y\"'      88     `8'     88    88888888Y\"'      88888888P\"   ")
            
            println(s"${RESET}")
            println;println;println;println  
            Thread.sleep(2000)
            println("    *Your Own Movie Database")
            Thread.sleep(1000)
            println("    **Loading Movie List")
            Thread.sleep(3000)
            for(a <- 1 to 5) println
    }
    
    def printGoodbye = {
        for(a <- 1 to 35) println
        println(s"${YELLOW}")
        println("  .oooooo.                              .o8   .o8                             .o. ")
        println(" d8P'  `Y8b                            \"888  \"888                             888 ")
        println("888            .ooooo.   .ooooo.   .oooo888   888oooo.  oooo    ooo  .ooooo.  888 ")
        println("888           d88' `88b d88' `88b d88' `888   d88' `88b  `88.  .8'  d88' `88b Y8P ")
        println("888     ooooo 888   888 888   888 888   888   888   888   `88..8'   888ooo888 `8' ")
        println("`88.    .88'  888   888 888   888 888   888   888   888    `888'    888    .o .o. ")
        println(" `Y8bood8P'   `Y8bod8P' `Y8bod8P' `Y8bod88P\"  `Y8bod8P'     .8'     `Y8bod8P' Y8P ")
        println("                                                        .o..P'                    ")
        println("                                                        `Y8P'                     ")
        println(s"${RESET}")
    }
    
    def printMenu() = {
       println
       println(s"${BLUE}Scroll up to view contents or choose an option below:${RESET}")
       println(s"${YELLOW}A:  Add Movie\nD:  Delete Movie\nS:  Stat Options\nX:  Quit\n${RESET}\n")
    }
    
    def mainMenu(action: String) = {
        action match {
            case "A" | "a" => addMovie
            //case "C" | "c" => addFromCSV
            case "D" | "d" => deleteMovie
            case "U" | "u" => println("Update Movie")
            case "S" | "s" => statsPage
            case "X" | "x" => println("Exiting")
            case _ => println("Please Choose one of the options above")
        }
    }
    
    //Fill fields for Added movie and Call Dispatching method movetoDB
    def addMovie = {
        val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
        val driver = "com.mysql.cj.jdbc.Driver"
        val username = "root"
        val password = "venusawake"
        var connection: Connection = DriverManager.getConnection(url, username, password)
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
            case "Y" | "y" => movetoDB(title,year,run_time,director1,director2,lang, genre,rating)
            case "N" | "n" => { println(s"\n\n${RED}Operation Aborted. Returning to Home Screen${RESET}\n\n")
                                Thread.sleep(1000)
                                initialQuery(url, driver, username, password, connection)
                                println; println; printMenu
                                }
            case _ => println ("Please enter Y or N")
        }
        
    }
   
    //Save the added movie to the DB
    def movetoDB(title:String,year:Int,run_time:Int,director1:String,director2:String,language:String, genre:String,rating:String) = {

    
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
            
            println; println; println(s"${GREEN}Movie has been added!${RESET}")
            println
            println("\n Reloading Database")
            Thread.sleep(2500)
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
    
    //print DB Field Header
    def printHeading() {
 
        val title = "TITLE"
        val director = "DIRECTOR"
        val year = "YEAR"
        val run_time = "RUN TIME"
        val language = "LANGUAGE"
        val genre = "GENRE"
        val rating = "RATING"
        println(s"${BLUE}${BOLD}${UNDERLINED}%-36s%-6s%-16s%12s%20s%13s %14s${RESET}".format(title,year,run_time,director,language, genre, rating))
    }
    
    //Print Stats Page Menu
    def statsPage = {
        println
        println(s"${BLUE}Please Choose a Stat from the Options Below: ${RESET}")
        println(s"${YELLOW}1: Top Directors\n2: Top Years\n3: Top MPAA Ratings\n4: Top Genres\nM: Main Menu\n${RESET}")
        
        var choice = scala.io.StdIn.readLine
        
        choice match {
            case "1" | "2" | "3" | "4" | "M" | "m" => getStats(choice)
            case _ => println("Please Choose a Stat Above or M to Return to the Main Menu")
        }
        println
        
    }
    
    //Process Stat Choice 
    def getStats(choice: String) {
        
        
        try {
            
            val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
            val driver = "com.mysql.cj.jdbc.Driver"
            val username = "root"
            val password = "venusawake"
            var connection: Connection = DriverManager.getConnection(url, username, password)
            Class.forName(driver)
            val statement = connection.createStatement
            choice match {
                case "1" => {
                                println;println
                                println(s"${UNDERLINED}${BLUE}Top Directors${RESET}")
                                var query = "SELECT count(*) as number, director1, director2  from movies group by director2 order by number desc limit 5";
                                val rs = statement.executeQuery(query)
                                while(rs.next) {
                                    val count = rs.getInt("number")      
                                    val director1 = rs.getString("director1")
                                    val director2 = rs.getString("director2")
                                    println("%-12s %-20s %-3d".format(director1, director2, count))
                                }
                                statsPage
                            }
                             
                case "2" => {   
                                println;println
                                println(s"${UNDERLINED}${BLUE}Top Years${RESET}")
                                var query = "SELECT count(*) as number, release_year from movies group by release_year order by number desc limit 5"
                                val rs = statement.executeQuery(query)
                                while(rs.next) {
                                    val count = rs.getInt("number")
                                    val year = rs.getInt("release_year")
                                    println("%-8d  %-5d".format(year, count))  
                                }
                                statsPage                             
                }
                            
                case "3" => {
                                println;println
                                println(s"${UNDERLINED}${BLUE}Top Ratings${RESET}")
                                var query = "SELECT count(*) as number, rating from movies group by rating order by number desc limit 5"
                                val rs = statement.executeQuery(query)
                                while(rs.next) {
                                    val count = rs.getInt("number")
                                    val rating = rs.getString("rating")
                                    println("%-5s %-5d".format(rating, count)) 
                                }
                                statsPage
                }
                
                case "4" => {
                                println;println
                                println(s"${UNDERLINED}${BLUE}Top Genres${RESET}")
                                var query = "SELECT count(*) as number, genre from movies group by genre order by number desc limit 5"
                                val rs = statement.executeQuery(query)
                                while(rs.next) {
                                    val count = rs.getInt("number")
                                    val rating = rs.getString("genre")
                                    println("%-10s%-5d".format(rating, count))
                                }
                                statsPage
                }
                            
                case "M" | "m" => { 
                                println;println
                                printHeading; initialQuery(url, driver, username, password, connection);  
                                printMenu 
                }
                
                case _ => { 
                            println("Invalid Selection. Reloading DB")
                            initialQuery(url, driver, username, password, connection)
                }
            }
        }
        catch 
        {
            case e:SQLException => e.printStackTrace
        }
        
        
        
    }
    
    def deleteMovie() = {
        val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
        val driver = "com.mysql.cj.jdbc.Driver"
        val username = "root"
        val password = "venusawake"
        var connection: Connection = DriverManager.getConnection(url, username, password)
        print("Which movie would you like to delete? ")
        val title = scala.io.StdIn.readLine
        println(s"Confirm Delete $title? Y/N?")
        val choice = scala.io.StdIn.readLine
        choice match { 
            case "Y" | "y" => deleteFromDB(title)
            case "N" | "n" => { println(s"${RED}Operation Aborted. Returning to Home Screen${RESET}")
                                Thread.sleep(1000)
                                initialQuery(url, driver, username, password, connection)
                                println; println; printMenu
                                }
            case _ => println ("Please enter Y or N")
        }
        
    }
    def deleteFromDB(title:String) = {
        println; println;
        print("Which movie would you like to delete? ")
        val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
        val driver = "com.mysql.cj.jdbc.Driver"
        val username = "root"
        val password = "venusawake"
        var connection: Connection = DriverManager.getConnection(url, username, password)
        
        Class.forName(driver)
        val statement = connection.createStatement
        val rs = statement.executeQuery("SELECT title, release_year, run_time, director1, director2, lang, genre, rating  FROM movies ORDER BY title")
        var query = "Delete from movies WHERE title = ?"
        
        val prepStatement:PreparedStatement  = connection.prepareStatement(query)
        prepStatement.setString(1, title)
        prepStatement.execute
        prepStatement.close
            
            println; println; println(s"${GREEN}Movie has been DELETED!${RESET}")
            println
            println("\n Reloading Database")
            Thread.sleep(2500)
            println;println;println
            printHeading
            initialQuery(url, driver, username, password, connection)
            printMenu

    }
    
    /*def addFromCSV = {
        println
        print("Please enter the name of your .csv file: ")
        val fileName = scala.io.StdIn.readLine
    
    }*/
    //Abandoned Print Methods
    //for(e <- library) println(e) 
  // library foreach{row => row foreach print; println}
   //for(e <- library)(i <- e) print("%-35s %-5d %-10s %12s %-15s %-15s %-13s %-10s" format e)

   //val librarySorted = library.sortBy(_(1).asInstanceOf[Int])
   //print(librarySorted)
}


