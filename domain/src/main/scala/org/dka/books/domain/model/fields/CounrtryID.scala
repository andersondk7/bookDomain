package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.UUIDValidation

import java.util.UUID

final case class CountryID private (override val value: UUID) extends Field[UUID]

object CountryID extends UUIDValidation[CountryID] {

  override val fieldName: String = "country_id"

  override def build(id: UUID): CountryID = new CountryID(id)

  def build: CountryID = new CountryID(UUID.randomUUID())

  def build(s: String) = new CountryID(UUID.fromString(s))

}
