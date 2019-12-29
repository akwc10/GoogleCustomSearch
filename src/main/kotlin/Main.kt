import api.CustomSearchEngineApiService
import api.CustomSearchEngineResult
import api.itemsTo
import core.formatAsHtml
import fileoperations.openFileInChromeTab
import fileoperations.writeToFile
import io.reactivex.disposables.Disposable
import mu.KotlinLogging
import java.io.IOException

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
    val pathName = "${System.getenv("PWD")}/Output.htm"
    lateinit var disposable: Disposable

    while (true) {
        println("Input query or $quit to exit")
        val query = readLine()
        if (query == quit) break
        if (!query.isNullOrEmpty()) {
            val apiService = CustomSearchEngineApiService.createApiService()
            disposable = runQuery(query, apiService, pathName)
        }
    }
    tearDown(disposable)
}

private fun runQuery(query: String, apiService: CustomSearchEngineApiService, pathName: String) =
    apiService
        .getCustomSearchEngineResult(query)
        .doOnError { throwable -> println(if (throwable is IOException) "Network error" else throwable.message) }
        .retry(3)
        .subscribe { customSearchEngineResult ->
            showResultsInChromeTab(customSearchEngineResult, pathName)
        }

private fun showResultsInChromeTab(customSearchEngineResult: CustomSearchEngineResult, pathName: String) {
    customSearchEngineResult.items.itemsTo().formatAsHtml().writeToFile(pathName).openFileInChromeTab()
}

private fun tearDown(disposable: Disposable) {
    if (!disposable.isDisposed) disposable.dispose()
}
