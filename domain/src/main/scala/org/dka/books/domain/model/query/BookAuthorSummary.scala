package org.dka.books.domain.model.query

import io.circe._
import org.dka.books.domain.model.fields.{FirstName, LastName, Title}
import org.dka.books.domain.model.item.{Author, AuthorBookRelationship, Book}

final case class BookAuthorSummary(
  titleName: Title,
  authorLastName: LastName,
  authorFirstName: Option[FirstName],
  authorOrder: Int)

object BookAuthorSummary {

  def apply(relationship: AuthorBookRelationship, book: Book, author: Author): BookAuthorSummary =
    new BookAuthorSummary(book.title, author.lastName, author.firstName, relationship.authorOrder)

  def fromDB(title: String, lastName: String, firstName: Option[String], order: Int): BookAuthorSummary =
    BookAuthorSummary(Title.build(title), LastName.build(lastName), FirstName.build(firstName), order)

  implicit val encodeTitleAuthorSummary: Encoder[BookAuthorSummary] = (summary: BookAuthorSummary) => {
    val objects = List(
      Some(Title.toJson(summary.titleName)),
      Some(LastName.toJson(summary.authorLastName)),
      FirstName.toJson(summary.authorFirstName)
    ).flatten // filter out the None, i.e. only needed lines
    Json.obj(objects: _*)
  }

}
