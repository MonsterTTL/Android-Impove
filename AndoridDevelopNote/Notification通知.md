# Android�����ʼ�:Notification(֪ͨ)�ļ�Ӧ��

## ��飺  

      ֪ͨ��ָ Android ����Ӧ�õĽ���֮����ʾ����Ϣ��ּ�����û��ṩ���ѡ�������
      �˵�ͨ����Ϣ����Ӧ���е�����ʵʱ��Ϣ���û����Ե㰴֪ͨ����Ӧ�ã���ֱ�Ӵ�
      ֪ͨ��ִ�в�����

 ��ͼ��Ϊ֪ͨ��


 ## ��δ���֪ͨ��

 ### ֪ͨ������
 һ��ʼAndroidϵͳ��û��֪ͨ�����ĸ���������������Ӧ�ö����ϵص���֪ͨ����󽵵���Android�û����û����顣��ˣ�Android8.0��������֪ͨ�����ĸ��

 Android8.0��ÿһ��֪ͨ�������Ӧһ��֪ͨ������һ��Ӧ��Ҳ���Դ������֪ͨ����������һ��������ɣ���Щ�����Ŀ���Ȩ�͹��û������ˣ��û��������ɵ�ѡ���Ƿ�Ҫ������Щ������

 ### ���岽�裺

 - 1.������Ҫ����һ��NotificationManager��֪ͨ���й����������ͨ������Context��getSystemService()������ȡ��getSystemService()ͨ������һ���ַ�����������ȷ����ȡϵͳ���ĸ������������Ǵ���Context.NOTIFICATION_SERVICE���ɡ�    
        
        ����Ϊ��
            NotificationManager manager = (NotificationManager) 
            getSystemService(NOTIFICATION_SERVICE);
 - 2.������ʹ��NotificationChannel�๹��һ��֪ͨ������������֮ǰ��    NotificationManager ʵ���� createNotificationChannel����������ɴ�����      

        ����Ϊ��
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.
        VERSION_CODES.O) {
            //�������ȵ��ж�ϵͳ�İ汾������8.0�Ժ����Ҫ����֪ͨ����
            NotificationChannel channel = new NotificationChannel("normal",
            "Normal",NotificationManager.IMPORTANCE_HIGH);
            //����һ��Channelʵ�������������������ֱ�������ID���������ƣ���Ҫ�̶ȡ���������IDֻ��Ҫ��֤ȫ��Ψһ����
            manager.createNotificationChannel(channel);
            //����Manager��createNotificationChannel��������������Ĵ���
            }

 - 3.���崴��Notificationʵ������������ز�����
    ������Ҫһ��Builder������������Notification���󣬿��ǵ����������⣬����ʹ��AndroidX���е�NotificationCompat��������

            Notification notification = new NotificationCompat.Builder(this,
            "normal");

    �˴��÷�����Ҫ���������������ֱ���������context������ID���������ڶ�������������ã���ȻҲ�����ڴ�����ʱ���һ�����ã�

            Notification notification = new NotificationCompat.Builder(this,
            "normal")
                        .setContentTitle("This is a content title")
                        .setContentText("This is a content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource
                        (getResources(),
                                R.mipmap.ic_launcher))
                        .build();  

        Сͼ�꣺�����ṩ��ͨ�� setSmallIcon() �������á�  
        Ӧ�����ƣ���ϵͳ�ṩ��  
        ʱ�������ϵͳ�ṩ����������ʹ�� setWhen() �滻������ʹ�� setShowWhen(false) ��������   
        ��ͼ�꣺��ѡ���ݣ�ͨ����������ϵ����Ƭ������������Ӧ��ͼ�꣩��ͨ�� setLargeIcon() �������á�  
        ���⣺��ѡ���ݣ�ͨ�� setContentTitle() �������á�  
        �ı�����ѡ���ݣ�ͨ�� setContentText() �������á�   
        ֪ͨ���ȼ���ͨ�� setPriority() ���á����ȼ�ȷ��֪ͨ�� Android 7.1 �͸��Ͱ汾�ϵĸ��ų̶ȡ������� Android 8.0 �͸��߰汾����������������Ҫ�ԣ�

 - 4.������NotificationManager��notify��������֪ͨ��ʾ���÷�����Ҫ����������������һ����֪ͨID���ڶ����Ǿ����notificationʵ����

        manager.notify(5,notification);

 �����ID�������֪ͨ������ID������ͬ����������ʾ֪ͨʱ��ID��
 �������Ѿ�ʵ������򵥵�һ��֪ͨ��ʹ�á�  **��Ҫע����ǣ�ʵ�������֪ͨ�п��ܲ���ʾ��������ϵͳ��Ȩ�������н����Գ�������Ȩ�޴򿪼��ɡ�**                  


 ## ֪ͨ���õ�һЩ��չ��  

 ### ��ʾ�������ı�(�ٷ�����)��
        Ĭ������£�֪ͨ���ı����ݻᱻ�ض��Է���һ�С��������Ҫ������֪ͨ������ʹ�� setStyle() �����ʽģ�������ÿ�չ����֪ͨ��
        ���磬���´���ᴴ��������ı�����  

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...
                    "))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

 ### ����֪ͨ�ĵ���¼���   
    �����Ҫ�ڵ��֪ͨʱ��ת��һ�����������Խ���intentʵ����ת�� 
        
    Intent intent = new Intent(this, AlertDetails.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
    intent, 0);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);
    

����getActivity���������ĸ��������ֱ���Context�����ģ������룬�����Intentʵ����flag���������Ǽ򵥵�����Ϊ�㣬�����������״̬��
- FLAG_ONE_SHOT
    ��ǰ������PendingIntentֻ�ܱ�ʹ��һ�Σ�Ȼ�����ͻᱻ�Զ�cancel���������������ͬ��PendingIntent����ô���ǵ�send�����ͻ����ʧ�ܡ�����֪ͨ����Ϣ��˵������ô˱��λ����ôͬ���֪ͨҲֻ��ʹ��һ�Σ�������֪ͨ�������޷��򿪡�  

- FLAG_NO_CREATE
    ��ǰ������PendingIntent�������������������ǰPendingIntent֮ǰ�����ڣ���ôgetActivity��getService��getBroadcast������ֱ�ӷ���null������ȡPendingIntentʧ�ܡ�������λ���ټ������޷�����ʹ�ã�������ճ�����������û��̫���ʹ������   

- FLAG_CANCEL_CURRENT
    ��ǰ������PendingIntent����Ѿ����ڣ���ô���Ƕ��ᱻcancle��Ȼ��ϵͳ�ᴴ��һ���µ�PendingIntent������֪ͨ����Ϣ��˵����ô��cancel����Ϣ�������޷��򿪡�   

- FLAG_UPDATE_CURRENT
    ��ǰ������PendingIntent����Ѿ����ڣ���ô���Ƕ��ᱻ���£������ǵ�Intent�е�Extras�ᱻ�滻�����µġ�


    
           
     



    