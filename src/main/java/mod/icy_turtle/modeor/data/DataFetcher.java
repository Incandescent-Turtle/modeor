package mod.icy_turtle.modeor.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataFetcher
{
	private static final String API_KEY = "GfkcwkM5OhbvDk8JWlbB8vDvbeFgcnfMNCKrrdpY";
	private static final String BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed";
	private static List<MeteorData> meteors = new ArrayList<>();
	private static LocalDate currentDay;
	private DataFetcher(){}

	// public method to get the next meteor, fetches new data if the list is empty
	public static MeteorData getNextMeteor()
	{
		// if we ran out of meteors, fetch +1 days into the future until we find some
		if (meteors.isEmpty())
		{
			// if we haven't polled any yet
			if(currentDay == null)
			{
				currentDay = LocalDate.now().minusDays(1);
			}

			// loop until we got some
			do {
				currentDay = currentDay.plusDays(1);
				// attempt to call the api and parse the data, or fallback to default
				try
				{
					meteors = parse(fetchCurrentDay());
				} catch(Exception e)
				{
					meteors = new ArrayList<>();
					meteors.add(MeteorData.DEFAULT);
				}
			} while(meteors.isEmpty());

		}
		// pop one off
		return meteors.remove(0);
	}

	// parses json data into list of MeteorData
	private static List<MeteorData> parse(JsonObject data)
	{
		List<MeteorData> meteors = new ArrayList<>();
		JsonObject objects = data.getAsJsonObject("near_earth_objects");
		for (String date : objects.keySet())
		{
			for (JsonElement e : objects.getAsJsonArray(date))
			{
				JsonObject obj = e.getAsJsonObject();
				MeteorData m = new MeteorData();

				m.name = obj.get("name").getAsString();
				m.diameterMetersMax = obj
						.getAsJsonObject("estimated_diameter")
						.getAsJsonObject("meters")
						.get("estimated_diameter_max").getAsDouble();
				m.diameterMetersMin = obj
						.getAsJsonObject("estimated_diameter")
						.getAsJsonObject("meters")
						.get("estimated_diameter_min").getAsDouble();

				JsonObject approach = obj.getAsJsonArray("close_approach_data")
						.get(0).getAsJsonObject();

				m.velocityKps = approach
						.getAsJsonObject("relative_velocity")
						.get("kilometers_per_second").getAsDouble();

				m.missDistanceKm = approach
						.getAsJsonObject("miss_distance")
						.get("kilometers").getAsDouble();

				m.magnitude = obj.get("absolute_magnitude_h").getAsDouble();
				m.hazardous = obj.get("is_potentially_hazardous_asteroid").getAsBoolean();

				meteors.add(m);
			}
		}
		return meteors;
	}

	// queries for the currently selected day and returns the json object
	private static JsonObject fetchCurrentDay() throws IOException, InterruptedException
	{
		String today = currentDay.toString();
		String url = BASE_URL + "?start_date=" + today + "&end_date=" + today + "&api_key=" + API_KEY;

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return JsonParser.parseString(response.body()).getAsJsonObject();
	}
}
