package htmloperations

import core.ItemsDomainModel

fun formatAsHtml(customSearchEngineResultDomainModel: ItemsDomainModel): String {
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