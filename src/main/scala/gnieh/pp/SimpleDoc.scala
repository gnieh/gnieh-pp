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

sealed trait SimpleDoc {

  def fits(width: Int): Boolean

  def layout: String

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