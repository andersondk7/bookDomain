package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.UUIDValidation

import java.util.UUID

sealed abstract case class ID private (override val value: UUID) extends Field[UUID]

object ID extends UUIDValidation[ID] {

  override val fieldName: String = "ID"

  override def build(id: UUID): ID = new ID(id) {}

  def build: ID = build(UUID.randomUUID())

  def build(s: String): ID = new ID(UUID.fromString(s)) {}

}
