/*
 * Copyright (C) 2014 Alexander Myltsev
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

package org.speg

trait ParserInput {
  def charAt(ix: Int): Char
  def length: Int
  def sliceString(start: Int, end: Int): String
}

object ParserInput {
  implicit def apply(string: String): StringBasedParser = new StringBasedParser(string)

  class StringBasedParser(string: String) extends ParserInput {
    def charAt(ix: Int) = string.charAt(ix)
    def length = string.length
    def sliceString(start: Int, end: Int): String = string.substring(start, end)
  }
}
