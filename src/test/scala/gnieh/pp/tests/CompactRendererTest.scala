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
class CompactRendererTest extends PpTest {

  val doc = "this" :|: "is" :|: "some" :|: "document" :|: "with" :|: "lines"

  val grouped = group("this" :|: "is" :|: "a" :|: "grouped" :|: "document")

  val nested = nest(4) { "this" :|: "one" :|: "has" :|: "indentation" }

  val aligned = "this" :+: align("document" :|: "is" :|: "aligned")

  "the compact renderer" should "flatten the entire document" in {
    compact(doc) should be("this is some document with lines")
  }

  it should "ignore groups" in {
    compact(grouped) should be("this is a grouped document")
  }

  it should "ignore nesting" in {
    compact(nested) should be("this one has indentation")
  }

  it should "ignore alignment" in {
    compact(aligned) should be("this document is aligned")
  }

}
