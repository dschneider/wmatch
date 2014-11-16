import scalaj.http.Http
import scalaj.http.HttpOptions
import java.io.InputStreamReader
import net.liftweb.json._

object Wmatch {
  def main(args: Array[String]) {
    println("\nFetching latest words ...")

    val jsonWords = JsonParser.parse(fetchWords)
    val jsonElements = jsonWords.children

    println("\nThere are some words like: " + pretty(render(jsonElements(0))))
    val userInput = readLine("Enter a word you are looking for: ")

    println("\nMatched words: \n")
    for (word <- matchWords(sortWordStrings(jsonElements), userInput)) println("* " + word)
    println("-------------- \n")
  }

  def fetchWords() : String = {
    val connTimeout = HttpOptions.connTimeout(5000)
    val readTimeout = HttpOptions.readTimeout(5000)
    Http("https://myobie-wordy.herokuapp.com/words").option(connTimeout).option(readTimeout).asString
  }

  def sortWordStrings(jsonElements: List[net.liftweb.json.JsonAST.JValue]) : List[String] = {
    jsonElements.map(x => pretty(render(x))).sorted
  }

  def matchWords(words: List[String], userInput: String) : List[String] = {
    words.filter(x => x contains userInput)
  }
}
