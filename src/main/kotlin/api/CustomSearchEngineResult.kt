package api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomSearchEngineResult(
    @Json(name = "context")
    val context: Context,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "queries")
    val queries: Queries,
    @Json(name = "searchInformation")
    val searchInformation: SearchInformation,
    @Json(name = "url")
    val url: Url
) {
    @JsonClass(generateAdapter = true)
    data class Context(
        @Json(name = "title")
        val title: String
    )

    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "cacheId")
        val cacheId: String,
        @Json(name = "displayLink")
        val displayLink: String,
        @Json(name = "formattedUrl")
        val formattedUrl: String,
        @Json(name = "htmlFormattedUrl")
        val htmlFormattedUrl: String,
        @Json(name = "htmlSnippet")
        val htmlSnippet: String,
        @Json(name = "htmlTitle")
        val htmlTitle: String,
        @Json(name = "kind")
        val kind: String,
        @Json(name = "link")
        val link: String,
        @Json(name = "pagemap")
        val pageMap: PageMap,
        @Json(name = "snippet")
        val snippet: String,
        @Json(name = "title")
        val title: String
    ) {
        @JsonClass(generateAdapter = true)
        data class PageMap(
            @Json(name = "metatags")
            val metaTags: List<MetaTag>,
            @Json(name = "place")
            val place: List<Place>?,
            @Json(name = "cse_image")
            val cse_image: List<CseImage>?
        ) {
            @JsonClass(generateAdapter = true)
            data class MetaTag(
                @Json(name = "referrer")
                val referrer: String?,
                @Json(name = "google")
                val google: String?,
                @Json(name = "viewport")
                val viewport: String?,
                @Json(name = "og:title")
                val ogTitle: String?,
                @Json(name = "og:image")
                val ogImage: String?,
                @Json(name = "og:image:width")
                val ogImageWidth: String?,
                @Json(name = "og:image:height")
                val ogImageHeight: String?,
                @Json(name = "og:description")
                val ogDescription: String?,
                @Json(name = "og:site_name")
                val ogSiteName: String?,
                @Json(name = "twitter:card")
                val twitterCard: String?
            )

            @JsonClass(generateAdapter = true)
            data class Place(
                @Json(name = "name")
                val name: String,
                @Json(name = "image")
                val image: String,
                @Json(name = "description")
                val description: String
            )

            @JsonClass(generateAdapter = true)
            data class CseImage(
                @Json(name = "src")
                val src: String
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Queries(
        @Json(name = "nextPage")
        val nextPage: List<NextPage>,
        @Json(name = "request")
        val request: List<Request>
    ) {
        @JsonClass(generateAdapter = true)
        data class NextPage(
            @Json(name = "count")
            val count: Int,
            @Json(name = "cx")
            val cx: String,
            @Json(name = "inputEncoding")
            val inputEncoding: String,
            @Json(name = "outputEncoding")
            val outputEncoding: String,
            @Json(name = "safe")
            val safe: String,
            @Json(name = "searchTerms")
            val searchTerms: String,
            @Json(name = "startIndex")
            val startIndex: Int,
            @Json(name = "title")
            val title: String,
            @Json(name = "totalResults")
            val totalResults: String
        )

        @JsonClass(generateAdapter = true)
        data class Request(
            @Json(name = "count")
            val count: Int,
            @Json(name = "cx")
            val cx: String,
            @Json(name = "inputEncoding")
            val inputEncoding: String,
            @Json(name = "outputEncoding")
            val outputEncoding: String,
            @Json(name = "safe")
            val safe: String,
            @Json(name = "searchTerms")
            val searchTerms: String,
            @Json(name = "startIndex")
            val startIndex: Int,
            @Json(name = "title")
            val title: String,
            @Json(name = "totalResults")
            val totalResults: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class SearchInformation(
        @Json(name = "formattedSearchTime")
        val formattedSearchTime: String,
        @Json(name = "formattedTotalResults")
        val formattedTotalResults: String,
        @Json(name = "searchTime")
        val searchTime: Double,
        @Json(name = "totalResults")
        val totalResults: String
    )

    @JsonClass(generateAdapter = true)
    data class Url(
        @Json(name = "template")
        val template: String,
        @Json(name = "type")
        val type: String
    )
}
