package image.getter

import image.getter.model.Url

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun apiIsRunning() = testApplication {
        application {
            module()
        }

        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun newUrlCanBeAdded() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val url = Url("https://example.net")
        val response = client.post("/urls") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(url)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }
}