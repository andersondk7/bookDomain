package org.dka.books.domain.model.fields

/**
 * Container for a value of type T Represent values that are guaranteed to be valid. For example:
 *   - an age instance can not be negative
 *   - a firstName can't be extremely long (no more than 50 characters for example)
 *
 * Implementations of fields follow the smart constructor pattern
 * [[https://medium.com/@supermanue/smart-constructors-in-scala-fa5a03e25326 smartConstructor]].
 *
 * The smart constructor pattern requires that construction is accomplished in the companion objects.
 *
 * These companion objects should extend a [[Validation]] type such as
 * [[org.dka.books.domain.model.validation.StringLengthValidation]] which provides methods for construction from
 *
 *   - untrusted sources (such as from json sent by a client)
 *
 *   - trusted sources, i.e. the source of truth for the field (such as the database where the field data resides)
 *
 * @tparam T
 */
trait Field[T] {

  def value: T

}
