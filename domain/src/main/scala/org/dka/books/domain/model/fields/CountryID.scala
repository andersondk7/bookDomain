package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.UUIDValidation

import java.util.UUID

sealed abstract case class CountryID private (override val value: UUID) extends Field[UUID]

object CountryID extends UUIDValidation[CountryID] {

  override val fieldName: String = "country_id"

  override def build(id: UUID): CountryID = new CountryID(id) {}

  def build: CountryID = build(UUID.randomUUID())

  def build(s: String): CountryID = new CountryID(UUID.fromString(s)) {}

}
