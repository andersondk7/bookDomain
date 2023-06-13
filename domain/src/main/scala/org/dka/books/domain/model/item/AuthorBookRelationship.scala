package org.dka.books.domain.model.item

import org.dka.books.domain.model.fields.ID

final case class AuthorBookRelationship(authorId: ID, bookId: ID, authorOrder: Int) {}
