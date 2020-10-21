package Bank_App

import scala.io.StdIn

/**
 * Start holds the methods necessary to traverse the
 * logic of the program and runs it with startApp().
 */

class Start {
  def startApp(): Unit = {
    var continueLoop = true
    while (continueLoop)
    {
      startOptions()
      //Gets user input to either Log In, Sign Up, or Exit
      StdIn.readLine() match {
        case a if (a.equalsIgnoreCase ("log in") || a.equalsIgnoreCase ("login"))=> {
          println("Redirecting you to the log in option...")
          val login = new LogIn
          login.logInCredentials() //changed to use LogIn class
          continueLoop = false
        }

        case b if (b.equalsIgnoreCase ("sign up"))=> {
          println("Redirecting you to the sign up option...")
          val signup = new SignUp
          signup.signUpCredentials()
          continueLoop = false
        }
        case e if (e.equalsIgnoreCase ("exit"))=> {
          println("Goodbye! Hope you have a nice day!")
          continueLoop = false
        }
        case notRecognized => {
          println(s"${notRecognized} is not valid please try again:   ")
        }
      }
    }
  }
  def startOptions(): Unit = {
    println("****** Welcome to Revature Community Bank  ******")
    println("*************************************************")
    println("******              [Log In]               ******")
    println("******             [Sign Up]               ******")
    println("******              [Exit]                 ******")
    println("*************************************************")
    print("What would you like to do?:   ")
  }
}