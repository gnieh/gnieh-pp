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
package gnieh

import scala.collection.TraversableLike

/** Pretty-printer library */
package object pp {

  /** Indents the document */
  @scala.inline
  def nest(indent: Int)(inner: Doc): Doc =
    NestedDoc(indent, inner)

  /** Renders as a space */
  @scala.inline
  val space: Doc =
    TextDoc(" ")

  /** Renders as a new line unless it is discarded by a group, in which case behaves like [[gnieh.pp.space]] */
  @scala.inline
  val line: Doc = LineDoc(false)

  /** Renders as a new line unless it is discarded by a group, in which case behaves like [[gnieh.pp.empty]] */
  @scala.inline
  val linebreak: Doc =
    LineDoc(true)

  /** Behaves like [[gnieh.pp.space]] if the result fits in the page, otherwise behaves like [[gnieh.pp.line]] */
  @scala.inline
  val softline: Doc =
    group(line)

  /** Behaves like [[gnieh.pp.empty]] if the result fits in the page, otherwise behaves like [[gnieh.pp.line]] */
  @scala.inline
  val softbreak: Doc =
    group(linebreak)

  /** Renders as an empty string */
  @scala.inline
  val empty: Doc =
    EmptyDoc

  /** Renders the document with nesting level set to the current column */
  @scala.inline
  def align(doc: Doc): Doc =
    AlignDoc(doc)

  /** Renders the document with nesting level set to the current column plus `indent` */
  @scala.inline
  def hang(indent: Int)(doc: Doc): Doc =
    align(nest(indent)(doc))

  /** Renders the text as is. If it contains new lines, [[gnieh.pp.text]] should be used. */
  def text(s: String): Doc =
    TextDoc(s)

  /** Concatenates all characters, using [[gnieh.pp.line]] for new lines and [gnieh.pp.char]] for other characters */
  def string(s: String): Doc =
    s.foldRight(empty) { (c, acc) =>
      if (c == '\n')
        line :: acc
      else
        char(c) :: acc
    }

  /** Splits the string into words and create a document for each word */
  def words(s: String): List[Doc] =
    s.split("\\s+").map(text).toList

  @scala.inline
  def char(c: Char): Doc =
    TextDoc(c.toString)

  @scala.inline
  def int(i: Int): Doc =
    TextDoc(i.toString)

  @scala.inline
  def long(l: Long): Doc =
    TextDoc(l.toString)

  @scala.inline
  def float(f: Float): Doc =
    TextDoc(f.toString)

  @scala.inline
  def double(d: Double): Doc =
    TextDoc(d.toString)

  /** Discards all line breaks in the given document if the result fits in the page, otherwise, renders without any changes */
  def group(doc: Doc): Doc =
    if (doc == empty)
      empty
    else
      GroupDoc(doc)

  /** Renders the document as usual, and then fills until `width` with spaces if necessary */
  @scala.inline
  def fill(width: Int)(doc: Doc): Doc =
    FillDoc(width, doc)

  @scala.inline
  def hsep(docs: TraversableLike[Doc, _]): Doc =
    docs.foldRight(empty)(_ :+: _)

  @scala.inline
  def vsep(docs: TraversableLike[Doc, _]): Doc =
    docs.foldRight(empty)(_ :|: _)

  @scala.inline
  def fillSep(docs: TraversableLike[Doc, _]): Doc =
    docs.foldRight(empty)(_ :\: _)

  @scala.inline
  def sep(docs: TraversableLike[Doc, _]): Doc =
    group(vsep(docs))

  @scala.inline
  def hcat(docs: TraversableLike[Doc, _]): Doc =
    docs.foldRight(empty)(_ :: _)

  @scala.inline
  def vcat(docs: TraversableLike[Doc, _]): Doc =
    docs.foldRight(empty)(_ :|: _)

  @scala.inline
  def fillCat(docs: TraversableLike[Doc, _]): Doc =
    docs.foldRight(empty)(_ :\: _)

  @scala.inline
  def cat(docs: TraversableLike[Doc, _]): Doc =
    group(vcat(docs))

  implicit def s2doc(s: String) =
    if (s.contains("\n"))
      string(s)
    else
      text(s)

  @scala.inline
  implicit def i2doc(i: Int) =
    int(i)

  @scala.inline
  implicit def l2doc(l: Long) =
    long(l)

  @scala.inline
  implicit def f2doc(f: Float) =
    float(f)

  @scala.inline
  implicit def d2doc(d: Double) =
    double(d)

  @scala.inline
  implicit def c2doc(c: Char) =
    char(c)

  implicit def os2doc(o: Option[String]) = o match {
    case Some(s) => s2doc(s)
    case None    => empty
  }

  implicit def oi2doc(i: Option[Int]) = i match {
    case Some(i) => int(i)
    case None    => empty
  }

  implicit def ol2doc(l: Option[Long]) = l match {
    case Some(l) => long(l)
    case None    => empty
  }

  implicit def of2doc(f: Option[Float]) = f match {
    case Some(f) => float(f)
    case None    => empty
  }

  implicit def od2doc(d: Option[Double]) = d match {
    case Some(d) => double(d)
    case None    => empty
  }

  implicit def oc2doc(c: Option[Char]) = c match {
    case Some(c) => char(c)
    case None    => empty
  }

  implicit def odoc2doc(d: Option[Doc]) = d match {
    case Some(d) => d
    case None    => empty
  }

}