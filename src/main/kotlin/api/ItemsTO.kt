package api

import core.ItemsDomainModel

fun List<CustomSearchEngineResult.Item>.itemsTo() =
    ItemsDomainModel(items = map { item ->
        ItemsDomainModel.Item(
            cacheId = item.cacheId,
            displayLink = item.displayLink,
            formattedUrl = item.formattedUrl,
            htmlFormattedUrl = item.htmlFormattedUrl,
            htmlSnippet = item.htmlSnippet,
            htmlTitle = item.htmlTitle,
            kind = item.kind,
            link = item.link,
            pageMap = ItemsDomainModel.Item.PageMap(
                metaTags = item.pageMap?.metaTags?.map { metaTag ->
                    ItemsDomainModel.Item.PageMap.MetaTag(
                        referrer = metaTag.referrer,
                        google = metaTag.google,
                        viewport = metaTag.viewport,
                        ogTitle = metaTag.ogTitle
                    )
                },
                place = item.pageMap?.place?.map { place ->
                    ItemsDomainModel.Item.PageMap.Place(
                        name = place.name,
                        image = place.image,
                        description = place.description
                    )
                },
                cseImage = item.pageMap?.cse_image?.map { cseImage ->
                    ItemsDomainModel.Item.PageMap.CseImage(
                        src = cseImage.src
                    )
                }
            ),
            snippet = item.snippet,
            title = item.title
        )
    })
