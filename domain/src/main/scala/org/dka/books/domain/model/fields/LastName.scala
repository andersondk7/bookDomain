package org.dka.books.domain.model.fields

import org.dka.books.domain.model.fields.LastName.fieldName
import org.dka.books.domain.model.validation.StringLengthValidation

/**
 * lastName requirements:
 *   - can't be empty
 *   - can not be more than 40
 */
final case class LastName private (override val value: String) extends Field[String]

object LastName extends StringLengthValidation[LastName] {

  override val maxLength = 40

  override val minLength = 1

  override val fieldName: String = "last_name"

  override def build(ln: String): LastName = new LastName(ln)

}
