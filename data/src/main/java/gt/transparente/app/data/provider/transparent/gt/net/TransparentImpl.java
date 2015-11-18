
package gt.transparente.app.data.provider.transparent.gt.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import gt.transparente.app.data.exception.NetworkConnectionException;
import gt.transparente.app.data.provider.transparent.gt.TransparentProvider;
import gt.transparente.app.data.provider.transparent.gt.net.response.PoliticalPartyListResponse;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;

/**
 * Class that represents a Kandy Endpoint implementation.
 */
@Singleton
public class TransparentImpl implements TransparentProvider {
    public final String API_BASE_URL = TransparentEndpoint.API_BASE_URL;

    private TransparentEndpoint mTransparentEndpoint;
    private final Context mContext;

    /**
     * Constructor of the class
     *
     * @param context {@link android.content.Context}.
     */
    @Inject
    public TransparentImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.mContext = context.getApplicationContext();
        init();
    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTransparentEndpoint = retrofit.create(TransparentEndpoint.class);

    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    @Override
    public Observable<List<PoliticalPartyEntity>> politicalPartyEntityList() {
        return Observable.create(new Observable.OnSubscribe<List<PoliticalPartyEntity>>() {
            @Override
            public void call(Subscriber<? super List<PoliticalPartyEntity>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Call<PoliticalPartyListResponse> call = mTransparentEndpoint.politicalPartyList(1, 100);
                        PoliticalPartyListResponse politicalPartyListResponse = call.execute().body();
                        if (politicalPartyListResponse.getResult() != null) {
                            subscriber.onNext(politicalPartyListResponse.getResult().getPoliticalPartyEntities());
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (IOException e) {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }

            }
        });
    }

    @Override
    public Observable<PoliticalPartyEntity> politicalPartyEntityById(int politicalPartyId) {
        return Observable.create(new Observable.OnSubscribe<PoliticalPartyEntity>() {
            @Override
            public void call(Subscriber<? super PoliticalPartyEntity> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Call<PoliticalPartyEntity> call = mTransparentEndpoint.politicalParty(politicalPartyId);
                        PoliticalPartyEntity politicalPartyEntity = call.execute().body();
                        if (politicalPartyEntity != null) {
                            subscriber.onNext(politicalPartyEntity);
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (IOException e) {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }

            }
        });
    }
}
