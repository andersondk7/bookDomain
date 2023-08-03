package org.dka.books.domain.model.validation

import cats.data.Validated._
import cats.data.{NonEmptyChain, ValidatedNec}
import io.circe._
import org.dka.books.domain.model.fields.Field
import org.dka.books.domain.model.validation.Validation._

import scala.language.implicitConversions

/**
 * Validation of data for use in companion objects that follow the
 * [[https://medium.com/@supermanue/smart-constructors-in-scala-fa5a03e25326 smartConstructor]] pattern
 *
 * @tparam I
 *   source type of data
 * @tparam S
 *   type of data held in the item
 * @tparam T
 *   item from source type
 */
trait Validation[I, S, T <: Field[S]] {

  val fieldName: String

  /**
   * Constructor from previously validated input
   *
   * '''This should be used only from trusted sources'''
   *
   * It does not follow smart constructor pattern and does '''not''' check for valid data!
   *
   * @param data
   *   data held in the item
   * @return
   *   Item holding the data
   */
  def build(data: S): T

  /**
   * Constructor from previously validated input
   *
   * '''This should be used only from trusted sources'''
   *
   * It does not follow smart constructor pattern and does '''not''' check for valid data!
   *
   * @param data
   *   data held in the item
   * @return
   *   Item holding the data
   */
  def build(o: Option[S]): Option[T] = o.map(build)

  /**
   * Constructor for unvalidated data.
   *
   * This method should be used for untrusted sources. It follows the smart constructor pattern
   */
  def apply(s: I): ValidationErrorsOr[T] = validate(s)

  /**
   * smart constructor method checks for valid input should be used for untrusted sources
   */
  def apply(o: Option[I]): ValidationErrorsOr[Option[T]] = validateOption(o)

  /**
   * This method '''does''' check for valid data and should be used to construct from un-trusted sources such as the
   * clients outside of this library.
   */
  def validate(input: I): ValidationErrorsOr[T]

  /**
   * This method '''does''' check for valid data and should be used to construct from un-trusted sources such as the
   * clients outside of this library.
   */
  def validateOption(o: Option[I]): ValidationErrorsOr[Option[T]] = o match {
    case None => Valid(None)
    case Some(s) =>
      val validated = validate(s)
      validated.map(Some(_))
  }

  /**
   * Write the Item as json
   */
  def toJson(item: T): (String, Json)

  def toJson(item: Option[T]): Option[(String, Json)] = item.map(toJson)

  /**
   * Read the item from json This method will perform validation
   */
  def fromJson(c: HCursor): ValidationErrorsOr[T]

  def fromOptionalJson(c: HCursor): ValidationErrorsOr[Option[T]]

}

object Validation {

  type ValidationErrorsOr[T] = ValidatedNec[ValidationException, T]

  private def asList(errors: NonEmptyChain[ValidationException]): List[String] =
    errors.tail.foldLeft(List(errors.head.reason))((acc, ve) => ve.reason :: acc)

  implicit def asString(errors: NonEmptyChain[ValidationException]): String = asList(errors).mkString(" : ")

}
