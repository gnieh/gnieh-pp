package gnieh.pp
package tests

import org.scalatest._

abstract class PpTest extends FlatSpec with ShouldMatchers {

  val render = (new PrettyRenderer(80)).andThen(_.layout)

}