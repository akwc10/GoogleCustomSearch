package chromeoperations

import api.CustomSearchEngineResult
import api.getItemsTo
import core.formatAsHtml
import fileoperations.openFileInChromeTab
import fileoperations.writeToFile

fun showResultsInChromeTab(customSearchEngineResult: CustomSearchEngineResult, pathName: String) {
    customSearchEngineResult.items.getItemsTo().formatAsHtml().writeToFile(pathName).openFileInChromeTab()
}