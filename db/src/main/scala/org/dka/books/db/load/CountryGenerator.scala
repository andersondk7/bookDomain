package org.dka.books.db.load

import org.dka.books.domain.model.fields.{CountryAbbreviation, CountryName, ID, Version}
import org.dka.books.domain.model.item.Country
import Generator._

import java.util.UUID

class CountryGenerator(
  override val count: Int,
  override val fileName: String = "countryInsert.sql")
  extends ItemGenerator {

  import CountryGenerator._

  override val headerLine: String = bulkLoadCountry.header

  override def insertLine(uuid: UUID): String = {

    val country = Country(
      ID.build(uuid),
      Version.defaultVersion,
      CountryName.build(genString(CountryName.maxLength)),
      CountryAbbreviation.build(genString(CountryAbbreviation.maxLength))
    )
    bulkLoadCountry.insertLine(country)
  }

}

object CountryGenerator {

  private val bulkLoadCountry: BulkLoad[Country] = new BulkLoad[Country] {

    override def header: String = "insert into countries(id, country_name, country_abbreviation)\n  values\n"

    override def insertLine(c: Country): String =
      s"('${c.id.value.toString}', '${c.countryName.value}', '${c.countryAbbreviation.value}'),"

  }

}
