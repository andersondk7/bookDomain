package org.dka.books.domain.services

import org.dka.books.domain.model.item.Country

/**
 * adds methods beyond simple crud stuff anticipated to be mostly specific queries
 *
 * this interface is db agnostic and allows for easy unit testing since an database is not required
 */
trait CountryDao extends CrudDao[Country] {}
