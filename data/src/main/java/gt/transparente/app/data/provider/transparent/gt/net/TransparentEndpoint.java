
package gt.transparente.app.data.provider.transparent.gt.net;

import gt.transparente.app.data.entity.PoliticalPartyEntity;
import gt.transparente.app.data.provider.transparent.gt.net.response.PoliticalPartyListResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TransparentEndpoint {
    String API_BASE_URL = "http://transparente.gt";

    @GET("/api/partido-politico")
    Call<PoliticalPartyListResponse> politicalPartyList(
            @Query("page") int pageNumber,
            @Query("page_size") int pageSize);


    @GET("/api/partido-politico/{id}")
    Call<PoliticalPartyEntity> politicalParty(
            @Path("id") int politicalPartyId);

}
