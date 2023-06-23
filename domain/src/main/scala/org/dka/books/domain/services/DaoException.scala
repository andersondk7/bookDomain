package org.dka.books.domain.services

import org.dka.books.domain.model.fields.{ID, Version}

import java.sql.SQLException

/**
 * These represent things that can go wrong in the DAO layer.
 *
 * There are generic DAO exceptions that correspond to DAO actions: ( delete, insert, query, config etc.)
 *
 * And there are SQL specific errors.
 *
 * SQL standard (which postgres follows) delineates 'classes' of errors, with specific errors under the class.
 *
 * Each error is a 5 digit string, xxYYYY where:
 *   - xx is the error class identifier
 *   - yyy is the specific error condition
 *
 * for example:
 *   - 08001 -
 *     - 08 connection exception
 *     - 001 sqlclient_unable_to_establish_sqlconnection
 *
 * for each class there is a DaoException implementation, but for the specific condition, only a few have been
 * implemented
 */

sealed trait DaoException extends Throwable {

  val reason: String

  val underlyingCause: Option[Throwable]

  override def getMessage: String = reason

  override def getCause: Throwable = underlyingCause.orNull

}

//
// Generic DAO exceptions
//
final case class DeleteException(override val reason: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException

final case class InsertException(override val reason: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException

final case class QueryException(override val reason: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException

final case class ConfigurationException(reasons: List[String], override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = reasons.mkString("\t")

}

final case class ItemNotFoundException(id: ID, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = s"could not find $id"

}

final case class InvalidVersionException(oldVersion: Version, newVersion: Option[Version] = None) extends DaoException {

  override val underlyingCause: Option[Throwable] = None

  override val reason: String = newVersion.fold(s"attempt to update old version $oldVersion")(nv =>
    s"attempt to update old version $oldVersion with $nv")

}

final case class UpdateException(id: ID, override val underlyingCause: Option[Throwable] = None) extends DaoException {

  override val reason: String = s"could not update id: $ID"

}

//
// SQL based exceptions
//

final case class SQLWarning(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLNoData(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLStatmentNoComplete(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLConnectionException(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLTriggeredActionException(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLFeateureNotSupported(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidTransactionInitiation(
  errorCode: String,
  override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLLocatorException(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidGrantor(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidRoleSpecification(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLDiagnosticsException(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLCaseNotFound(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLCardinalityViolation(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLDataException(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLIntegrityConstraintViolation(
  errorCode: String,
  override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidCursorState(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidTransactionState(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidStatementName(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLTriggeredDataChangeViolation(
  errorCode: String,
  override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidAuthorizationSpecification(
  errorCode: String,
  override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLDependantPrivilege(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidTransactionTermination(
  errorCode: String,
  override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLRoutineException(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidCursorName(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLExternalRoutingInvocation(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLSavePoint(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidCatalogName(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInvalidSchemaName(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLTransactionRollback(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLSyntaxError(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLWithCheckOptionViolation(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInsufficientResources(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLProgramLimitExceeded(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLObjectNotInPrequisiteState(
  errorCode: String,
  override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLOperatorIntervention(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLSystemError(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLSnapshotFailure(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLConfigurationFileError(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLForeignDataWrapper(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLPgSqlError(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLInternalError(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

final case class SQLUnMappedError(errorCode: String, override val underlyingCause: Option[Throwable] = None)
  extends DaoException {

  override val reason: String = errorCode

}

object DaoException {

  // only expect one DaoException, so don't make this a ValidatedNecDaoException, T]
  type DaoErrorsOr[T] = Either[DaoException, T]

  def fromSQL(sqlException: SQLException): DaoException = sqlException.getSQLState.take(2) match {
    case "01" => SQLWarning(sqlException.getSQLState)
    case "02" => SQLNoData(sqlException.getSQLState)
    case "03" => SQLStatmentNoComplete(sqlException.getSQLState)
    case "08" => SQLConnectionException(sqlException.getSQLState)
    case "09" => SQLTriggeredActionException(sqlException.getSQLState)
    case "0A" => SQLFeateureNotSupported(sqlException.getSQLState)
    case "0B" => SQLInvalidTransactionInitiation(sqlException.getSQLState)
    case "0F" => SQLLocatorException(sqlException.getSQLState)
    case "0L" => SQLInvalidGrantor(sqlException.getSQLState)
    case "0P" => SQLInvalidRoleSpecification(sqlException.getSQLState)
    case "0Z" => SQLDiagnosticsException(sqlException.getSQLState)
    case "20" => SQLCaseNotFound(sqlException.getSQLState)
    case "21" => SQLCardinalityViolation(sqlException.getSQLState)
    case "22" => SQLDataException(sqlException.getSQLState)
    case "23" => SQLIntegrityConstraintViolation(sqlException.getSQLState)
    case "24" => SQLInvalidCursorState(sqlException.getSQLState)
    case "25" => SQLInvalidTransactionState(sqlException.getSQLState)
    case "26" => SQLInvalidStatementName(sqlException.getSQLState)
    case "27" => SQLTriggeredDataChangeViolation(sqlException.getSQLState)
    case "28" => SQLInvalidAuthorizationSpecification(sqlException.getSQLState)
    case "2B" => SQLDependantPrivilege(sqlException.getSQLState)
    case "2D" => SQLInvalidTransactionTermination(sqlException.getSQLState)
    case "2F" => SQLRoutineException(sqlException.getSQLState)
    case "34" => SQLInvalidCursorName(sqlException.getSQLState)
    case "38" => SQLRoutineException(sqlException.getSQLState)
    case "39" => SQLExternalRoutingInvocation(sqlException.getSQLState)
    case "3B" => SQLSavePoint(sqlException.getSQLState)
    case "3D" => SQLInvalidCatalogName(sqlException.getSQLState)
    case "3F" => SQLInvalidSchemaName(sqlException.getSQLState)
    case "40" => SQLTransactionRollback(sqlException.getSQLState)
    case "42" => SQLSyntaxError(sqlException.getSQLState)
    case "44" => SQLWithCheckOptionViolation(sqlException.getSQLState)
    case "53" => SQLInsufficientResources(sqlException.getSQLState)
    case "54" => SQLProgramLimitExceeded(sqlException.getSQLState)
    case "55" => SQLObjectNotInPrequisiteState(sqlException.getSQLState)
    case "57" => SQLOperatorIntervention(sqlException.getSQLState)
    case "58" => SQLSystemError(sqlException.getSQLState)
    case "72" => SQLSnapshotFailure(sqlException.getSQLState)
    case "F0" => SQLConfigurationFileError(sqlException.getSQLState)
    case "HV" => SQLForeignDataWrapper(sqlException.getSQLState)
    case "P0" => SQLPgSqlError(sqlException.getSQLState)
    case "XX" => SQLInternalError(sqlException.getSQLState)
    case _    => SQLUnMappedError(sqlException.getSQLState)
  }

}
