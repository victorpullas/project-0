package Bank_App

import org.mongodb.scala.MongoClient

import scala.io.{BufferedSource, Source}

object FileUtil {

  def getCSVContent (filename: String): Option[String] = {

    var openedFile: BufferedSource = null
    try{
      openedFile = Source.fromFile(filename)
      Some(openedFile.getLines().drop(1).mkString(" "))

    } finally {
      if(openedFile!=null) openedFile.close
    }

  }

  def readValuesAndInsertToDB(v:Option[String])={

    val value: String = v match {
      case None => ""
      case Some(s:String) => s
    }
    val b =value.split(",").map(_.toString).distinct
    val firstname = b(0).substring(1,b(0).length-1)
    val lastname = b(1).substring(1,b(1).length-1)
    val username = b(2).substring(1,b(2).length-1)
    val password = b(3).substring(1,b(3).length-1)
    val accntNum = b(4).substring(1,b(4).length-1).toInt

    //println(s"First Name: ${firstname}")
    //println(s"Last Name: ${lastname}")
    //println(s"Username: ${username}")
    //println(s"Password: ${password}")
    //println(s"accntNum: ${accntNum}")

    new CreateNewDoc(firstname,lastname,username,password,accntNum)

  }


}
