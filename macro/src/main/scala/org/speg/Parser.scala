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

import scala.reflect.macros.Context

abstract class Parser {
  import Parser._
  import support._

  val EOI = '\uFFFF'

  implicit def str(s: String): Rule = `n/a`

  def input: ParserInput

  var _cursor: Int = 0
  def cursorChar() =
    if (_cursor == input.length) EOI
    else input charAt _cursor

  def advance(): Boolean =
    if (_cursor < input.length) {
      _cursor += 1
      true
    } else false

  def rule(r: Rule): Rule = macro ruleImpl
}

object Parser {
  type ParserContext = Context {type PrefixType = Parser}

  def ruleImpl(ctx: ParserContext)(r: ctx.Expr[Rule]): ctx.Expr[Rule] = {
    import ctx.universe._

    def render(tree: Tree) = tree match {
      case q"$a.this.str($s)" => q"""
          val p = ${ctx.prefix}
          var ix = 0
          while (ix < $s.length && $s.charAt(ix) == p.cursorChar()) {
            ix += 1
            p.advance()
          }
          Rule(ix == $s.length)
        """
      case x => ctx.abort(tree.pos, s"Unexpected expression: $tree")
    }

    ctx.Expr[Rule](render(r.tree))
  }
}