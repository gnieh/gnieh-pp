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
    withUnit(ConsPp(_, this))(that)

  /** Equivalent to `that :: space :: this` */
  @scala.inline
  def +::(that: Doc): Doc =
    withUnit(_ :: space :: this)(that)

  /** Equivalent to `that :: line :: this` */
  @scala.inline
  def #::(that: Doc): Doc =
    withUnit(_ :: line :: this)(that)

  /** Equivalent to `that :: softline :: this` */
  @scala.inline
  def \::(that: Doc): Doc =
    withUnit(_ :: softline :: this)(that)

  /** Equivalent to `that :: linebreak :: this` */
  @scala.inline
  def ##::(that: Doc): Doc =
    withUnit(_ :: linebreak :: this)(that)

  /** Equivalent to `that :: softbreak :: this` */
  @scala.inline
  def \\::(that: Doc): Doc =
    withUnit(_ :: softbreak :: this)(that)

  /** Equivalent to `align(this <:> that)` */
  @scala.inline
  def $$(that: Doc) =
    align(this #:: that)

  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean): String

  /** Renders the document on the given width */
  def render(width: Int): String = render(width, 0, 0, false, false)

}

private[pp] final case class NestedDoc(indent: Int, inner: Doc) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) =
    inner.render(width, indent + this.indent, col, inGroup, newLine)
}

private[pp] final case class GroupDoc(inner: Doc) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) =
    inner.render(width, indent, col, true, newLine)
}

private[pp] case object EmptyDoc extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) =
    ""
}

private[pp] final case class TextDoc(text: String) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) =
    if (newLine) (" " * indent) + text
    else text
}

private[pp] final case class LineDoc(break: Boolean) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) =
    if (inGroup && break && col < width) {
      ""
    } else if (inGroup && !break && col + 1 < width) {
      " "
    } else {
      "\n"
    }
}

private[pp] final case class ConsPp(first: Doc, second: Doc) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) = {
    val firstRendered = first.render(width, indent, if (newLine) 0 else col, inGroup, newLine)
    val firstSize = firstRendered.length
    val secondRendered =
      second.render(width,
        indent,
        if (firstRendered.endsWith("\n")) 0 else (col + firstSize),
        inGroup,
        firstRendered.endsWith("\n"))
    if (inGroup) {
      if (secondRendered.length + firstSize <= width) {
        firstRendered + secondRendered
      } else {
        render(width, indent, col, false, newLine)
      }
    } else {
      firstRendered + secondRendered
    }

  }
}

private[pp] final case class AlignDoc(inner: Doc) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) = {
    inner.render(width, col, col, inGroup, newLine)
  }
}

private[pp] final case class FillDoc(width: Int, inner: Doc) extends Doc {
  private[pp] def render(width: Int, indent: Int, col: Int, inGroup: Boolean, newLine: Boolean) = {
    val rendered = inner.render(width, indent, col, inGroup, newLine)
    val spaces = if (newLine)
      this.width + indent - rendered.length
    else
      this.width - rendered.length
    rendered + (" " * spaces)
  }
}