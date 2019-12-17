import api.CustomSearchEngineApiService
import api.customSearchEngineResultTO
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
    val customSearchEngineResultSingle =
        CustomSearchEngineApiService.createApiService().getCustomSearchEngineResult("monkey").retry(3).blockingGet()
    val customSearchEngineResultsDomainModel = customSearchEngineResultTO(customSearchEngineResultSingle)
//    customSearchEngineResultsDomainModel.items.forEachIndexed { index, item ->  println("$index: $item")}

    var html = """
        <!DOCTYPE html>
        <html>
    """.trimIndent()
    customSearchEngineResultsDomainModel.items.forEach { item ->
        html += """
            <p><a href="${item.link}">${item.htmlTitle}</a><br><font color="green">${item.htmlFormattedUrl}</font><br>${item.htmlSnippet}</p>
        """.trimIndent()
    }
    html += "</html>"
    val pathName = "${System.getenv("PWD")}/Output.htm"
    writeToFile(pathName, html)
    openFileInChrome(pathName)

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