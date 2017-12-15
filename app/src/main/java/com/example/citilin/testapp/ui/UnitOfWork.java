package com.example.citilin.testapp.ui;

import com.example.citilin.testapp.database.MyCharacterDataSource;
import com.example.citilin.testapp.database.MyCharacterRepository;
import com.example.citilin.testapp.ui.characters.CharacterDataSource;
import com.example.citilin.testapp.ui.characters.CharacterRepository;


public class UnitOfWork {

    private MyCharacterDataSource myCharactersRepository;
    private CharacterDataSource characterRepository;

    public UnitOfWork() {
        myCharactersRepository = new MyCharacterRepository();
        characterRepository = new CharacterRepository();
    }

    public MyCharacterDataSource getMyCharactersRepository() {
        return myCharactersRepository;
    }

    public CharacterDataSource getCharacterRepository() {
        return characterRepository;
    }
}
