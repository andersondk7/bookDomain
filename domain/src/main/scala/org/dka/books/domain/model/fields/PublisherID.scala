package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.UUIDValidation

import java.util.UUID

final case class PublisherID private (override val value: UUID) extends Field[UUID]

object PublisherID extends UUIDValidation[PublisherID] {

  override val fieldName: String = "publisher_id"

  override def build(id: UUID): PublisherID = new PublisherID(id)

  def build: PublisherID = new PublisherID(UUID.randomUUID())

  def build(s: String): PublisherID = new PublisherID(UUID.fromString(s))

  def fromOpt(o: Option[String]): Option[PublisherID] = o.map(build)

}
