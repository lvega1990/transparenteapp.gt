
package gt.transparente.app.data.exception;

/**
 * Exception throw by the application when a Political Party search can't return a valid result.
 */
public class PoliticalPartyNotFoundException extends Exception {

    public PoliticalPartyNotFoundException() {
        super();
    }

    public PoliticalPartyNotFoundException(final String message) {
        super(message);
    }

    public PoliticalPartyNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PoliticalPartyNotFoundException(final Throwable cause) {
        super(cause);
    }
}
