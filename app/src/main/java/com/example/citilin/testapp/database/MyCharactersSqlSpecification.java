package com.example.citilin.testapp.database;

public class MyCharactersSqlSpecification implements SqlSpecification {
    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM  %1s", Entity.MyCharacterEntity.TABLE_NAME
        );
    }
}
