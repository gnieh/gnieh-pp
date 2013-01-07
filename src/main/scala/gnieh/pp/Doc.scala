/*
* This file is part of the gnieh-pp project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package gnieh.pp

/** A document that can then be laid out.
 *
 *  @author Lucas Satabin
 */
sealed trait Doc {

  // for all operators, the empty document is a right and left unit
  private def withUnit(f: Doc => Doc)(that: Doc): Doc =
    if (this == EmptyDoc)
      that
    else if (that == EmptyDoc)
      this
    else
      f(that)

  /** Concatenates two documents.
   *  Is left associative with [[gnieh.pp.empty]] as left and right unit.
   */
  @scala.inline
  def ::(that: Doc): Doc =
    withUnit(ConsDoc(_, this))(that)

  /** Equivalent to `that :: space :: this` */
  @scala.inline
  def :+:(that: Doc): Doc =
    withUnit(_ :: space :: this)(that)

  /** Equivalent to `that :: line :: this` */
  @scala.inline
  def :|:(that: Doc): Doc =
    withUnit(_ :: line :: this)(that)

  /** Equivalent to `that :: softline :: this` */
  @scala.inline
  def :\:(that: Doc): Doc =
    withUnit(_ :: softline :: this)(that)

  /** Equivalent to `that :: linebreak :: this` */
  @scala.inline
  def :||:(that: Doc): Doc =
    withUnit(_ :: linebreak :: this)(that)

  /** Equivalent to `that :: softbreak :: this` */
  @scala.inline
  def :\\:(that: Doc): Doc =
    withUnit(_ :: softbreak :: this)(that)

  /** Equivalent to `align(this :|: that)` */
  @scala.inline
  def $$(that: Doc) =
    align(this :|: that)

  // internal stuffs used by the pretty printer algorithm

  val flatten: Doc

}

final case class NestDoc(indent: Int, inner: Doc) extends Doc {
  lazy val flatten =
    NestDoc(indent, inner.flatten)
}

final case class UnionDoc(long: Doc, short: Doc) extends Doc {
  val flatten =
    long.flatten
}

case object EmptyDoc extends Doc {
  val flatten =
    this
}

final case class TextDoc(text: String) extends Doc {
  val flatten =
    this
}

final case class LineDoc(break: Boolean) extends Doc {
  lazy val flatten =
    if (break) {
      TextDoc("")
    } else {
      TextDoc(" ")
    }
}

final case class ConsDoc(first: Doc, second: Doc) extends Doc {
  lazy val flatten =
    ConsDoc(first.flatten, second.flatten)
}

final case class AlignDoc(inner: Doc) extends Doc {
  lazy val flatten =
    AlignDoc(inner.flatten)
}

final case class FillDoc(width: Int, inner: Doc) extends Doc {
  lazy val flatten =
    FillDoc(width, inner.flatten)
}