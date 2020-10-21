package Bank_App

import javax.print.attribute.standard.PrinterLocation
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.model.Filters._

import scala.io.StdIn
import scala.util.control.Exception

class accountMemberDao(mongoClient: MongoClient) {

  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[AccountMember]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val db: MongoDatabase = mongoClient.getDatabase("RevComBank").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[AccountMember] = db.getCollection("AccountHolder")


  private def getResults[T](obs: Observable[T]): Seq[T] = {

    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def getAll(): Seq[AccountMember] = getResults(collection.find())

  def getUser(username: String): Seq[AccountMember] = {
    getResults(collection.find(equal("username", username)))
  }


  def checkUserExists(username: String): Boolean = {
    //println(getResults(collection.find(equal("username", username))))
    try {
      print("Did the query come back empty? :")
      println(getUser(username).isEmpty)


      if (getUser(username).isEmpty) {
        (false)
      }
      else {
        //println("value in db: ")
        val userinDB = getUser(username)(0).username
        //println(userinDB)
        //println("value passed:")
        //println(username)
        (userinDB == username)
      }
    }
    catch {
      case e: Exception => println(e.printStackTrace())
        (false)
    }
  }

  def checkPassword(username: String, password: String): Boolean = {

    val correctPass = getUser(username)(0).password

    if (correctPass == password) {
      true
    }
    else {
      println("Incorrect password...")
      println("Would you like to try again?: (Y/N)")
      var loop = true

      val prog = new LogIn
      while (loop) {
        StdIn.readChar() match {
          case a if (a == 'Y' || a == 'y') => {
            prog.logInCredentials()
            loop = false
          }
          case b if (b == 'N' || b == 'n') => {
            println("Goodbye")
            loop = false
          }
          case notFound => {
            println(s"${notFound} is not a valid entry. Please try again.")
          }
        }
      }
      false
    }
  }

  def viewAccount(username: String, password: String): Unit = {

    val firstName = getUser(username)(0).firstName
    val balance = getUser(username)(0).acctBalance
    val accntNum = getUser(username)(0).accntNum
    val user = getUser(username)(0).username
    val pass = getUser(username)(0).password
    println(s"Welcome back, ${firstName}!")
    println("====================================================")
    println(s"REVATURE ACCOUNT (${accntNum}) ............ ${balance} USD")
    println("=====================OPTIONS========================")
    println("= Deposit............(1)     Withdraw.........(2)  =")
    println("= Log Out ...........(3)     Log Out and Exit.(4)  =")

    println("How can we help you today?: ")
    var loop = true
    while (loop) {
      StdIn.readLine().toInt match {

        case a if (a.toInt == 1) => {
          println("How much money would you like to deposit? :")
          val dep = StdIn.readLine().toDouble
          val acc = new ManageAccounts
          acc.depositToAccount(username, balance, dep)

          viewAccount(user, pass)
          loop = false
        }

        case a if (a.toInt == 2) => {
          println("How much money would you like to withdraw? :")
          // TODO: withdrawChoice()

          val withdraw = StdIn.readLine().toDouble
          val acc = new ManageAccounts
          acc.withdrawFromAccount(username, balance, withdraw)

          viewAccount(user, pass)

          loop = false
        }

        case a if (a.toInt == 3) => {
          println("Redirecting you to our Home Menu...")
          val home = new Start
          home.startApp()

          loop = false
        }
        case a if (a.toInt == 4) => {
          println("Have a nice day!")

          loop = false
        }

        case notRecognized => {
          println(s"${notRecognized} is not valid please try again:   ")

        }
      }
    }
  }
}