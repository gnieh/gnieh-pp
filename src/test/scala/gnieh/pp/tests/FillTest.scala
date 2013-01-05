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

class FillTest extends FlatSpec with ShouldMatchers {

  val doc = string("some document")

  "a rendered document" should "be filled until filling width is reached" in {
    fill(20)(doc).render(80) should be("some document       ")
  }

  it should "not be filled if the width was already reached" in {
    fill(5)(doc).render(80) should be("some document")
  }

  it should "be filled even if there was indentation" in {
    nest(3)("test" #:: fill(20)(doc)).render(80) should be("test\n   some document       ")
  }

}