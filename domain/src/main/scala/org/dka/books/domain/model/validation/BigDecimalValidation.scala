package org.dka.books.domain.model.validation

import cats.data.Validated._
import cats.implicits.catsSyntaxValidatedIdBinCompat0
import io.circe._
import org.dka.books.domain.model.fields.Field

import scala.util.{Failure, Success, Try}

trait BigDecimalValidation[T <: Field[BigDecimal]] extends Validation[String, BigDecimal, T] {

  import Validation._

  def validate(string: String): ValidationErrorsOr[T] =
    Try(BigDecimal(string)) match {
      case Failure(t)    => InvalidNumberException(fieldName, string, t).invalidNec
      case Success(date) => Valid(build(date))
    }

  def toJson(item: T): (String, Json) = (fieldName, Json.fromString(item.value.toString))

  def fromOptionalJson(c: HCursor): ValidationErrorsOr[Option[T]] = {
    val result = for {
      value <- c.downField(fieldName).as[Option[String]]
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
