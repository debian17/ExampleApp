package com.example.citilin.testapp.database;

/**
 * Created by Андрей on 22-Aug-17.
 */

final class Entity {

    static class MyCharacterEntity {
        static final String TABLE_NAME = "myCharacters";
        static final String ID = "id";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_SUPERPOWER = "superpower";
        static final String COLUMN_PICTURE_PATH = "picture";

        static final String CREATE_MY_CHARACTERS_TABLE = "CREATE TABLE " + TABLE_NAME
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_SUPERPOWER + " TEXT NOT NULL, "
                + COLUMN_PICTURE_PATH + " TEXT NOT NULL);";
    }

    static class CharacterEntity {
        static final String TABLE_NAME = "characters";
        static final String ID = "id";
        static final String COLUMN_HERO_ID = "herId";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_PICTURE_PATH = "picture";

        static final String CREATE_CHARACTERS_TABLE = "CREATE TABLE " + TABLE_NAME
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_HERO_ID + " INTEGER NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_PICTURE_PATH + " TEXT NOT NULL);";
    }
}
