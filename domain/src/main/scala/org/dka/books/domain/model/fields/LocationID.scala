package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.UUIDValidation

import java.util.UUID

sealed abstract case class LocationID private (override val value: UUID) extends Field[UUID]

object LocationID extends UUIDValidation[LocationID] {

  override val fieldName: String = "location_id"

  override def build(id: UUID): LocationID = new LocationID(id) {}

  def build(s: String): LocationID = new LocationID(UUID.fromString(s)) {}

  def fromOpt(opt: Option[String]): Option[LocationID] = opt.map(s => build(UUID.fromString(s)))

  def build: LocationID = build(UUID.randomUUID())

}
