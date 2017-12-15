package com.example.citilin.testapp.database;

public class CharacterSqlSpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM  %1s", Entity.CharacterEntity.TABLE_NAME
        );
    }
}
