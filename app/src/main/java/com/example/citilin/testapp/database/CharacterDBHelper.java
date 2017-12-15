package com.example.citilin.testapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.citilin.testapp.App;
import com.example.citilin.testapp.ui.characters.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class CharacterDBHelper extends SQLiteOpenHelper {

    private static String CHECK_IS_EMPTY = "select exists(select 1 from "
            + Entity.CharacterEntity.TABLE_NAME + ");";

    private static CharacterDBHelper instance;

    private CharacterDBHelper() {
        super(App.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized CharacterDBHelper getInstance() {
        if (instance == null) {
            instance = new CharacterDBHelper();
        }
        return instance;
    }

    private static final String DATABASE_NAME = "charactersDB.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase sqLiteDatabase;

    private boolean initWritableDatabase() {
        try {
            sqLiteDatabase = getWritableDatabase();
            return true;
        } catch (SQLiteException exception) {
            return false;
        }
    }

    private boolean initReadableDatabase() {
        try {
            sqLiteDatabase = getReadableDatabase();
            return true;
        } catch (SQLiteException exception) {
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Entity.CharacterEntity.CREATE_CHARACTERS_TABLE);
        sqLiteDatabase.execSQL(Entity.MyCharacterEntity.CREATE_MY_CHARACTERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addCharacter(Character character) {
        if (!initWritableDatabase()) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Entity.CharacterEntity.COLUMN_HERO_ID, character.getId());
        contentValues.put(Entity.CharacterEntity.COLUMN_NAME, character.getName());
        contentValues.put(Entity.CharacterEntity.COLUMN_PICTURE_PATH, character.getImagePath());
        sqLiteDatabase.insert(Entity.CharacterEntity.TABLE_NAME, null, contentValues);

    }

    private List<Character> queryFromDataBase(SqlSpecification sqlSpecification) {
        if (!initReadableDatabase()) {
            return Collections.emptyList();
        }

        List<Character> myCharacters = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(sqlSpecification.toSqlQuery(), null);
        for (int i = 0, size = cursor.getCount(); i < size; i++) {
            cursor.moveToPosition(i);
            myCharacters.add(toCharacter(cursor));
        }

        cursor.close();
        sqLiteDatabase.close();

        return myCharacters;
    }

    public Single<List<Character>> query(SqlSpecification sqlSpecification) {
        return Single.fromCallable(() -> queryFromDataBase(sqlSpecification));
    }

    private Character toCharacter(Cursor cursor) {
        int heroIdColumnIndex = cursor.getColumnIndexOrThrow(Entity.CharacterEntity.COLUMN_HERO_ID);
        int nameColumnIndex = cursor.getColumnIndexOrThrow(Entity.CharacterEntity.COLUMN_NAME);
        int picturePathColumnIndex = cursor.getColumnIndexOrThrow(Entity.CharacterEntity.COLUMN_PICTURE_PATH);

        return new Character(cursor.getInt(heroIdColumnIndex),
                cursor.getString(nameColumnIndex),
                cursor.getString(picturePathColumnIndex));
    }

    public boolean cacheIsEmpty() {
        if (!initReadableDatabase()) {
            return true;
        } else {
            boolean flag;
            Cursor cursor = sqLiteDatabase.rawQuery(CHECK_IS_EMPTY, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if (count == 1) {
                flag = false;
            } else {
                flag = true;
            }
            cursor.close();
            sqLiteDatabase.close();

            return flag;
        }
    }

}
