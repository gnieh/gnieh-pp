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
package tests

import org.scalatest._

class LineReplacementTest extends PpTest {

  val doc = group("function1(i1, i2)" :: lineOr("; ") :: "function2(i3)")

  "the replacement text" should "be used if the document fits on one line" in {
    render80(doc) should be("function1(i1, i2); function2(i3)")
  }

  "a new line" should "be used if the result does not fit on one line" in {
    render10(doc) should be("function1(i1, i2)\nfunction2(i3)")
  }

}
