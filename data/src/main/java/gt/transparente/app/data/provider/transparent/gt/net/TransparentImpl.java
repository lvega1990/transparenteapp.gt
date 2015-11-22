
package gt.transparente.app.data.provider.transparent.gt.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import gt.transparente.app.data.exception.NetworkConnectionException;
import gt.transparente.app.data.exception.NoMoreDataAvailableException;
import gt.transparente.app.data.exception.NotFoundException;
import gt.transparente.app.data.provider.transparent.gt.TransparentProvider;
import gt.transparente.app.data.provider.transparent.gt.net.response.PoliticalPartyListResponse;
import gt.transparente.app.data.provider.transparent.gt.net.response.Response;
import gt.transparente.app.data.util.LoggingInterceptor;
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
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
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
    public Observable<List<PoliticalPartyEntity>> politicalPartyEntityList(int pageNumber) {
        return Observable.create(new Observable.OnSubscribe<List<PoliticalPartyEntity>>() {
            @Override
            public void call(Subscriber<? super List<PoliticalPartyEntity>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Call<PoliticalPartyListResponse> call = mTransparentEndpoint.politicalPartyList(pageNumber);
                        retrofit.Response<PoliticalPartyListResponse> response = call.execute();
                        PoliticalPartyListResponse politicalPartyListResponse = response.body();
                        if (politicalPartyListResponse != null && politicalPartyListResponse.getResult() != null) {
                            subscriber.onNext(politicalPartyListResponse.getResult().getPoliticalPartyEntities());
                            subscriber.onCompleted();
                        } else {
                            Throwable e;
                            switch (response.code()) {
                                case 404:
                                    e = new NotFoundException();
                                    break;
                                case 409:
                                    e = new NoMoreDataAvailableException();
                                    break;
                                default:
                                    e = new NetworkConnectionException();
                            }
                            subscriber.onError(e);
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
