package org.dka.books.domain.model.item

import org.dka.books.domain.model.fields.{ID, UpdateDate, Version}

/**
 * Represents a CRUD Item that can be updated
 *
 * @tparam T
 *   item to be updated
 */
trait Updatable[T] {

  def id: ID

  def version: Version

  def lastUpdate: Option[UpdateDate]

  /**
   * change the values in the item that should be updated (typically the updateDate and version
   */
  def update: T

}
