package org.dka.books.domain.model.item

import io.circe.parser.decode
import io.circe.syntax._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import org.dka.books.domain.model.fields._

class LocationSpec extends AnyFunSpec with Matchers {

  describe("read and write from json") {
    it("with all fields") {
      val location = Location(
        ID.build,
        Version.defaultVersion,
        LocationName.build("Far Far Away"),
        LocationAbbreviation.build("FFA"),
        CountryID.build,
        CreateDate.now,
        UpdateDate.now
      )
      val json = location.asJson.noSpaces
      decode[Location](json) match {
        case Left(error)    => fail(error)
        case Right(decoded) => decoded shouldBe location
      }
    }
  }

}
