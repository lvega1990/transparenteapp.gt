
package gt.transparente.app.domain.exception;

/**
 *  Wrapper around Exceptions used to manage default errors.
 */
public class DefaultErrorBundle implements ErrorBundle {

  private static final String DEFAULT_ERROR_MSG = "Unknown error";

  private final Exception mException;

  public DefaultErrorBundle(Exception exception) {
    this.mException = exception;
  }

  @Override
  public Exception getException() {
    return mException;
  }

  @Override
  public String getErrorMessage() {
    return (mException != null) ? this.mException.getMessage() : DEFAULT_ERROR_MSG;
  }
}
