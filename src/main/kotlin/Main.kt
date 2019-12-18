import api.CustomSearchEngineApiService
import api.customSearchEngineResultTO
import core.CustomSearchEngineResultDomainModel
import mu.KotlinLogging
import java.io.File

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

    while (true) {
        println("Input query or $quit to exit")
        val query = readLine()
        if (query == quit) break
        if (!query.isNullOrEmpty()) {
            writeToFile(pathName, formatHtml(customSearchEngineResultDomainModel(query)))
            openFileInChrome(pathName)
        }
    }
}

private fun customSearchEngineResultDomainModel(query: String) =
    customSearchEngineResultTO(customSearchEngineResultSingle(query))

//TODO(Error handling)
//TODO(Do not use blockingGet(). Imperative!)
private fun customSearchEngineResultSingle(query: String) =
    CustomSearchEngineApiService
        .createApiService()
        .getCustomSearchEngineResult(query)
//        .doOnError()
        .retry(3)
        .blockingGet()

private fun formatHtml(customSearchEngineResultDomainModel: CustomSearchEngineResultDomainModel): String {
    var html = """
        <!DOCTYPE html>
        <html>
    """.trimIndent()
    customSearchEngineResultDomainModel.items.forEach { item ->
        html += """
            <p><a href="${item.link}">${item.htmlTitle}</a><br><font color="green">${item.htmlFormattedUrl}</font><br>${item.htmlSnippet}</p>
        """.trimIndent()
    }
    html += "</html>"
    return html
}

private fun openFileInChrome(pathName: String) {
    Runtime.getRuntime().exec(
        arrayOf(
            "/usr/bin/open",
            "-a",
            "/Applications/Google Chrome.app",
            pathName
        )
    )
}

fun writeToFile(pathName: String, fileContent: String) {
    File(pathName).bufferedWriter().use { out -> out.write(fileContent) }
}