import api.CustomSearchEngineApiService
import api.CustomSearchEngineResult
import api.itemsTo
import core.formatAsHtml
import fileoperations.openFileInChromeTab
import fileoperations.writeToFile
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.io.IOException

class CustomSearchEngine {
    private val quit = ":q"
    private val pathName = "${System.getenv("PWD")}/Output.htm"
    private var input = ""
    private val subjectCSE: Subject<CustomSearchEngine> = PublishSubject.create()
    private lateinit var disposable: Disposable

    init {
        subjectCSE.subscribe {
            val apiService = CustomSearchEngineApiService.createApiService()
            disposable = runQuery(input, apiService, pathName)
        }
    }

    fun handleInput() {
        while (true) {
            println("Input query or $quit to exit")
            input = readLine().toString()
            if (input == quit) break
            if (input.isNotBlank()) {
                subjectCSE.onNext(this)
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
}