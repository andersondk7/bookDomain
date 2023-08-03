package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.StringLengthValidation

/**
 * country requirements:
 *   - can not be more than 40
 */
sealed abstract case class LocationAbbreviation private (override val value: String) extends Field[String]

object LocationAbbreviation extends StringLengthValidation[LocationAbbreviation] {

  override val maxLength = 4

  override val minLength = 1

  override val fieldName: String = "location_abbreviation"

  override def build(a: String): LocationAbbreviation = new LocationAbbreviation(a) {}

}
