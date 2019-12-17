package api

import core.CustomSearchEngineResultsDomainModel

fun customSearchEngineResultTO(customSearchEngineResult: api.CustomSearchEngineResult): CustomSearchEngineResultsDomainModel =
    CustomSearchEngineResultsDomainModel(items = customSearchEngineResult.items.map { item ->
        CustomSearchEngineResultsDomainModel.Item(
            cacheId = item.cacheId,
            displayLink = item.displayLink,
            formattedUrl = item.formattedUrl,
            htmlFormattedUrl = item.htmlFormattedUrl,
            htmlSnippet = item.htmlSnippet,
            htmlTitle = item.htmlTitle,
            kind = item.kind,
            link = item.link,
            pageMap = CustomSearchEngineResultsDomainModel.Item.PageMap(
                metaTags = item.pageMap.metaTags.map { metaTag ->
                    CustomSearchEngineResultsDomainModel.Item.PageMap.MetaTag(
                        referrer = metaTag.referrer,
                        google = metaTag.google,
                        viewport = metaTag.viewport,
                        ogTitle = metaTag.ogTitle
                    )
                },
                place = item.pageMap.place?.map { place ->
                    CustomSearchEngineResultsDomainModel.Item.PageMap.Place(
                        name = place.name,
                        image = place.image,
                        description = place.description
                    )
                },
                cseImage = item.pageMap.cse_image?.map { cseImage ->
                    CustomSearchEngineResultsDomainModel.Item.PageMap.CseImage(
                        src = cseImage.src
                    )
                }
            ),
            snippet = item.snippet,
            title = item.title
        )
    })