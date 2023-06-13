package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.StringLengthValidation

/**
 * company name requirements:
 *   - can't be empty
 *   - can not be more than 40
 */

final case class PublisherName private (override val value: String) extends Field[String]

object PublisherName extends StringLengthValidation[PublisherName] {

  override val minLength: Int = 1

  override val maxLength: Int = 40

  override val fieldName: String = "publisher_name"

  override def build(cn: String): PublisherName = new PublisherName(cn)

}
