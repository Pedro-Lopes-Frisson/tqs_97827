package pedroLopes.tqs.covid19trackingservice.apiconsumer;


import org.springframework.stereotype.Component;
import pedroLopes.tqs.covid19trackingservice.models.RootReport;
import pedroLopes.tqs.covid19trackingservice.models.SummaryReport;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

@Component
public interface APIJonhsHopkinsCSSE {
  @Headers({
    "X-RapidAPI-Host: covid-19-statistics.p.rapidapi.com",
    "X-RapidAPI-Key: ea0cd966e4msh3606703ced4ea73p1acda7jsn516db04bb8d3"})
  @GET("reports")
  Call<RootReport> getSpecificReport( @Query("city_name") String city_name,
                                      @Query("date") String date );
  
  @GET("reports/total")
  @Headers({
    "X-RapidAPI-Host: covid-19-statistics.p.rapidapi.com",
    "X-RapidAPI-Key: ea0cd966e4msh3606703ced4ea73p1acda7jsn516db04bb8d3"})
  Call<SummaryReport> getWorldWideReport( @Query("date") String date );
  
}