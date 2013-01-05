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
class OperatorUnitTest extends FlatSpec with ShouldMatchers {

  val doc1 = text("doc1")
  val doc2 = text("doc2")

  "empty" should "be a left unit element for <>" in {
    empty <> doc1 should be(doc1)
  }

  it should "be a right unit element for <>" in {
    doc1 <> empty should be(doc1)
  }

  it should "be a left unit element for <+>" in {
    empty <+> doc1 should be(doc1)
  }

  it should "be a right unit element for <+>" in {
    doc1 <+> empty should be(doc1)
  }

  it should "be a left unit element for <:>" in {
    empty <:> doc1 should be(doc1)
  }

  it should "be a right unit element for <:>" in {
    doc1 <:> empty should be(doc1)
  }

  it should "be a left unit element for <::>" in {
    empty <::> doc1 should be(doc1)
  }

  it should "be a right unit element for <::>" in {
    doc1 <::> empty should be(doc1)
  }

  it should """be a left unit element for <\>""" in {
    empty <\> doc1 should be(doc1)
  }

  it should """be a right unit element for <\>""" in {
    doc1 <\> empty should be(doc1)
  }

  it should """be a left unit element for <\\>""" in {
    empty <\\> doc1 should be(doc1)
  }

  it should """be a right unit element for <\\>""" in {
    doc1 <\\> empty should be(doc1)
  }

}