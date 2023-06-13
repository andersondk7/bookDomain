package org.dka.books.domain.model.validation

import cats.data.Validated._
import cats.implicits.catsSyntaxValidatedIdBinCompat0
import io.circe._
import org.dka.books.domain.model.fields.Field

import scala.util.{Failure, Success, Try}

trait PositiveIntegerValidation[T <: Field[Int]] extends Validation[Int, Int, T] {

  import Validation._

  def validate(i: Int): ValidationErrorsOr[T] =
    if (i <= 0) {
      val msg = s"$i must be greater than 0"
      InvalidNumberException(fieldName, msg, new IllegalArgumentException(msg)).invalidNec
    } else Valid(build(i))

  def toJson(item: T): (String, Json) = (fieldName, Json.fromInt(item.value))

  def fromJson(
    c: HCursor
  ): ValidationErrorsOr[T] = {
    val value: Either[DecodingFailure, Int] = for {
      i <- c.downField(fieldName).as[Int]
    } yield i
    value.fold(
      df => JsonParseException(df).invalidNec,
      input => validate(input) // convert string to Item, converting ValidationException to DecodingFailure
    )
  }

  def fromOptionalJson(c: HCursor): ValidationErrorsOr[Option[T]] = {
    val result = for {
      value <- c.downField(fieldName).as[Option[Int]]
    } yield value
    result.fold(
      df => JsonParseException(df).invalidNec,
      {
        case None => Valid(None)
        case Some(value) =>
          validate(value) match {
            case Invalid(ve)    => Invalid(ve)
            case Valid(decoded) => Valid(Some(decoded))
          }
      }
    )
  }

}
