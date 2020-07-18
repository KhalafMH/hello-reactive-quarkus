package pro.mahdi

import io.smallrye.mutiny.Multi
import org.jboss.resteasy.annotations.SseElementType
import java.time.Duration
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
class ExampleResource {
    data class Message(val message: String)

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "hello"

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    @Path("/{count}/{name}")
    fun hello(@PathParam("count") count: Long, @PathParam("name") name: String): Multi<Message> = Multi.createFrom().ticks()
            .every(Duration.ofSeconds(1))
            .map { Message("Hello $name, $it") }
            .transform()
            .byTakingFirstItems(count)
}