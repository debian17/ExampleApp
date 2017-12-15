package com.example.citilin.testapp.ui.test;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.notifications.MyService;
import com.example.citilin.testapp.ui.TestSecondActivity;
import com.example.citilin.testapp.ui.characters.Character;

public class TestFragment extends Fragment implements MyBroadcastReceiver.Receive {

//    private Button alertButton;
//    private ViewSwitcher viewSwitcher;
//    private Button switchViewButton;

    private Button bottomActivityStart;
    private Button tabbedActivityStart;

    private Button startServiceButton;
    private Button stopServiceButton;

    private Button handler;
    private TextView handlerText;
    private Handler myHandler;

    private Intent serviceIntent;

    private Button testLC;

    private BroadcastReceiver broadcastReceiver;
    private MyBroadcastReceiver myBroadcastReceiver;

    private MyService myService;
    private boolean mBound;

    private static boolean isReady = false;
    private static final Object barrier = new Object();

    public TestFragment() {
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder localBinder = (MyService.LocalBinder) iBinder;
            myService = localBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        //getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        //Log.d("TestFragment", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.d("TestFragment", "onStop");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBroadcastReceiver = new MyBroadcastReceiver(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                    handlerText.setText("ЗАРЯЖАЕТСЯ");
                } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                    handlerText.setText("НЕ ЗАРЯЖАЕТСЯ");
                }
            }
        };

//        LinkedList<String> linkedList = new LinkedList<>();
//
//        for (int i = 0; i < 10; i++) {
//            linkedList.add(String.valueOf(i));
//        }
//
//        ListIterator<String> listIterator = linkedList.listIterator(5);
//
//
//        for (int i = 0; i < linkedList.size(); i++) {
//            Log.e("LIST", linkedList.get(i));
//        }

        //Log.d("TestFragment", "onCreate");
//        myBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                testLC.setText("ЗАРЯДКА!");
//            }
//        };
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter globalIntentFilter = new IntentFilter();
        globalIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        globalIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(MyBroadcastReceiver.MY_ACTION);
        localIntentFilter.addAction(MyBroadcastReceiver.FROM_SERVICE);

        getContext().registerReceiver(broadcastReceiver, globalIntentFilter);

        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(myBroadcastReceiver, localIntentFilter);

        //Log.d("TestFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBroadcastReceiver);
        //Log.d("TestFragment", "onPause");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.d("TestFragment", "onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    private Handler.Callback hc = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Log.d("handleMessage", String.valueOf(message.getData().getInt("key")));
            Log.d("handleMessage", message.getData().getString("s"));
            handlerText.setText("asd");
            return false;
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        BlackBox blackBox = new BlackBox(1, "12");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                blackBox.do_it();
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                blackBox.prepare();
//            }
//        }).start();

        //Log.e("EQU", String.valueOf(blackBox.hashCode()) + "  " + String.valueOf(a.hashCode()));

        bottomActivityStart = (Button) view.findViewById(R.id.bottomBTN);
        tabbedActivityStart = (Button) view.findViewById(R.id.tabbedBTN);

        startServiceButton = (Button) view.findViewById(R.id.startServiceBTN);
        stopServiceButton = (Button) view.findViewById(R.id.stopServiceBTN);

        testLC = (Button) view.findViewById(R.id.activityTest);

        testLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TestSecondActivity.class);
                getContext().startActivity(intent);
            }
        });

        handler = (Button) view.findViewById(R.id.handlerBTN);
        handlerText = (TextView) view.findViewById(R.id.handlerTV);

        myHandler = new Handler(hc);

        handler.setOnClickListener(view1 -> new Thread(() -> {
            Bundle bundle = new Bundle();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    bundle.putInt("key", i);
                    bundle.putString("s", "STRING");
                    Message message = new Message();
                    message.setData(bundle);
                    Character character = new Character(1, "NAME", "PATH");
                    myHandler.obtainMessage(17, character);
                    myHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                LinkedList<Integer> linkedList = new LinkedList<Integer>();
//
//                for (int i = 0; i < 20; i++) {
//                    linkedList.add(i);
//                }
//
//                ListIterator<Integer> integerListIterator = linkedList.listIterator();
//
//                //Log.e("ILI", String.valueOf(integerListIterator.next()));
//                //integerListIterator.next();
//                //integerListIterator.remove();//first element
//
//                while (integerListIterator.hasNext()) {
//                    if (integerListIterator.next().equals(linkedList.get(10))) {
//                        for (int i = 0; i < 5; i++) {
//                            integerListIterator.add(i * 2);
//                        }
//                    }
//                }
//
//                for (int i = 0; i < linkedList.size(); i++) {
//                    Log.e("LIST", String.valueOf(linkedList.get(i)));
//                }

//                Intent intent = new Intent(getContext(), TouchEventTestActivity.class);
//                getContext().startActivity(intent);
//                Intent intent = new Intent(MyBroadcastReceiver.MY_ACTION);
//                intent.putExtra(MyBroadcastReceiver.MESSAGE, "Сообщение");
//                LocalBroadcastManager.getInstance(getContext())
//                        .sendBroadcast(intent);
//                serviceIntent = new Intent(getContext(), MyService.class);
//                serviceIntent.putExtra("key", "bla bla");
//                getContext().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

//                Intent intent = new Intent(getContext(), MyIntentService.class);
//                BlackBox a = new BlackBox(1, "123");
//
//                intent.putExtra("input", a);
//                getContext().startService(intent);

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        synchronized (barrier) {
//                            while (!isReady) {
//                                try {
//                                    barrier.wait();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.e("DO", "ACTION");
//                        }
//                    }
//                }).start();
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        synchronized (barrier) {
//                            Log.e("DO", "PREPARE");
//                            try {
//                                Thread.sleep(5000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            isReady = true;
//                            barrier.notify();
//                        }
//                    }
//                }).start();
                //getContext().unbindService(serviceConnection);
            }
        });

        bottomActivityStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BottomNavigationActivity.class);
                getContext().startActivity(intent);
            }
        });

        tabbedActivityStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        alertButton = (Button) view.findViewById(R.id.btnAlertDialog);
//        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.viewSwitcher);
//        switchViewButton = (Button) view.findViewById(R.id.btnSwitch);
//
//        String[] arr = {"Один", "Два", "Три", "Четыре", "Пять", "Шесть", "Семь"};
//
//        alertButton.setOnClickListener(view1 -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Заголовок")
//                    .setPositiveButton("Позитив", (dialogInterface, i) ->
//                            Toast.makeText(getContext(), "Позитиная кнопка", Toast.LENGTH_SHORT)
//                                    .show())
//                    .setNegativeButton("Негатив", (dialogInterface, i) ->
//                            Toast.makeText(getContext(), "Негативная кнопка", Toast.LENGTH_SHORT)
//                                    .show())
//                    .setSingleChoiceItems(arr, -1, (dialogInterface, i) -> {
//                        Toast.makeText(getContext(), arr[i], Toast.LENGTH_SHORT)
//                                .show();
//                    });
////                    .setItems(arr, (dialogInterface, i) ->
////                            Toast.makeText(getContext(), arr[i], Toast.LENGTH_SHORT)
////                                    .show());
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        });
//
//
//        switchViewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewSwitcher.showNext();
//            }
//        });
    }

    @Override
    public void myAction(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateProgress(Intent intent) {

    }
}
