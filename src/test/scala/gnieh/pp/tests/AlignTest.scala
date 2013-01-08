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

class AlignTest extends PpTest {

  val doc1 = text("hi")
  val doc2 = text("nice")
  val doc3 = text("world")

  "the align operator" should "align worlds on the current column" in {

    render(doc1 :+: (doc2 || doc3)) should be("hi nice\n   world")

  }

}