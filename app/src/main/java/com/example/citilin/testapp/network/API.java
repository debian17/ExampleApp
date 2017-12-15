package com.example.citilin.testapp.network;

import com.example.citilin.testapp.ui.characters.Character;
import com.example.citilin.testapp.ui.characters.DataWrapper;
import com.example.citilin.testapp.ui.characters.character.comics.Comic;
import com.example.citilin.testapp.ui.characters.character.event.Event;
import com.example.citilin.testapp.ui.characters.character.series.Series;
import com.example.citilin.testapp.ui.characters.character.stories.Story;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("/v1/public/characters")
    Single<DataWrapper<Character>> loadCharacters(
            @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash,
            @Query("limit") int limit, @Query("offset") int offset
    );

    @GET("/v1/public/characters/{characterId}/comics")
    Single<DataWrapper<Comic>> loadComicsByHeroId(
            @Path("characterId") int id,
            @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash,
            @Query("limit") int limit, @Query("offset") int offset
    );

    @GET("/v1/public/characters/{characterId}/stories")
    Single<DataWrapper<Story>> loadStoriesByHeroId(
            @Path("characterId") int id, @Query("ts") String ts, @Query("apikey") String apikey,
            @Query("hash") String hash, @Query("limit") int limit, @Query("offset") int offset);

    @GET("v1/public/characters/{characterId}/events")
    Single<DataWrapper<Event>> loadEventsByHeroId(
            @Path("characterId") int id, @Query("ts") String ts, @Query("apikey") String apikey,
            @Query("hash") String hash, @Query("limit") int limit, @Query("offset") int offset);

    @GET("/v1/public/characters/{characterId}/series")
    Single<DataWrapper<Series>> loadSeriesByHeroId(
            @Path("characterId") int id, @Query("ts") String ts, @Query("apikey") String apikey,
            @Query("hash") String hash, @Query("limit") int limit, @Query("offset") int offset);
}
