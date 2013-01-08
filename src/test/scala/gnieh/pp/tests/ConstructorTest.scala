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

/** @author Lucas Satabin
 *
 */
class ConstructorTest extends PpTest {

  "the text constructor" should "return the empty doc if the text is empty" in {
    text("") should be(EmptyDoc)
  }

  it should "return a text document with the text if not empty" in {
    text("hi") should be(TextDoc("hi"))
  }

  "the string constructor" should "split a text with new lines into lines separated by a line document" in {
    string("some line\nsome other one") should be("some line" :|: "some other one")
  }

  it should "return a simple text document if no new lines appear in the string" in {
    string("some line") should be(text("some line"))
  }
  
  "the char constructor" should "return the line document if the new line character is passed" in {
    char('\n') should be(line)
  }
  
  it should "return a text document if the character is not new line" in {
    char('a') should be(text("a"))
  }

}