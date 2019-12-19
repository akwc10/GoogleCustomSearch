import api.CustomSearchEngineApiService
import api.getItemsTO
import fileoperations.openFileInChrome
import fileoperations.writeToFile
import htmloperations.formatAsHtml
import mu.KotlinLogging

/**
 * Command line app where you insert a query,
 * searches on google
 * gets the first result url
 * dumps the content into a html file
 * returns the filename to the user or opens the browser
 * with a presenter and a provider
 * use retrofit with rxjava adapter
 * https://developers.google.com/custom-search/v1/using_rest
TODO()
Very procedural
You need to invert your thinking
You don't order it to do something
You expect something and it pulls from the dependencies instead
Start from what you want to do and move to what you need
Rather than starting from what you need
And put that request single behind a nice function
 **/

private val logger = KotlinLogging.logger {}

fun main() {
    val quit = ":q"
    while (true) {
        println("Input query or $quit to exit")
        val query = readLine()
        if (query == quit) break
        if (!query.isNullOrEmpty()) runQuery(query)
    }
}

//TODO(Error handling)
private fun runQuery(query: String) =
    CustomSearchEngineApiService
        .createApiService()
        .getCustomSearchEngineResult(query)
        .doOnError { println("TODO Best practice???") }
        .retry(3)
        .subscribe { customSearchEngineResult ->
            val pathName = "${System.getenv("PWD")}/Output.htm"
            writeToFile(pathName, formatAsHtml(getItemsTO(customSearchEngineResult.items)))
            openFileInChrome(pathName)
        }
