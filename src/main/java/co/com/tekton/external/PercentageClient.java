package co.com.tekton.external;

import co.com.tekton.model.dto.PercentageResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PercentageClient {
    @GET("/percentage")
    Call<PercentageResponse> getPercentage();
}
