package com.example.citilin.testapp.ui.rxjava;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class RxProperty {

    private Integer value;
    private PublishSubject<Integer> subjectValue;

    RxProperty() {
        value = 0;
        subjectValue = PublishSubject.create();
    }

    Observable<Integer> getObservableValue() {
        return subjectValue;
    }

    void setValue(Integer i) {
        value = i;
        subjectValue.onNext(value);
    }

    Integer getValue() {
        return value;
    }
}
