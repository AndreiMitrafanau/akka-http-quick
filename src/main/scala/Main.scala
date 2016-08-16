import akka.actor.ActorSystem
import akka.actor.Status.Status
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

object Main extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val formats =
    Serialization.formats(FullTypeHints(List(classOf[Status])))

  val route =
    get {
      path("") {
        complete {
          """<html>
            <body>
              <h1>Say hello to
                <i>akka-http</i>
                !</h1>
            </body>
          </html>"""
        }
      } ~
        path("book" / IntNumber) { bookId =>
          complete {
            val book: Book = Book(bookId, "Default title")
            pretty(render(Extraction.decompose(book)))
          }
        } ~
        path("book" / "add") {
          parameters('bookId, 'name) { (Id, name) =>
            val bookId = Id.toInt
            val title = name
            val book = Book(bookId, title)
            print(book)
            complete("Book Added!")
          }
        }
    } ~
      // curl -X POST  -H "Content-Type: application/json" --data '{ "bookId" : '9', "title" : "John" }' localhost:8080/Json
      post {
        path("Json") {
          entity(as[String]) { json =>
            val book = parse(json).extract[Book]
            println("BookId: " + book.bookId)
            println("name: " + book.title)
            complete(StatusCode.int2StatusCode(200))
          }
        }
      }

  Http().bindAndHandle(route, "localhost", 8080)
}

case class Book(bookId: Int, title: String)
