package image.getter.model

object UrlRepository {
    private val urls = mutableListOf<Url>()

    fun allUrl(): List<Url> = urls

    fun addUrl(url: Url) {
        urls.add(url)
    }
}