package co.com.tekton.config;

import co.com.tekton.external.PercentageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public PercentageClient percentageClient(Retrofit retrofit) {
        return retrofit.create(PercentageClient.class);
    }
}
