package com.example.citilin.testapp.ui.characters;

import com.example.citilin.testapp.ui.characters.character.comics.Comic;
import com.example.citilin.testapp.ui.characters.character.event.Event;
import com.example.citilin.testapp.ui.characters.character.series.Series;
import com.example.citilin.testapp.ui.characters.character.stories.Story;

import java.util.List;

import io.reactivex.Single;

public interface CharacterDataSource {

    Single<List<Character>> getCharacters(int countCharacters, int offset);

    Single<List<Comic>> getComics(int characterId, int countComics, int offset);

    Single<List<Event>> getEvents(int characterId, int countEvents, int offset);

    Single<List<Series>> getSeries(int characterId, int countSeries, int offset);

    Single<List<Story>> getStories(int characterId, int countStories, int offset);

    void addCharactersToCache(List<Character> characters);

}
