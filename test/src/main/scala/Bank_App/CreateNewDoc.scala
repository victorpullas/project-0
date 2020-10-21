package Bank_App
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}
import org.mongodb.scala.model.Filters.equal

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

class CreateNewDoc(firstname: String, lastname: String, username: String, password: String, accntNum :Int) {

  val codecRegistry = fromRegistries(fromProviders(classOf[AccountMember]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val mongoClient = MongoClient()
  val db = mongoClient.getDatabase("RevComBank").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[AccountMember] = db.getCollection("AccountHolder")


  private def getResults[T](obs: Observable[T]): Seq[T] = {

    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def getAll(): Seq[AccountMember] = getResults(collection.find())

  def getUser(username: String): Seq[AccountMember] = {
    getResults(collection.find(equal("username", username)))
  }

  def printResults[T](obs: Observable[T]): Unit = {
    getResults(obs).foreach(println(_))
  }

  printResults(collection.insertOne(AccountMember(firstname,lastname, username, password, accntNum, 0.0)))

}
