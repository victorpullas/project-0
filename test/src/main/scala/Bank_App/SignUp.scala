package Bank_App

import java.io.{BufferedWriter, FileNotFoundException, FileWriter}

import au.com.bytecode.opencsv.CSVWriter

import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.collection.JavaConverters._
import scala.util.Random

class SignUp {

  def signUpCredentials(): Unit = {
    var loop = true
    while (loop) {
      println("*************************************************")
      println("Please fill out his form to process your application:")
      print("[First Name]: ")
      var firstName = StdIn.readLine()
      print("[Last Name]: ")
      var lastName = StdIn.readLine()
      print("[UserName]: ")
      var user = StdIn.readLine()
      print("[Password]: ")
      var pass = StdIn.readLine()
      println("*************************************************")
      println(s"You enetered:" +
        s"\nFirst Name...... ${firstName} " +
        s"\nLast Name....... ${lastName} " +
        s"\nUsername........ ${user} " +
        s"\nPassword........ ${pass} ")
      print("Is this information correct? (yes/no) : ")

      StdIn.readLine() match {
        case y if y.equalsIgnoreCase("yes") => {


          println("Thank you, your application is being processed!")
          //Creates account based on information passed to csv file.

          createRegistrationFile(firstName, lastName, user, pass)


          loop = false
        }
        case n if n.equalsIgnoreCase("no") => {
          println("Please try again! ")
        }
        case notRecognized => {
          println(s"${notRecognized} is not valid please try again:   ")
          //
        }
      }
    }
  }

  def createRegistrationFile(firstName: String, lastName: String, username: String, password: String): Unit = {
    val outPutFile = new BufferedWriter(new FileWriter("testFile.csv"))
    val CSVWriter = new CSVWriter(outPutFile)
    val csvFields = Array("firstName", "lastName", "username", "password", "accountNum", "balance")
    val in = Array(firstName, lastName, username, password, s"${(new Random()).nextInt(10000)}" ,"" )
    val listOfRecords = new ListBuffer[Array[String]]()
    listOfRecords += csvFields
    listOfRecords += in

    CSVWriter.writeAll(listOfRecords.toList.asJava)
    outPutFile.close()

    try {
      //println(FileUtil.getCSVContent("testFile.csv"))
      val text = FileUtil.getCSVContent("testFile.csv")
      //println(text)
      FileUtil.readValuesAndInsertToDB(text)
    }
    catch {
      case fnf: FileNotFoundException => println("Failed to find file")
    }
    println("Your application has successfully been processed! Redirecting you to the home page...")
    val start = new Start
    start.startApp()
  }

  def openAccount(): String = {

    println("*************************************")
    println("*           [CHECKINGS]             *")
    println("*            [SAVINGS]              *")
    println("*             [BOTH]                *")
    println("*             [NONE]                *")
    println("*************************************")
    println("Which account would you like to open?")

    var loop = true
      var opt = ""
      while (loop) {
        StdIn.readLine() match {
          case c if c.equalsIgnoreCase("checkings") => {
            opt = "checkings"
            loop = false
          }
          case s if s.equalsIgnoreCase("savings") => {
            opt = "savings"
            loop = false
          }
          case b if b.equalsIgnoreCase("both") => {
            opt = "both"
            loop = false
          }
          case n if n.equalsIgnoreCase("none") => {
            opt = "none"
            loop = false
          }
          case notValid =>
            println("Sorry not a valid entry please try again!")
        }
      }
    opt
  }
}
