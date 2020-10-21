package Bank_App

import org.mongodb.scala.bson.ObjectId


case class AccountMember(
                          _id: ObjectId,
                          firstName: String,
                          lastName: String,
                          username: String,
                          password: String,
                          accntNum:Int,
                        acctBalance:Double) {
}

object AccountMember {

  def apply(firstName: String, lastName: String, username: String, password: String, accntNum: Int, acctBalance: Double): AccountMember = {
    AccountMember(
      new ObjectId(),
      firstName,
      lastName,
      username,
      password,
    accntNum,
    acctBalance)
  }
}
