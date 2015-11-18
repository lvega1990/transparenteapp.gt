
package gt.transparente.app.domain.executor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link gt.transparente.app.domain.interactor.UseCase} out of the UI thread.
 */
public interface ThreadExecutor extends Executor {}
