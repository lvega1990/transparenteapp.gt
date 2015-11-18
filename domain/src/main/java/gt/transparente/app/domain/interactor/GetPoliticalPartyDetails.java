
package gt.transparente.app.domain.interactor;

import javax.inject.Inject;

import gt.transparente.app.domain.PoliticalParty;
import gt.transparente.app.domain.executor.PostExecutionThread;
import gt.transparente.app.domain.executor.ThreadExecutor;
import gt.transparente.app.domain.repository.TransparentRepository;
import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link PoliticalParty}.
 */
public class GetPoliticalPartyDetails extends UseCase {

  private final int partyId;
  private final TransparentRepository transparentRepository;

  @Inject
  public GetPoliticalPartyDetails(int partyId, TransparentRepository transparentRepository,
                                  ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.partyId = partyId;
    this.transparentRepository = transparentRepository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return this.transparentRepository.politicalPartyDetails(this.partyId);
  }
}
