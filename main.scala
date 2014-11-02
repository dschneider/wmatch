import scalaj.http.Http
import scalaj.http.HttpOptions
import java.io.InputStreamReader
import net.liftweb.json._

object Wmatch {
  def main(args: Array[String]) {
    println("\nFetching latest words ...")

    val jsonWords = Http("https://myobie-wordy.herokuapp.com/words").option(HttpOptions.connTimeout(100000)){inputStream =>
      JsonParser.parse(new InputStreamReader(inputStream))
    }

     val jsonElements = (jsonWords).children
     val sortedWordStrings = jsonElements.map(x => (pretty(render(x)))).sorted
     println("\nThere are some words like: " + pretty(render(jsonElements(0))))
     val userInput = readLine("Enter a word you are looking for: ")
     val matchedWords = sortedWordStrings.filter(x => x contains userInput)

     println("\nMatched words: \n")
     for (word <- matchedWords) println("* " + word)
     println("-------------- \n")
  }
}
