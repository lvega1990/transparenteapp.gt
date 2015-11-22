
package gt.transparente.app.domain.interactor;

import javax.inject.Inject;

import gt.transparente.app.domain.PoliticalParty;
import gt.transparente.app.domain.executor.PostExecutionThread;
import gt.transparente.app.domain.executor.ThreadExecutor;
import gt.transparente.app.domain.repository.TransparentRepository;
import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link PoliticalParty}.
 */
public class GetPoliticalPartyList extends UseCase {
    private int pageNumber;
    private final TransparentRepository transparentRepository;


    @Inject
    public GetPoliticalPartyList(TransparentRepository transparentRepository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.transparentRepository = transparentRepository;
    }

    @Override
    public Observable buildUseCaseObservable() {
        return this.transparentRepository.politicalPartyList(this.pageNumber);
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
