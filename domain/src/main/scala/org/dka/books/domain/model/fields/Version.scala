package org.dka.books.domain.model.fields

import cats.implicits.catsSyntaxValidatedIdBinCompat0
import io.circe.{DecodingFailure, HCursor}
import org.dka.books.domain.model.validation.Validation.ValidationErrorsOr
import org.dka.books.domain.model.validation.{JsonParseException, PositiveIntegerValidation}

/**
 * version requirements:
 *   - can't be empty
 *   - must be positive
 */
sealed abstract case class Version private (override val value: Int) extends Field[Int] {

  def next: Version = Version.build(this.value + 1)

}

object Version extends PositiveIntegerValidation[Version] {

  override val fieldName: String = "version"

  val defaultVersion: Version = build(1)

  override def build(value: Int): Version = new Version(value) {}

}
