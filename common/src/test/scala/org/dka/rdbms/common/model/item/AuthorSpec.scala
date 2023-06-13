package org.dka.rdbms.common.model.item

import io.circe.parser.decode
import io.circe.syntax._
import org.dka.rdbms.common.model.fields._
import org.dka.rdbms.common.model.item
import org.dka.rdbms.common.model.item.Author._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class AuthorSpec extends AnyFunSpec with Matchers {

  describe("read and write from json") {
    it("with all fields") {
      val author = Author(
        ID.build,
        Version.defaultVersion,
        LastName.build("Doe"),
        Some(FirstName.build("John")),
        Some(LocationID.build),
        CreateDate.now,
        UpdateDate.now
      )
      val json = author.asJson.noSpaces
      println(s"author:\n${author.asJson.spaces2}")
      decode[Author](json) match {
        case Left(error) => fail(error)
        case Right(decoded) => decoded shouldBe author
      }
    }
    it("with all fields, read different order than write") {
      val id = ID.build
      val createDate = CreateDate.now
      val author = Author(
        id,
        Version.defaultVersion,
        LastName.build("Doe"),
        Some(FirstName.build("John")),
        None,
        createDate
      )
      val json = author.asJson.spaces2
      println(s"json:\n$json")
      val differentOrder =
        """
          {
            "ID" : "replaceID",
            "create_date" : "replaceCD",
            "version" : 1,
            "first_name" : "John",
            "last_name" : "Doe"
          }""".stripMargin
          .replace("replaceID", id.value.toString)
          .replace("replaceCD", createDate.asTimestamp.toString.replace(' ', 'T'))

      println(s"differentOrder:\n$differentOrder")
      decode[Author](differentOrder) match {
        case Left(error)    => fail(error)
        case Right(decoded) => decoded shouldBe author
      }
    }
    it("with optional fields") {
      val author = item.Author(
        ID.build,
        Version.defaultVersion,
        LastName.build("Doe"),
        None,
        None
      )
      val json = author.asJson.spaces2
      println(s"json:\n$json")
      decode[Author](json) match {
        case Left(error)    => fail(error)
        case Right(decoded) => decoded shouldBe author
      }
    }
  }

  describe("with valid json. but holding invalid model") {
    it("should fail when too short") {
      // first name is too short
      val json = s""" {"ID":"f2591bcf-41d6-4b35-a3ff-00916e7d48ea","lastName":"Doe","firstName":""} """
      decode[Author](json) match {
        case Left(error) => error.getMessage contains "firstName must be at least 1"
        case Right(_)    => fail(s"should not have parsed")
      }
    }
    it("should fail when too long") {
      // first name can't be more that 20 chars
      val json =
        s""" {"ID":"f2591bcf-41d6-4b35-a3ff-00916e7d48ea","lastName":"Doe","firstName":"123456789 123456789 12345"} """
      decode[Author](json) match {
        case Left(error) => error.getMessage contains "firstName can't be longer than 20"
        case Right(_)    => fail(s"should not have parsed")
      }
    }
    it("should fail when multiple domain errors") {
      // first name can't be more that 20 chars
      // lastName can't be empty
      val json =
        s""" {"ID":"f2591bcf-41d6-4b35-a3ff-00916e7d48ea","firstName":"123456789 123456789 12345", "lastName": ""} """
      decode[Author](json) match {
        case Left(error) =>
          error.getMessage contains "firstName can't be longer than 20"
          error.getMessage contains "lastName must be at least 1"
        case Right(_) => fail(s"should not have parsed")
      }
    }
    it("should fail when both json and domain errors") {
      // first name can't be more that 20 chars
      // lastName is missing from json
      val json =
        s""" {"ID":"f2591bcf-41d6-4b35-a3ff-00916e7d48ea","firstName":"123456789 123456789 12345", "last_name": ""} """
      decode[Author](json) match {
        case Left(error) =>
          error.getMessage contains "firstName can't be longer than 20"
          error.getMessage contains "lastName must be at least 1"
        case Right(_) => fail(s"should not have parsed")
      }
    }
    it("should fail with invalid json and domain errors") {
      // first name can't be more that 20 chars
      // lastName is missing from json
      val json =
        s""" {"ID":"f2591bcf-41d6-4b35-a3ff-00916e7d48ea","firstName":"123456789 123456789 12345", "last_name: ""} """
      decode[Author](json) match {
        case Left(_)  => succeed
        case Right(_) => fail(s"should not have parsed")
      }
    }
  }

}
