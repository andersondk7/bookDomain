package org.dka.books.domain.model.item

import org.dka.books.domain.model.fields.CreateDate

/**
 * Represents a CRUD Item that can be created
 *
 * @tparam T
 *   item to be created
 */
trait Createable[T] {

  def createDate: CreateDate

}
