import api.CustomSearchEngineApiService
import chromeoperations.showResultsInChromeTab
import flowableoperations.addRetryWithBackOff
import io.reactivex.Observable
import java.io.IOException
import kotlin.system.exitProcess

class CustomSearchEngine {
    private val quit = ":q"
    private val pathName = "${System.getenv("PWD")}/Output.htm"

    init {
        while (true) {
            createInputObservable()
                .subscribe { input ->
                    val apiService = CustomSearchEngineApiService.createApiService()
                    apiService
                        .getCustomSearchEngineResult(input)
                        .toFlowable()
                        .addRetryWithBackOff()
                        .subscribe { customSearchEngineResult ->
                            showResultsInChromeTab(customSearchEngineResult, pathName)
                        }
                }
        }
    }

    private fun createInputObservable(): Observable<String> =
        Observable.create<String> { emitter ->
            println("Input query or $quit to exit")
            val input = readLine().toString()
            if (input != quit) emitter.onNext(input) else throw Exception()
        }.doOnError {
            exitProcess(0)
        }.filter { input -> input.isNotBlank() }

    private fun handleInputOld() {
        while (true) {
            createInputObservable()
                .subscribe { input ->
                    val apiService = CustomSearchEngineApiService.createApiService()
                    apiService
                        .getCustomSearchEngineResult(input)
                        .doOnError { throwable ->
                            println(if (throwable is IOException) "Network error" else throwable.message)
                        }
                        .retry(3)
                        .subscribe { customSearchEngineResult ->
                            showResultsInChromeTab(customSearchEngineResult, pathName)
                        }
                }
        }
    }
}
