package org.jetbrains.plugins.scala
package codeInspection.methodSignature

import com.intellij.codeInspection._
import org.intellij.lang.annotations.Language
import org.jetbrains.plugins.scala.lang.psi.api.statements.ScFunction
import org.jetbrains.plugins.scala.extensions._
import org.jetbrains.plugins.scala.lang.psi.ScalaPsiElement
import quickfix.AddEmptyParentheses

/**
 * Pavel Fatin
 */

class JavaMutatorMethodOverridenAsParameterlessInspection extends AbstractMethodSignatureInspection(
  "ScalaJavaMutatorMethodOverridenAsParameterless", "Java mutator method overriden as parameterless") {

  def actionFor(holder: ProblemsHolder) = {
    case f: ScFunction if f.isParameterless =>
      f.superMethods.headOption match { // f.superMethod returns None for some reason
        case Some(_: ScalaPsiElement) => // do nothing
        case Some(method) if method.isMutator =>
          holder.registerProblem(f.nameId, getDisplayName, new AddEmptyParentheses(f))
        case _ =>
      }
  }
}