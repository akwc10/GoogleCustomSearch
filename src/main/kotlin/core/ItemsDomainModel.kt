package core

import java.io.Serializable

data class ItemsDomainModel(val items: List<Item>) : Serializable {
    data class Item(
        val cacheId: String?,
        val displayLink: String,
        val formattedUrl: String,
        val htmlFormattedUrl: String,
        val htmlSnippet: String,
        val htmlTitle: String,
        val kind: String,
        val link: String,
        val pageMap: PageMap?,
        val snippet: String,
        val title: String
    ) : Serializable {
        data class PageMap(
            val metaTags: List<MetaTag>?,
            val place: List<Place>?,
            val cseImage: List<CseImage>?
        ) : Serializable {
            data class MetaTag(
                val referrer: String?,
                val google: String?,
                val viewport: String?,
                val ogTitle: String?
            )

            data class Place(
                val name: String,
                val image: String,
                val description: String
            )

            data class CseImage(
                val src: String
            )
        }
    }
}

fun ItemsDomainModel.formatAsHtml(): String {
    var html = """
        <!DOCTYPE html>
        <html>
    """.trimIndent()
    items.forEach { item ->
        html += """
            <p><a href="${item.link}">${item.htmlTitle}</a><br><font color="green">${item.htmlFormattedUrl}</font><br>${item.htmlSnippet}</p>
        """.trimIndent()
    }
    html += "</html>"
    return html
}