package org.dka.books.domain.model.fields

/**
 * Container for a value of type T
 * @tparam T
 */
trait Field[T] {

  def value: T

}
