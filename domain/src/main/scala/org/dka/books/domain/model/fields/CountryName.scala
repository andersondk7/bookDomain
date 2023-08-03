package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.StringLengthValidation

/**
 * country requirements:
 *   - can not be more than 40
 */
sealed abstract case class CountryName private (override val value: String) extends Field[String]

object CountryName extends StringLengthValidation[CountryName] {

  override val maxLength = 40

  override val minLength = 1

  override val fieldName: String = "country_name"

  override def build(c: String): CountryName = new CountryName(c) {}

}
