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

/** @author Lucas Satabin
 *
 */
class ListCombinatorsTest extends PpTest {

  val someText = words("text to lay out")

  "vsep" should "vertically lay out documents" in {
    val test1 = "some" :+: vsep(someText)
    render(test1) should be("some text\nto\nlay\nout")

    val test2 = "some" :+: align(vsep(someText))
    render(test2) should be("some text\n     to\n     lay\n     out")
  }

}