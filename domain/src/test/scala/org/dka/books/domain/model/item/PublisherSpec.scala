package org.dka.books.domain.model.item

import com.typesafe.scalalogging.Logger
import io.circe.parser.decode
import io.circe.syntax._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import org.dka.books.domain.model.fields._
import org.dka.books.domain.model.item
import org.dka.books.domain.model.item.Publisher._

class PublisherSpec extends AnyFunSpec with Matchers {

  private val logger = Logger(getClass.getName)

  describe("read and write from json") {
    it("with all fields") {
      val publisher = Publisher(
        ID.build,
        Version.defaultVersion,
        PublisherName.build("Harper"),
        Some(LocationID.build),
        Some(WebSite.build("http://somehere.com")),
        CreateDate.now,
        UpdateDate.now
      )
      val json = publisher.asJson.noSpaces
      logger.debug(s"with all args: json: $json")
      decode[Publisher](json) match {
        case Left(error)    => fail(error)
        case Right(decoded) => decoded shouldBe publisher
      }
    }
    it("with optional fields") {
      val publisher = item.Publisher(
        ID.build,
        Version.defaultVersion,
        PublisherName.build("Harper"),
        None,
        None
      )
      val json = publisher.asJson.noSpaces
      logger.debug(s"with missing args: json: $json")
      decode[Publisher](json) match {
        case Left(error)    => fail(error)
        case Right(decoded) => decoded shouldBe publisher
      }
    }
  }

}
