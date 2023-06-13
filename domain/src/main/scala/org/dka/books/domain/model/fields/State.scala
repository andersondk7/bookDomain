package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.StringLengthValidation

/**
 * state requirements:
 *   - must be 2 characters
 */

final case class State private (override val value: String) extends Field[String]

object State extends StringLengthValidation[State] {

  val maxLength = 2

  val minLength = 2

  val fieldName: String = "state"

  def build(s: String): State = new State(s)

}
