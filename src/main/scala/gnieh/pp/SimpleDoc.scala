package gnieh.pp

sealed trait SimpleDoc {

  def fits(width: Int): Boolean

  val layout: String

  override def toString = layout

}

case object SEmpty extends SimpleDoc {
  def fits(width: Int) =
    width >= 0 // always fits if there is enough place

  val layout =
    ""

}

final case class SText(text: String, next: SimpleDoc) extends SimpleDoc {
  def fits(width: Int) =
    next.fits(width - text.length)

  lazy val layout =
    text + next.layout
}

final case class SLine(indent: Int, next: SimpleDoc) extends SimpleDoc {
  def fits(width: Int) =
    width >= 0 // always fits if there is enough place

  lazy val layout =
    if (next.layout.isEmpty)
      ""
    else
      "\n" + (" " * indent) + next.layout
}