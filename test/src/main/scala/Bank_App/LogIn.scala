package Bank_App

import org.mongodb.scala.MongoClient
import scala.io.StdIn

class LogIn {

  def logInCredentials(): Unit = {
    println("*************************************************")
    print("[Username]: ")
    var user = StdIn.readLine()
    print("[Password]: ")
    var pass = StdIn.readLine()
    println("*************************************************")
    println(s"You enetered: Username=${user}, Password=${pass}")
    println("Here are the existing accounts:")
    loginDBConnection(user, pass)
  }


  def loginDBConnection(username: String, password: String)= {

    val client = MongoClient()
    val accountMemberDao = new accountMemberDao(client)

    //println(accountMemberDao.getAll()) // PRINTS : List(AccountMember(5f890284ff733c76884493c5,John,Appleseed,john.app,pass123))
    //Finds accounts with firstname as a filter

    val exists = accountMemberDao.checkUserExists(username)

    if (exists) {
      // CHECKS IF PASSWORD MATCHES THE ACCOUNT USERNAME IN THE DATABASE
      val correctInfo = accountMemberDao.checkPassword(username, password)

      if(correctInfo)
      {
        println("Log In was successful!...")
        accountMemberDao.viewAccount(username,password)
      }
    }
    else
    {
      println("Sorry this account does not seem to exist. Redirecting you to the home screen...")
      val start = new Start
      start.startApp()
    }
  }
}
