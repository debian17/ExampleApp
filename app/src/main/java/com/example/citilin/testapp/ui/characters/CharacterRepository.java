package com.example.citilin.testapp.ui.characters;

import com.example.citilin.testapp.App;
import com.example.citilin.testapp.BuildConfig;
import com.example.citilin.testapp.database.CharacterDBHelper;
import com.example.citilin.testapp.database.CharacterSqlSpecification;
import com.example.citilin.testapp.network.ApiHelper;
import com.example.citilin.testapp.ui.characters.character.comics.Comic;
import com.example.citilin.testapp.ui.characters.character.event.Event;
import com.example.citilin.testapp.ui.characters.character.series.Series;
import com.example.citilin.testapp.ui.characters.character.stories.Story;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CharacterRepository implements CharacterDataSource {

    private CharacterDBHelper characterDBHelper;
    private boolean cacheIsLoaded;

    public CharacterRepository() {
        characterDBHelper = CharacterDBHelper.getInstance();
        cacheIsLoaded = false;
    }

    @Override
    public Single<List<Character>> getCharacters(int countCharacters, int offset) {
        if (offset == 0 && !characterDBHelper.cacheIsEmpty()) {
            cacheIsLoaded = true;
            return characterDBHelper.query(new CharacterSqlSpecification())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            String time = ApiHelper.getTime();
            return App.getApi().loadCharacters(time, BuildConfig.MARVEL_PUBLIC_KEY,
                    ApiHelper.getMD5(time), countCharacters, offset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(i -> Arrays.asList(i.data.getResults()))
                    .map(items -> {
                        addCharactersToCache(items);
                        return items;
                    });
        }
    }

    @Override
    public void addCharactersToCache(List<Character> characters) {
        Observable.fromCallable(() -> {
            for (int i = 0; i < characters.size(); i++) {
                characterDBHelper.addCharacter(characters.get(i));
            }
            return true;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public Single<List<Comic>> getComics(int characterId, int countComics, int offset) {
        String time = ApiHelper.getTime();
        return App.getApi().loadComicsByHeroId(characterId, time, BuildConfig.MARVEL_PUBLIC_KEY,
                ApiHelper.getMD5(time), countComics, offset)
                .subscribeOn(Schedulers.io())
                .map(it -> Arrays.asList(it.data.getResults()));
    }

    @Override
    public Single<List<Event>> getEvents(int characterId, int countEvents, int offset) {
        String time = ApiHelper.getTime();
        return App.getApi().loadEventsByHeroId(characterId, time, BuildConfig.MARVEL_PUBLIC_KEY,
                ApiHelper.getMD5(time), countEvents, offset)
                .subscribeOn(Schedulers.io())
                .map(it -> Arrays.asList(it.data.getResults()));
    }

    @Override
    public Single<List<Series>> getSeries(int characterId, int countSeries, int offset) {
        String time = ApiHelper.getTime();
        return App.getApi().loadSeriesByHeroId(characterId, time, BuildConfig.MARVEL_PUBLIC_KEY,
                ApiHelper.getMD5(time), countSeries, offset)
                .subscribeOn(Schedulers.io())
                .map(it -> Arrays.asList(it.data.getResults()));
    }

    @Override
    public Single<List<Story>> getStories(int characterId, int countStories, int offset) {
        String time = ApiHelper.getTime();
        return App.getApi().loadStoriesByHeroId(characterId, time, BuildConfig.MARVEL_PUBLIC_KEY,
                ApiHelper.getMD5(time), countStories, offset)
                .subscribeOn(Schedulers.io())
                .map(it -> Arrays.asList(it.data.getResults()));
    }
}
