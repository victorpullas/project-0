package Bank_App

import org.mongodb.scala.MongoClient

class ManageAccounts
{

  def depositToAccount(user: String, balance: Double,dep: Double) = {

    println ("Processing your transaction...")
    //println (s"username: ${user}")
    //println (s"balance: ${balance}")
    //println (s"deposit: ${dep}")

    new updateBalance(user, balance,dep)

  }

  def withdrawFromAccount(user: String, balance: Double,withdraw: Double) = {

    println ("Processing your transaction...")
    //println (s"username: ${user}")
    //println (s"balance: ${balance}")
    //println (s"withdraw: ${withdraw}")

    if (withdraw>balance) { println("Sorry there is not enough funds in your bank account. Please try again")}
    else {new updateBalance(user, balance, -(withdraw))}
  }


}
