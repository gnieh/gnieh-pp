/*
 * This file is part of the gnieh-pp project.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gnieh.pp

/** A pretty printer, that tries to make the document fit in the page width
 *
 *  @author Lucas Satabin
 */
class PrettyRenderer(width: Int) extends (Doc => SimpleDoc) {

  private type Docs = List[(Int, Doc)]

  def apply(doc: Doc) =
    best(width, 0, List((0, doc)))

  private def best(width: Int, column: Int, docs: Docs): SimpleDoc = docs match {
    case Nil =>
      SEmpty
    case (_, EmptyDoc) :: tail =>
      best(width, column, tail)
    case (i, ConsDoc(first, second)) :: tail =>
      best(width, column, (i, first) :: (i, second) :: tail)
    case (i, NestDoc(j, inner)) :: tail =>
      best(width, column, (i + j, inner) :: tail)
    case (i, TextDoc(text)) :: tail =>
      SText(text, best(width, column + text.length, tail))
    case (i, LineDoc(_)) :: tail =>
      SLine(i, best(width, i, tail))
    case (i, UnionDoc(l, s)) :: tail =>
      better(width, column,
        best(width, column, (i, l) :: tail),
        best(width, column, (i, s) :: tail))
    case (i, AlignDoc(inner)) :: tail =>
      best(width, column, (column + i, inner) :: tail)
    case (i, ColumnDoc(f)) :: tail =>
      best(width, column, (i, f(column)) :: tail)
  }

  private def better(width: Int, column: Int, d1: SimpleDoc, d2: SimpleDoc): SimpleDoc =
    if (d1.fits(width - column))
      d1
    else
      d2

}

/** This printer is not pretty (and thus faster): it does not insert any indentation, just renders everything compact
 *
 *  @author Lucas Satabin
 */
object CompactRenderer extends (Doc => SimpleDoc) {

  def apply(doc: Doc) =
    scan(0, List(doc))

  private def scan(column: Int, docs: List[Doc]): SimpleDoc = docs match {
    case Nil => SEmpty
    case doc :: docs => doc match {
      case EmptyDoc               => SEmpty
      case TextDoc(text)          => SText(text, scan(column + text.length, docs))
      case LineDoc(_)             => SLine(0, scan(0, docs))
      case ConsDoc(first, second) => scan(column, first :: second :: docs)
      case NestDoc(j, doc)        => scan(column, doc :: docs)
      case UnionDoc(_, short)     => scan(column, short :: docs)
      case AlignDoc(inner)        => scan(column, inner :: docs)
      case ColumnDoc(f)           => scan(column, (f(column)) :: docs)
    }
  }

}