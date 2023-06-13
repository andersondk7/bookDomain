package org.dka.books.domain.services

import org.dka.books.domain.services.DaoException.DaoErrorsOr
import org.dka.books.domain.model.fields.ID
import org.dka.books.domain.model.item.Book
import org.dka.books.domain.model.query.BookAuthorSummary

import scala.concurrent.{ExecutionContext, Future}

/**
 * adds methods beyond simple crud stuff anticipated to be mostly specific queries
 *
 * this interface is db agnostic and allows for easy unit testing since an database is not required
 */
trait BookDao extends CrudDao[Book] {

  /**
   * get the ids of all books // should be a stream!!!
   */
  def getAllIds(implicit ec: ExecutionContext): Future[DaoErrorsOr[Seq[ID]]]

  /**
   * for a given book, return all the authors, and order
   */
  def getBookAuthorSummary(bookId: ID)(implicit ec: ExecutionContext): Future[DaoErrorsOr[Seq[BookAuthorSummary]]]
//  def getAuthorsForBookSql(bookId: ID)(implicit ec: ExecutionContext): Future[DaoErrorsOr[Seq[BookAuthorSummary]]]

}
