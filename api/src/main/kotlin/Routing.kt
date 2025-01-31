package image.getter

import image.getter.model.*
import image.getter.model.Url
import image.getter.model.Picture
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }
        route("/urls") {
            get {
                val urls = UrlRepository.allUrl()
                call.respond(urls)
            }
            post {
                try {
                    val urlObject = call.receive<Url>()
                    val trueUrl = urlObject.url
                    UrlRepository.addUrl(urlObject)
                    val client = HttpClient()
                    val response = client.get(trueUrl) {
                        headers {
                            append(HttpHeaders.Accept, "image/*")
                        }
                    }
                    val body = response.bodyAsText()
                    val parsedData = mutableListOf<Picture>()
                    val imgPattern = "<img.*?/>".toRegex()
                    val picturePattern = "<picture.*?>.*?</picture>".toRegex()
                    val imgResult = imgPattern.findAll(body)
                    val pictureResult = picturePattern.findAll(body)
                    for (matchResult in imgResult) {
                        parsedData.add(Picture(matchResult.value))
                    }
                    for (item in pictureResult) {
                        parsedData.add(Picture(item.value))
                    }
                    call.respond(HttpStatusCode.Created,parsedData)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }

    }
}
