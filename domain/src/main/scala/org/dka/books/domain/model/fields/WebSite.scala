package org.dka.books.domain.model.fields

import org.dka.books.domain.model.validation.StringLengthValidation

/**
 * website requirements:
 *   - can not be more than 60
 */
sealed abstract case class WebSite private (override val value: String) extends Field[String]

object WebSite extends StringLengthValidation[WebSite] {

  override val maxLength = 60

  override val minLength = 1

  override val fieldName: String = "website"

  override def build(c: String): WebSite = new WebSite(c) {}

  def fromOpt(o: Option[String]): Option[WebSite] = o.map(build)

}
