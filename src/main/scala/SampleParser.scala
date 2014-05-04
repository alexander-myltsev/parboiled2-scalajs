import scala.scalajs.js.annotation.JSExport
import org.speg._

class SampleParser(val input: ParserInput) extends Parser {
  def InputRule = rule { "ab" ~ ("c" | "d") }
}

@JSExport
object SampleParser {
  @JSExport
  def matchStr(s: String): Boolean =
    new SampleParser(s).InputRule.matched
}
