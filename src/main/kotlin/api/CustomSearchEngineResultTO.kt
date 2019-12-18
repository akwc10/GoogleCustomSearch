package api

import core.CustomSearchEngineResultDomainModel

fun customSearchEngineResultTO(customSearchEngineResult: api.CustomSearchEngineResult): CustomSearchEngineResultDomainModel =
    CustomSearchEngineResultDomainModel(items = customSearchEngineResult.items.map { item ->
        CustomSearchEngineResultDomainModel.Item(
            cacheId = item.cacheId,
            displayLink = item.displayLink,
            formattedUrl = item.formattedUrl,
            htmlFormattedUrl = item.htmlFormattedUrl,
            htmlSnippet = item.htmlSnippet,
            htmlTitle = item.htmlTitle,
            kind = item.kind,
            link = item.link,
            pageMap = CustomSearchEngineResultDomainModel.Item.PageMap(
                metaTags = item.pageMap.metaTags.map { metaTag ->
                    CustomSearchEngineResultDomainModel.Item.PageMap.MetaTag(
                        referrer = metaTag.referrer,
                        google = metaTag.google,
                        viewport = metaTag.viewport,
                        ogTitle = metaTag.ogTitle
                    )
                },
                place = item.pageMap.place?.map { place ->
                    CustomSearchEngineResultDomainModel.Item.PageMap.Place(
                        name = place.name,
                        image = place.image,
                        description = place.description
                    )
                },
                cseImage = item.pageMap.cse_image?.map { cseImage ->
                    CustomSearchEngineResultDomainModel.Item.PageMap.CseImage(
                        src = cseImage.src
                    )
                }
            ),
            snippet = item.snippet,
            title = item.title
        )
    })