package gnieh.pp
package tests

import org.scalatest._

abstract class PpTest extends FlatSpec with ShouldMatchers {

  val compact = CompactRenderer.andThen(_.layout)

  val render80 = (new PrettyRenderer(80)).andThen(_.layout)

  val render20 = (new PrettyRenderer(20)).andThen(_.layout)

  val render10 = (new PrettyRenderer(10)).andThen(_.layout)

}
