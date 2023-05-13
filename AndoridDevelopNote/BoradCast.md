# Android�����ʼǣ�Broadcast(�㲥)

## �㲥������
 �����й㲥��Ҫ����Ϣ���ݵ����ã�������ƽ�������еĹ㲥���ƣ�Android�еĹ㲥������Ҫ����ϵͳ��Ӧ��֮�䣬Ӧ�ú�Ӧ��֮�䴫����Ϣ��ϵͳ��Ӧ�ö��ܹ����͹㲥��Ϣ��ϵͳ����һЩ�����¼�����ʱ���͹㲥(���翪����ɣ�����),Ӧ��Ҳ�ܷ���һЩ�Զ���㲥�ͽ����Լ�����Ȥ�Ĺ㲥��Ϣ��           
 **��������ϵͳ��ȫ���û������ԭ�����ڵĹ㲥�Ѿ�����������ơ�**

## �㲥�����ã�     
 Ӧ�ÿ���ע��㲥���������Դ������չ㲥��Ϣ���ڽ��ܵ��㲥��Ϣ֮�󣬾Ϳ��Խ���һЩ��Ӧ�Ĳ�����

## ��ν��չ㲥��
 �ո��ᵽ��Ҫ���չ㲥��Ҫע��(����)�㲥��������������Ҫ�����ַ�ʽ��ע��㲥���������ֱ��Ƕ�̬ע��;�̬ע�ᡣ
 ### ��̬ע�᣺
    ����˼�壬��̬ע�������Ӧ�����еĹ����ж�̬��ע��㲥�����������ַ�ʽע���
    �㲥�������Ƚ���������ƽ��١�����һ��Ҫ��ס����̬ע��Ĺ㲥���������һ��Ҫ
    ע����
  * 1.�����Լ��Ľ�������̳й㲥�������������ϵ�һ�д����ʾ��������ʱ��ĸı��
  ����
            public class TimeChangeReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent){
                Toast.makeText(context,"Time has changed!",Toast.
                LENGTH_SHORT).show();
                }
            }
    
    ����Ĵ����߼��ܼ򵥣������ڽ��յ��㲥֮�󵯳�һ��Toast��ʾ��

   * 2.�ڻ��ע��ý�������

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
   �˴�ע��������ĺ��ķ���ΪregisterReceiver()�������÷������������������ֱ�Ϊ
   һ������Ľ�����ʵ����һ��Intent������ʵ�������������ȴ���������Ӧ��ʵ������
   ���������ӹ���������ʹ���ܹ�����ʱ��ı�Ĺ㲥��������ע�᷽��ʵ�ֶ�̬ע��
   ���Ҫ���������ٷ����н���������ע���ˡ�   
   ����ÿ��һ���ӣ�Ӧ�þ��ܽ���һ���㲥��
 ### ��̬ע�᣺ 
    ���������Ǿ�̬ע���ˣ���Ͷ�̬ע����ȵĺô����Ǽ�ʹӦ��δ������Ҳ�ܽ��չ�
    ���������Դ�Android8.0ϵͳ֮��������ʽ�㲥��������ʹ�þ�̬ע��ķ���������
    �ˡ���ʽ�㲥����û����ȷ������ЩӦ�õĹ㲥��������Ȼ������ϵͳ��������ʽ�㲥
    �����þ�̬ע��ķ�ʽ�����ա�    

 ��̬ע����Ҫ���޸��嵥�ļ��е����ݣ�������Ȼ��Ҫһ���Լ��Ľ������࣬������������
 ǰ��Ľ��������ڿ�����ɺ���տ����㲥��   
    �޸�xml�嵥�ļ���   

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

����������Ҫ���޸���Ȩ�ޣ�
        
    <uses-permission android:name="android.permission.
            RECEIVE_BOOT_COMPLETED"/>
ʹ��Ӧ���ܹ����յ������㲥��Ȼ����application��ǩ��ע���˽�������
           
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

���е�name������ָ������Ľ��������������ǰʡ���˰�����exproted������ָ���ý���
���Ƿ��ܱ�����Ӧ�ü�⡣enabled����ʹ�ܽ�������  
����֮�е�< intent-filter >��ǩ��ָ������Ӧ��ϵͳ�ж����˴�Ϊ������ɶ�����  

�����ϣ����������������Ӧ�þ����ڿ�����ɺ����һ��Toast��Ϣ�ˣ�**�����������ҵ����飬�������൱һ�����ֻ��޷����ո���Ϣ����ʱ������Ҫȥ�����������д��Ӧ�õĿ���������Ȩ�޸�����**������������󲿷־Ϳ��Խ��ܵ��㲥�ˡ�

## ��η��͹㲥��
    ����Ĺ㲥��Ҫ�����֣��ֱ�Ϊ��׼�㲥������㲥��

### ��׼�㲥��
>�������˳�������н��������͹㲥�����Ϊ����㲥�����ַ���Ч�ʸ��ߣ���Ҳ��ζ
    �Ž������޷���������������ȡ������޷����ݴӹ㲥���յ������ݣ�Ҳ�޷���ֹ��
    ����

    ��Ӧ����sendBroadcast(Intent)������
### ����㲥��
>һ����һ�����������͹㲥�������������˳��ִ��ʱ���������������´��ݽ����Ҳ
    ������ȫ��ֹ�㲥��ʹ�䲻�ٴ��ݸ�������������������������˳�����ͨ��ƥ���
    intent-filter �� android:priority ���������ƣ�������ͬ���ȼ��Ľ�����������
    ��˳�����С�

    ��Ӧ����sendOrderedBroadcast(Intent, String)������

### ���ع㲥��
    ������������֮�⣬���б��ع㲥�ĸ����ӦLocalBroadcastManager.sendBroadcast������
>�Ὣ�㲥���͸��뷢����λ��ͬһӦ���еĽ����������������Ҫ��Ӧ�÷��͹㲥����ʹ�ñ��ع㲥������ʵ�ַ�����Ч�ʸ��ߣ�������н��̼�ͨ�ţ������������赣������Ӧ�����շ����Ĺ㲥ʱ�������κΰ�ȫ���⡣

### ���ͱ�׼�㲥��
    ���͵Ĳ�����Ҫ�ǹ���Intent���� + ������Ӧ�ķ��͹㲥����ʵ�ֵġ�һ������£�
    ���Ƿ��͵��Զ���㲥������ʽ�㲥�����Ծ�̬ע��Ĺ㲥�޷����ա����Ҫ����գ�
    ��Ҫ����setPackage��������ʽ�㲥��Ϊ��ʾ�㲥��setPackage��������һ���������ð���������Ҫ���չ㲥��Ӧ�õİ�����

* 1.����Intentʵ�������ù㲥����    
        
        ����һ����ť�������˼���������
        public void onClick(View v) {
                Intent intent = new Intent("com.example.825Boradcast.
                MY_CAST");
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
            }
    * �������ȹ�����һ��Intent����ָ��Intent�Ĺ㲥����Ϊ"com.example.825Boradcast.MY_CAST"

    * Ȼ��ͨ��setPackage����ָ���˳������Ϊ��Ӧ�õİ���������ʽָ����Ҫ���͵���
    Ӧ�ã�������̬�㲥�Ϳ��Խ��յ��ù㲥�ˡ�

    * �����÷��ͱ�׼�㲥��Ӧ��sendBroadcast�����������ͷ�����һ���㲥��Ϊ
    "com.example.825Boradcast.MY_CAST"�ı�׼�㲥�������ť���ɽ��յ�һ�ι㲥��

* 2.�޸�xml�嵥�ļ������ӽ������ܽ��յ��Ĺ㲥���ƣ�����������Ļ����Ͻ����޸ģ�

        <receiver
                android:name=".TimeChangeReceiver"
                android:exported="true"
                android:enabled="true"
                >
                <intent-filter>
                    <action android:name="android.intent.action.
                    BOOT_COMPLETED"/>
                    <action android:name="com.example.825Boradcast.
                    MY_CAST" /> //�������Զ���㲥������
                </intent-filter>
            </receiver>
����ÿ�ε����ťӦ�ü��ɽ���һ�μ������е�onReceive()������

### ��������㲥��   
    �������Ǿ���һ��Ӧ���﷢������㲥���ڸ�Ӧ����ֱ���������������������㲥��  
* 1.�½���һ���������࣬�������Android Studio�Ļ��������ڰ��µ�Java����ֱ����
��Broadcast��ϵͳ���Զ��������嵥�ļ�ע�᣺

    Ȼ�����ϰ취����дonReceive������

        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Receive in SecondReceiver",Toast.
            LENGTH_SHORT).show();
        }

* 2.���嵥�ļ������ӵڶ����������Ľ��չ㲥���ƣ��ͷ��ͱ�׼�㲥��һ���ģ����ﲻ����׸����

* 3.���ö�Ӧ��SendOrderedBroadcast��������������㲥���÷�����һ��������Ȼ��Intent���󣬵ڶ���������Ȩ����ص��ַ������������Ǵ���null���޸�ǰ�水ť�ϵķ�
����    

        public void onClick(View v) {
                    Intent intent = new Intent("com.example.825Boradcast.
                    MY_CAST");
                    intent.setPackage(getPackageName());
                    //sendBroadcast(intent);
                    sendOrderedBroadcast(intent,null);
                }

���������ť����ֱܷ�������Toast��Ϣ��ʾ�ˡ�

#### �ضϹ㲥��
   ��Ϊ����㲥�������һ��һ���ش����������ģ����Խ����������ڽ��յ��㲥��  
   �ͽ��㲥�����أ����������Ľ��������޷����յ��ù㲥�ˡ������������յ�˳��  
   �����嵥�ļ���receiver��ǩ�е�intent��ǩ�е�priority���ȼ������ģ������Ƚ�  
   ��һ���㲥�����ȼ�����Ϊ100����ߣ�   

        <intent-filter android:priority="100">
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <action android:name="com.example.825Boradcast.MY_CAST" />
            </intent-filter>
   Ȼ���ڵ�һ���㲥�Ľ��շ����н��㲥�ضϣ�

        public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"Time has changed!",Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }

  ��ʱ���ǵ����ť����ֻ�е�һ��������������Toast��Ϣ�ˣ������㲥�ͳɹ������ǽض��ˡ�

  ### ��Ϣ�Ĵ��ݣ�
  * 1.�����Ϣ����ԭʼ��Intent�������͹㲥ǰ�����Intent�����ģ������ڽ��շ�
  ���оͿ�������Ӧ��getExtra������á�

  * 2.��ϢҲ�������ϼ��������������ϼ����Ե���setResultData�ȷ������¼�����  
  ��Ϣ���¼�������Ӧ��getResult�ȷ��������ϼ�����Ϣ�� 

    �ϼ����¼����ݣ�    

             public void onReceive(Context context, Intent intent){
            // intent.putExtra("data","GGG");
                Toast.makeText(context,"Time has changed!",Toast.
                LENGTH_SHORT).show();
                setResultData("GGGGG");
                //abortBroadcast();
            }  

    �¼����գ�  

            public void onReceive(Context context, Intent intent) {
            String data = getResultData();
            Toast.makeText(context,"Receive in SecondReceiver" + data,Toast.
            LENGTH_SHORT).show();
         }        
        

