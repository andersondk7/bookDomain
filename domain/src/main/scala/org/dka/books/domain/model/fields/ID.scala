package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.UUIDValidation

import java.util.UUID

final case class ID private (override val value: UUID) extends Field[UUID]

object ID extends UUIDValidation[ID] {

  override val fieldName: String = "ID"

  override def build(id: UUID): ID =
    new ID(id)

  def build: ID = new ID(UUID.randomUUID())

  def build(s: String) = new ID(UUID.fromString(s))

}
