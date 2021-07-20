import java.sql.{Connection, DriverManager}
import scala.Console._

object databaseAttempt {
   def main(args:Array[String])= {
	//connect to database named "moviesTest" on default port of localhost
   val url = "jdbc:mysql://localhost/moviesTest?autoReconnect=true&useSSL=false"
   val driver = "com.mysql.cj.jdbc.Driver"
   val username = "root"
   val password = "venusawake"
   var connection: Connection = null
   try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT title, director2, release_year FROM movies ORDER BY title")
      val title = "TITLE"
      val director = "DIRECTOR"
      val year = "YEAR"
      println(s"${UNDERLINED}%-43s%-35s%-5s${RESET}".format(title,director,year))
      while(rs.next) {
         val title = rs.getString("title")
         val director = rs.getString("director2")
         val year = rs.getInt("release_year")
         println("Title: %-35s Director: %-25s Year: %d".format(title,director, year))

      }
}  catch {
      case e: Exception => e.printStackTrace
   }
   connection.close
}
}

