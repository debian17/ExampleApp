package com.example.citilin.testapp.database;

import com.example.citilin.testapp.ui.mychracters.MyCharacter;

import java.util.List;

import io.reactivex.Single;

public interface MyCharacterDataSource {
    void add(MyCharacter myCharacter);

    void remove(MyCharacter myCharacter);

    void update(MyCharacter myCharacter);

    Single<List<MyCharacter>> query(SqlSpecification sqlSpecification);
}
