package org.dka.books.domain.model.item

import cats.data.Validated._
import cats.implicits._
import io.circe._
import org.dka.books.domain.model.fields.{CreateDate, ID, LocationID, PublisherName, UpdateDate, Version, WebSite}
import org.dka.books.domain.model.validation.Validation._

final case class Publisher(
  override val id: ID,
  override val version: Version,
  publisherName: PublisherName,
  locationId: Option[LocationID],
  webSite: Option[WebSite],
  override val createDate: CreateDate = CreateDate.now,
  override val lastUpdate: Option[UpdateDate] = None)
  extends Updatable[Publisher]
    with Createable[Publisher] {

  override def update: Publisher = this.copy(version = version.next, lastUpdate = UpdateDate.now)

}

object Publisher {

  implicit val encodePublisher: Encoder[Publisher] = (p: Publisher) => {
    val objects = List(
      Some(ID.toJson(p.id)),
      Some(Version.toJson(p.version)),
      UpdateDate.toJson(p.lastUpdate),
      Some(PublisherName.toJson(p.publisherName)),
      LocationID.toJson(p.locationId),
      WebSite.toJson(p.webSite),
      Some(CreateDate.toJson(p.createDate))
    ).flatten
    Json.obj(objects: _*)
  }

  implicit val decodePublisher: Decoder[Publisher] = (c: HCursor) =>
    (
      ID.fromJson(c),
      Version.fromJson(c),
      PublisherName.fromJson(c),
      LocationID.fromOptionalJson(c),
      WebSite.fromOptionalJson(c),
      CreateDate.fromJson(c),
      UpdateDate.fromOptionalJson(c)
    ).mapN(Publisher.apply) match {
      case Invalid(errors)  => Left(DecodingFailure(errors, Nil))
      case Valid(publisher) => Right(publisher)
    }

}
