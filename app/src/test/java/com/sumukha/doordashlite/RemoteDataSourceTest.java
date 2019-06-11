package com.sumukha.doordashlite;

import com.sumukha.doordashlite.data.data_sources.RemoteDataSource;
import com.sumukha.doordashlite.data.models.Restaurant;
import com.sumukha.doordashlite.util.Constants;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.HttpException;

import static org.junit.Assert.assertEquals;

// Tests RemoteDataSource via Retrofit
// Tests that data is parsed into appropriate objects
public class RemoteDataSourceTest {

    private static MockWebServer server;

    private RemoteDataSource dataManager;

    @BeforeClass
    public static void setUpClass() throws IOException {
        server = new MockWebServer();
        server.start();
        Constants.API_BASE_URL = server.url("/").toString();
    }

    @Before
    public void setUp() {
        dataManager = new RemoteDataSource();
    }

    @Test
    public void successSingleRestaurantTest() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"delivery_fee\": 2,\n" +
                        "        \"name\": \"Restaurant\",\n" +
                        "        \"description\": \"Description\",\n" +
                        "        \"cover_img_url\": \"https://www.foo.bar\",\n" +
                        "        \"status\": \"10 min\"\n" +
                        "    }\n" +
                        "]"));

        TestObserver<List<Restaurant>> testObserver = dataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1);

        List<Restaurant> restaurants = testObserver.values().get(0);
        assertEquals(restaurants.size(), 1);

        Restaurant restaurant = restaurants.get(0);
        assertEquals(restaurant.id, 1);
        assertEquals(restaurant.name, "Restaurant");
        assertEquals(restaurant.description, "Description");
        assertEquals(restaurant.coverImgUrl, "https://www.foo.bar");
        assertEquals(restaurant.status, "10 min");

        testObserver.dispose();
    }

    @Test
    public void successNoRestaurantsTest() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("[]"));

        TestObserver<List<Restaurant>> testObserver = dataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1);


        List<Restaurant> restaurants = testObserver.values().get(0);
        assertEquals(restaurants.size(), 0);

        testObserver.dispose();
    }

    @Test
    public void successMultiPostTest() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"delivery_fee\": 2,\n" +
                        "        \"name\": \"Restaurant\",\n" +
                        "        \"description\": \"Description\",\n" +
                        "        \"cover_img_url\": \"https://www.foo.bar\",\n" +
                        "        \"status\": \"10 min\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"delivery_fee\": 3,\n" +
                        "        \"name\": \"Restaurant 2\",\n" +
                        "        \"description\": \"Description 2\",\n" +
                        "        \"cover_img_url\": \"https://www.foo.bar\",\n" +
                        "        \"status\": \"10 min\"\n" +
                        "    }\n" +
                        "]"));

        TestObserver<List<Restaurant>> testObserver = dataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1);

        List<Restaurant> restaurants = testObserver.values().get(0);
        assertEquals(restaurants.size(), 2);

        testObserver.dispose();
    }

    @Test
    public void failEmptyResponse() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(""));

        TestObserver<List<Restaurant>> testObserver = dataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG).test()
                .assertError(EOFException.class)
                .assertNoValues();

        assertEquals(testObserver.errorCount(), 1);

        testObserver.dispose();
    }

    @Test
    public void failNetworkTest() {
        server.enqueue(new MockResponse()
                .setResponseCode(500));

        TestObserver<List<Restaurant>> testObserver = dataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG).test()
                .assertError(HttpException.class)
                .assertNoValues();

        assertEquals(testObserver.errorCount(), 1);

        HttpException error = (HttpException) testObserver.errors().get(0);
        assertEquals(error.code(), 500);

        testObserver.dispose();
    }
}