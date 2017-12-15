package com.example.citilin.testapp.ui.rxjava;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.citilin.testapp.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxJava2Fragment extends Fragment {
    private Button rxButton;
    private Button unSUB;
    private CompositeDisposable compositeDisposable;
    private Observable<Integer> observable;

    private Observer<Integer> firstObserver;
    private Observer<Integer> secondObserver;

    private RxProperty rxProperty;

    public RxJava2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rx_java2, container, false);
    }

    private Integer timer() {
//        for (int i = 0; i < 1000; i++) {
//            Log.e("TIMER", String.valueOf(i));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        return 5;
    }

    private Observer<Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("observer1", String.valueOf(integer));
                if (integer == 5) {
                    observable.subscribe(secondObserver());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observer<Integer> secondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("observer2", String.valueOf(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();

        rxButton = (Button) view.findViewById(R.id.btnRX);
        unSUB = (Button) view.findViewById(R.id.btnUNS);

        rxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxProperty.setValue(7);
            }
        });

        firstObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("integerObserver", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("integerObserver", String.valueOf(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("integerObserver", "ERROR");
            }

            @Override
            public void onComplete() {
                Log.e("integerObserver", "onComplete");
            }
        };

        secondObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("anotherObserver", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("anotherObserver", String.valueOf(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("anotherObserver", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("anotherObserver", "onComplete");
            }
        };

        rxProperty = new RxProperty();

        //CompositeDisposable compositeDisposable = new CompositeDisposable();

        rxProperty.getObservableValue()
                .subscribe(firstObserver);

        rxProperty.getObservableValue()
                .subscribe(secondObserver);

        rxProperty.setValue(5);

//
//        observable.subscribe(getObserver());
//
//        PublishSubject<Integer> subject = PublishSubject.create();
//        subject.subscribeOn(Schedulers.io());
//
//        subject.subscribe(getObserver());
//
//
//        for (int i = 0; i < 10; i++) {
//            subject.onNext(i);
//            if (i == 4) {
//                subject.subscribe(secondObserver());
//            }
//        }

//        DisposableSubscriber<Integer> subscriber = new DisposableSubscriber<Integer>() {
//
//            @Override
//            protected void onStart() {
//                super.onStart();
//            }
//
//            @Override
//            public void onNext(Integer integer) {
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                Log.e("Subscriber1", String.valueOf(integer));
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.e("onError", "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("onComplete", "onComplete");
//            }
//        };

//        DisposableSubscriber<Integer> subscriber1 = new DisposableSubscriber<Integer>() {
//            @Override
//            public void onNext(Integer integer) {
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                Log.e("Subscriber2", String.valueOf(integer));
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };

//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull Integer integer) {
//                Log.e("observer", String.valueOf(integer));
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        Observer<Integer> observer1 = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull Integer integer) {
//                Log.e("observer1", String.valueOf(integer * 2));
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };

//        Observable<Integer> observableInt = Observable.fromCallable(() -> {
//            return 5;
//        });
//        observableInt.subscribeOn(Schedulers.io());
//
//        observableInt.subscribe(observer);
//        observableInt.subscribe(observer1);

//        Flowable<Integer> flowable = Flowable.range(1, 100)
//                .subscribeOn(Schedulers.io());
//
//        Disposable d = flowable.subscribeWith(subscriber);
//        Disposable d1 = flowable.subscribeWith(subscriber1);
//
//        compositeDisposable.add(d);
//        compositeDisposable.add(d1);
//
////        rxButton.setOnClickListener(v -> compositeDisposable.add(flowable.subscribeWith(subscriber)));
//
//        unSUB.setOnClickListener(v -> {
//            compositeDisposable.clear();
//        });
//    }

//    private void getI(Integer i) {
//        if (i == null) {
//            Log.e("MAYBE", "NULL");
//        } else {
//            Log.e("MAYBE", "NOT NULL");
//        }
//    }
//
//    private Integer generate() {
//        Random r = new Random(100);
//        if (r.nextInt() > 1000) {
//            return 1;
//        } else {
//            return null;
//        }
//    }
//
//    private void error(Throwable throwable) {
//        Log.d("ERROR", throwable.getMessage());
//    }
//
//    private void test(Integer i) {
//        Log.e("INTEGER", String.valueOf(i));
//    }

    }
}

