# Android开发笔记：Broadcast(广播)

## 广播概述：
 生活中广播主要起到消息传递的作用，与我们平日生活中的广播相似，Android中的广播机制主要是在系统和应用之间，应用和应用之间传递消息。系统和应用都能够发送广播消息，系统会在一些特殊事件发生时发送广播(比如开机完成，充电等),应用也能发送一些自定义广播和接收自己感兴趣的广播消息。           
 **不过由于系统安全和用户体验的原因，现在的广播已经有了诸多限制。**

## 广播的作用：     
 应用可以注册广播接收器，以此来接收广播消息，在接受到广播消息之后，就可以进行一些响应的操作。

## 如何接收广播：
 刚刚提到，要接收广播就要注册(创建)广播接收器。这里主要有两种方式来注册广播接收器，分别是动态注册和静态注册。
 ### 动态注册：
    顾名思义，动态注册就是在应用运行的过程中动态地注册广播接收器，这种方式注册的
    广播接收器比较灵活且限制较少。不过一定要记住，动态注册的广播接收器最后一定要
    注销。
  * 1.创建自己的接收器类继承广播接收器，这里上第一行代码的示例，接收时间的改变广
  播：
            public class TimeChangeReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent){
                Toast.makeText(context,"Time has changed!",Toast.
                LENGTH_SHORT).show();
                }
            }
    
    这里的处理逻辑很简单，就是在接收到广播之后弹出一条Toast提示。

   * 2.在活动中注册该接收器：

            public class MainActivity extends AppCompatActivity {

            private IntentFilter filter;

            private TimeChangeReceiver receiver;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                filter = new IntentFilter();
                filter.addAction("android.intent.action.TIME_TICK");
                receiver = new TimeChangeReceiver();
                registerReceiver(receiver,filter);
            }

            @Override
            protected void onDestroy(){
                super.onDestroy();
                unregisterReceiver(receiver);
            }
          } 
   此处注册接收器的核心方法为registerReceiver()方法，该方法接收两个参数，分别为
   一个具体的接收器实例和一个Intent过滤器实例。所以我们先创建两个响应的实例，并
   向过滤器添加过滤条件，使其能够接受时间改变的广播。最后调用注册方法实现动态注册
   最后不要忘了在销毁方法中将接收器给注销了。   
   这样每隔一分钟，应用就能接收一条广播。
 ### 静态注册： 
    接下来就是静态注册了，其和动态注册相比的好处就是即使应用未启动，也能接收广
    播，不过自从Android8.0系统之后，所有隐式广播都不允许使用静态注册的方法来接收
    了。隐式广播就是没有明确发给哪些应用的广播，不过仍然有少数系统发出的隐式广播
    允许用静态注册的方式来接收。    

 静态注册主要是修改清单文件中的内容，不过仍然需要一个自己的接收器类，这里我们沿用
 前面的接收器。在开机完成后接收开机广播。   
    修改xml清单文件：   

        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            package="com.example.a825broadcast">
            <uses-permission android:name="android.permission.
            RECEIVE_BOOT_COMPLETED"/>
            <application
                android:allowBackup="true"
                android:dataExtractionRules="@xml/data_extraction_rules"
                android:fullBackupContent="@xml/backup_rules"
                android:icon="@mipmap/ic_launcher"
                android:label="@string/app_name"
                android:roundIcon="@mipmap/ic_launcher_round"
                android:supportsRtl="true"
                android:theme="@style/Theme.825Broadcast"
                tools:targetApi="31">
                <activity
                    android:name=".MainActivity"
                    android:exported="true">
                    <intent-filter>
                        <action android:name="android.intent.action.MAIN" />

                        <category android:name="android.intent.category.
                        LAUNCHER" />
                    </intent-filter>
                </activity>
                <receiver
                    android:name=".TimeChangeReceiver"
                    android:exported="true"
                    android:enabled="true"
                    >
                    <intent-filter>
                        <action android:name="android.intent.action.
                        BOOT_COMPLETED"/>
                    </intent-filter>
                </receiver>
            </application>

        </manifest>

这里我们主要先修改了权限：
        
    <uses-permission android:name="android.permission.
            RECEIVE_BOOT_COMPLETED"/>
使本应用能够接收到开机广播。然后在application标签中注册了接收器：
           
           <receiver
                    android:name=".TimeChangeReceiver"
                    android:exported="true"
                    android:enabled="true"
                    >
                    <intent-filter>
                        <action android:name="android.intent.action.
                        BOOT_COMPLETED"/>
                    </intent-filter>
                </receiver>     

其中的name属性来指定具体的接收器类名，点号前省略了包名。exproted属性来指定该接收
器是否能被其他应用检测。enabled则来使能接收器。  
后面之中的< intent-filter >标签来指定能响应的系统行动，此处为开机完成动作。  

理论上，这样操作下来你的应用就能在开机完成后接收一条Toast消息了，**不过，经过我的试验，还是有相当一部分手机无法接收该消息，这时我们需要去设置里把我们写的应用的开机自启动权限给开了**，这样重启后大部分就可以接受到广播了。

## 如何发送广播：
    具体的广播主要有两种，分别为标准广播和有序广播。

### 标准广播：
>按随机的顺序向所有接收器发送广播。这称为常规广播。这种方法效率更高，但也意味
    着接收器无法从其他接收器读取结果，无法传递从广播中收到的数据，也无法中止广
    播。

    对应的是sendBroadcast(Intent)方法。
### 有序广播：
>一次向一个接收器发送广播。当接收器逐个顺序执行时，接收器可以向下传递结果，也
    可以完全中止广播，使其不再传递给其他接收器。接收器的运行顺序可以通过匹配的
    intent-filter 的 android:priority 属性来控制；具有相同优先级的接收器将按随
    机顺序运行。

    对应的是sendOrderedBroadcast(Intent, String)方法。

### 本地广播：
    除了上面两种之外，还有本地广播的概念。对应LocalBroadcastManager.sendBroadcast方法。
>会将广播发送给与发送器位于同一应用中的接收器。如果您不需要跨应用发送广播，请使用本地广播。这种实现方法的效率更高（无需进行进程间通信），而且您无需担心其他应用在收发您的广播时带来的任何安全问题。

### 发送标准广播：
    发送的步骤主要是构建Intent对象 + 调用响应的发送广播方法实现的。一般情况下，
    我们发送的自定义广播都是隐式广播，所以静态注册的广播无法接收。如果要想接收，
    就要调用setPackage方法将隐式广播变为显示广播，setPackage方法接收一个包名，该包名就是需要接收广播的应用的包名。

* 1.构建Intent实例来设置广播名称    
        
        我在一个按钮上设置了监听方法：
        public void onClick(View v) {
                Intent intent = new Intent("com.example.825Boradcast.
                MY_CAST");
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
            }
    * 这里首先构建了一个Intent并且指定Intent的广播名称为"com.example.825Boradcast.MY_CAST"

    * 然后通过setPackage方法指定了程序包名为本应用的包名，即显式指定了要发送到的
    应用，这样静态广播就可以接收到该广播了。

    * 最后调用发送标准广播对应的sendBroadcast方法，这样就发出了一个广播名为
    "com.example.825Boradcast.MY_CAST"的标准广播。点击按钮即可接收到一次广播。

* 2.修改xml清单文件，增加接收器能接收到的广播名称，还是在上面的基础上进行修改：

        <receiver
                android:name=".TimeChangeReceiver"
                android:exported="true"
                android:enabled="true"
                >
                <intent-filter>
                    <action android:name="android.intent.action.
                    BOOT_COMPLETED"/>
                    <action android:name="com.example.825Boradcast.
                    MY_CAST" /> //增加了自定义广播的名称
                </intent-filter>
            </receiver>
这样每次点击按钮应用即可进行一次监听器中的onReceive()方法。

### 发送有序广播：   
    这里我们就在一个应用里发送有序广播，在该应用里分别设置两个接收器来处理广播。  
* 1.新建另一个接收器类，如果是在Android Studio的话，可以在包下的Java包下直接新
建Broadcast，系统会自动帮你在清单文件注册：

    然后还是老办法，重写onReceive方法：

        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Receive in SecondReceiver",Toast.
            LENGTH_SHORT).show();
        }

* 2.在清单文件中增加第二个接收器的接收广播名称，和发送标准广播是一样的，这里不过多赘述。

* 3.调用对应的SendOrderedBroadcast方法来发送有序广播，该方法第一个参数依然是Intent对象，第二个则是与权限相关的字符串，这里我们传入null。修改前面按钮上的方
法：    

        public void onClick(View v) {
                    Intent intent = new Intent("com.example.825Boradcast.
                    MY_CAST");
                    intent.setPackage(getPackageName());
                    //sendBroadcast(intent);
                    sendOrderedBroadcast(intent,null);
                }

这样点击按钮后就能分别有两条Toast消息显示了。

#### 截断广播：
   因为有序广播是有序地一次一个地传给接收器的，所以接收器可以在接收到广播后  
   就将广播给拦截，这样后续的接收器就无法接收到该广播了。而接收器接收的顺序  
   是由清单文件中receiver标签中的intent标签中的priority优先级决定的，我们先将  
   第一个广播的优先级设置为100，最高：   

        <intent-filter android:priority="100">
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action android:name="com.example.825Boradcast.MY_CAST" />
            </intent-filter>
   然后在第一个广播的接收方法中将广播截断：

        public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"Time has changed!",Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }

  这时我们点击按钮，就只有第一个接收器发出的Toast消息了，这样广播就成功被我们截断了。

  ### 信息的传递：
  * 1.如果信息是由原始的Intent，即发送广播前构造的Intent发来的，我们在接收方
  法中就可以用响应的getExtra方法获得。

  * 2.信息也可以由上级接收器传来，上级可以调用setResultData等方法给下级传递  
  信息，下级调用相应的getResult等方法接收上级的消息。 

    上级给下级传递：    

             public void onReceive(Context context, Intent intent){
            // intent.putExtra("data","GGG");
                Toast.makeText(context,"Time has changed!",Toast.
                LENGTH_SHORT).show();
                setResultData("GGGGG");
                //abortBroadcast();
            }  

    下级接收：  

            public void onReceive(Context context, Intent intent) {
            String data = getResultData();
            Toast.makeText(context,"Receive in SecondReceiver" + data,Toast.
            LENGTH_SHORT).show();
         }        
        

