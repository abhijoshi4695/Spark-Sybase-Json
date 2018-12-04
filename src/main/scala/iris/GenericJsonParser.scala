package iris

import java.io.StringReader

import javax.json.Json
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event

//import scala.collection.mutable
import scala.io.Source

object GenericJsonParser {
  def main(args: Array[String]): Unit = {
    val fileContents: String = Source.fromFile("/home/abhishek/JsonSample/user.json").getLines.mkString

    val parser: JsonParser = Json.createParser(new StringReader(fileContents))
    var objectKey: String = ""
    var objectFlag: Boolean = false

//    var jsonFields = mutable.MutableList[(String, String, String)]()

    while (parser.hasNext) parser.next() match {
      case Event.START_OBJECT =>
        objectKey = ifArrayIncrement(objectKey)
        objectFlag = false
      case Event.END_OBJECT =>
        if(objectFlag)
          objectKey = postValue(objectKey)
        objectFlag = !objectFlag
      case Event.START_ARRAY =>
        if(objectKey != "")
          objectKey += ".0"
      case Event.END_ARRAY =>
        objectKey = postValue(objectKey)
        if (objectKey.length > 0 && objectKey.charAt(objectKey.length - 1) == '"')
          objectKey = postValue(objectKey)
      case Event.KEY_NAME =>
        if(objectKey == "")
          objectKey += "\"" + parser.getString + "\""
        else
          objectKey += ".\"" + parser.getString + "\""
      case Event.VALUE_STRING =>
        objectKey = ifArrayIncrement(objectKey)
        println(objectKey.replaceAll("\"", ""), parser.getString, "String")
//        jsonFields += ((objectKey.replaceAll("\"", ""), parser.getString, "String"))
        if(objectKey.length > 0 && !objectKey.charAt(objectKey.length - 1).isDigit)
          objectKey = postValue(objectKey)
      case Event.VALUE_NULL =>
        objectKey = ifArrayIncrement(objectKey)
        println(objectKey.replaceAll("\"", ""), "null", "String")
//        jsonFields += ((objectKey.replaceAll("\"", ""), "null", "String"))
        if(objectKey.length > 0 && !objectKey.charAt(objectKey.length - 1).isDigit)
          objectKey = postValue(objectKey)
      case Event.VALUE_NUMBER =>
        objectKey = ifArrayIncrement(objectKey)
        println(objectKey.replaceAll("\"", ""), parser.getBigDecimal.toString, "Number")
//        jsonFields += ((objectKey.replaceAll("\"", ""), parser.getBigDecimal.toString, "Number"))
        if(objectKey.length > 0 && !objectKey.charAt(objectKey.length - 1).isDigit)
          objectKey = postValue(objectKey)
      case Event.VALUE_TRUE =>
        objectKey = ifArrayIncrement(objectKey)
        println(objectKey.replaceAll("\"", ""), "True", "Boolean")
//        jsonFields += ((objectKey.replaceAll("\"", ""), "True", "Boolean"))
        if(objectKey.length > 0 && !objectKey.charAt(objectKey.length - 1).isDigit)
          objectKey = postValue(objectKey)
      case Event.VALUE_FALSE =>
        objectKey = ifArrayIncrement(objectKey)
        println(objectKey.replaceAll("\"", ""), "False", "Boolean")
//        jsonFields += ((objectKey.replaceAll("\"", ""), "False", "Boolean"))
        if(objectKey.length > 0 && !objectKey.charAt(objectKey.length - 1).isDigit)
          objectKey = postValue(objectKey)
      case _ => None
    }
    parser.close()

//    print(jsonFields.mkString("\n"))
  }

  def postValue(input: String): String = {
    val length = input.lastIndexOf(".")
    if (length == -1)
      ""
    else
      input.substring(0, length)
  }

  def ifArrayIncrement(input: String): String = {
    if(input.length == 0)
      ""
    else {
      if (input.charAt(input.length - 1) == '"')
        input
      else {
        val length = input.lastIndexOf(".")
        if (length == -1)
          return ""
        val key = input.substring(length + 1, input.length)
        if (key forall Character.isDigit)
          input.substring(0, length) + "." + (key.toInt + 1)
        else
          input
      }
    }
  }
}