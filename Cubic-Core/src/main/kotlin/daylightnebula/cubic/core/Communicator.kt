package daylightnebula.cubic.core

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.net.ServerSocket
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

abstract class Communicator {

    val json = Json {
        ignoreUnknownKeys = true
    }

    private lateinit var server: NettyApplicationEngine
    var port by Delegates.notNull<Int>()
        private set

    // abstracts
    abstract val metadata: CubicServerMetadata?
    abstract fun receivedReady(address: String, metadata: CubicServerMetadata)

    // start the server when required
    fun start(port: Int) {
        this.port = port
        server = embeddedServer(Netty, module = module, port = port)
        server.start(wait = false)
        println("Created Communicator on port $port!")
    }

    // stop the server when required
    fun stop() {
        server.stop(100, 100)
        println("Stopped Communicator!")
    }

    // sets up endpoints of this KTOR server
    private val module: Application.() -> Unit = {
        routing {
            // if metadata requests, return the given metadata
            get("/metadata") {
                val response = metadata?.let { json.encodeToString(it) } ?: "{}"
                this.call.respondText(response)
            }

            // handle ping requests
            get("/ping") {
                // decode ping request
                val requestString = this.call.request.queryParameters["request"] ?: "{\"time\":${System.currentTimeMillis()}}"
                val request = json.decodeFromString<PingRequest>(requestString)

                // respond
                val response = PingResponse(request.time, System.currentTimeMillis())
                this.call.respondText(json.encodeToString(response))
            }

            // handle ready requests
            get("/ready") {
                // decode request
                val requestString = this.call.request.queryParameters["request"] ?: "{\"time\":${System.currentTimeMillis()}}"
                val request = json.decodeFromString<CubicServerMetadata>(requestString)

                // call ready function
                receivedReady(this.call.request.origin.remoteAddress, request)

                // empty response
                this.call.respondText("{}")
            }
        }
    }

    // quick requests
    fun requestMetadata(address: String) =
        request<CubicServerMetadata>(address, "metadata", "{}")
    fun ping(address: String) =
        request<PingResponse>(address, "ping", json.encodeToString(PingRequest(System.currentTimeMillis())))
    fun ready(address: String) =
        request<JsonObject>(address, "ready", metadata?.let { json.encodeToString(it) } ?: "{}")

    // make a request
    inline fun <reified T: Any> request(
        address: String,
        path: String,
        request: String
    ): CompletableFuture<Result<T>> = CompletableFuture.supplyAsync {
        runBlocking {
            // attempt to send request
            val response = try {
                client.get("http://$address/$path", block = {
                    parameter("request", request)
                })
            } catch (ex: Exception) {
                return@runBlocking Result.failure<T>(ex)
            }

            // read response
            val result: T =
                try { json.decodeFromString(response.bodyAsText()) }
                catch (ex: Exception) { return@runBlocking Result.failure(ex) }

            // return success
            Result.success(result)
        }
    }

    // add client to companion object
    companion object {
        val client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }
    }
}

fun getEmptyPort(): Int {
    val socket = ServerSocket(0)
    val port = socket.localPort
    socket.close()
    return port
}
