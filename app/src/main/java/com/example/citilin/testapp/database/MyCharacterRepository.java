package com.example.citilin.testapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.citilin.testapp.App;
import com.example.citilin.testapp.ui.mychracters.MyCharacter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class MyCharacterRepository extends SQLiteOpenHelper
        implements MyCharacterDataSource {

    private static final String DATABASE_NAME = "charactersDB.db";
    private static final int DATABASE_VERSION = 1;

    public MyCharacterRepository() {
        super(App.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

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

    @Override
    public void add(MyCharacter myCharacter) {
        if (!initWritableDatabase()) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Entity.MyCharacterEntity.COLUMN_NAME, myCharacter.getName());
        contentValues.put(Entity.MyCharacterEntity.COLUMN_SUPERPOWER, myCharacter.getSuperPower());
        contentValues.put(Entity.MyCharacterEntity.COLUMN_PICTURE_PATH, myCharacter.getPicturePath());

        sqLiteDatabase.insert(Entity.MyCharacterEntity.TABLE_NAME, null, contentValues);
    }

    @Override
    public void remove(MyCharacter myCharacter) {
        if (!initWritableDatabase()) {
            return;
        }

        sqLiteDatabase.delete(Entity.MyCharacterEntity.TABLE_NAME, Entity.MyCharacterEntity.ID + " = ?",
                new String[]{String.valueOf(myCharacter.getId())});
    }

    @Override
    public void update(MyCharacter myCharacter) {
        if (!initWritableDatabase()) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Entity.MyCharacterEntity.COLUMN_NAME, myCharacter.getName());
        contentValues.put(Entity.MyCharacterEntity.COLUMN_SUPERPOWER, myCharacter.getSuperPower());
        contentValues.put(Entity.MyCharacterEntity.COLUMN_PICTURE_PATH, myCharacter.getPicturePath());

        sqLiteDatabase.update(Entity.MyCharacterEntity.TABLE_NAME, contentValues, Entity.MyCharacterEntity.ID + " = ?",
                new String[]{String.valueOf(myCharacter.getId())});
    }

    @Override
    public Single<List<MyCharacter>> query(SqlSpecification sqlSpecification) {
        if (!initReadableDatabase()) {
            return Single.just(Collections.emptyList());
        }

        List<MyCharacter> myCharacters = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(sqlSpecification.toSqlQuery(), null);

        for (int i = 0, size = cursor.getCount(); i < size; i++) {
            cursor.moveToPosition(i);
            myCharacters.add(toMyCharacters(cursor));
        }
        cursor.close();
        sqLiteDatabase.close();
        return Single.just(myCharacters);
    }

    private MyCharacter toMyCharacters(Cursor cursor) {
        int idColumnIndex = cursor.getColumnIndexOrThrow(Entity.MyCharacterEntity.ID);
        int nameColumnIndex = cursor.getColumnIndexOrThrow(Entity.MyCharacterEntity.COLUMN_NAME);
        int superPowerColumnIndex = cursor.getColumnIndexOrThrow(Entity.MyCharacterEntity.COLUMN_SUPERPOWER);
        int picturePathColumnIndex = cursor.getColumnIndexOrThrow(Entity.MyCharacterEntity.COLUMN_PICTURE_PATH);

        return new MyCharacter(cursor.getInt(idColumnIndex),
                cursor.getString(nameColumnIndex),
                cursor.getString(superPowerColumnIndex),
                cursor.getString(picturePathColumnIndex));
    }
}
