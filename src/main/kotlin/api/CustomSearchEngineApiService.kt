package api

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomSearchEngineApiService {
    @GET("customsearch/v1?key=$CSE_API_KEY&cx=$CSE_API_CX")
    fun getCustomSearchEngineResult(
        @Query("q") q: String
    ): Single<CustomSearchEngineResult>

    companion object {
        private const val CSE_API_BASE_URL = "https://www.googleapis.com/"
        fun createApiService(): CustomSearchEngineApiService {
            return Retrofit.Builder()
                .baseUrl(CSE_API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(CustomSearchEngineApiService::class.java)
        }
    }
}
