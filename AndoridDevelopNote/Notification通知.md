# Android开发笔记:Notification(通知)的简单应用

## 简介：  

      通知是指 Android 在您应用的界面之外显示的消息，旨在向用户提供提醒、来自他
      人的通信信息或您应用中的其他实时信息。用户可以点按通知来打开应用，或直接从
      通知中执行操作。

 如图即为通知：


 ## 如何创建通知：

 ### 通知渠道：
 一开始Android系统并没有通知渠道的概念，但后来由于许多应用都不断地弹出通知，大大降低了Android用户的用户体验。因此，Android8.0中引入了通知渠道的概念。

 Android8.0后，每一条通知都必须对应一个通知渠道，一个应用也可以创建多个通知渠道，但是一旦创建完成，这些渠道的控制权就归用户所有了，用户可以自由地选择是否要开启这些渠道。

 ### 具体步骤：

 - 1.首先需要创建一个NotificationManager对通知进行管理。具体可以通过调用Context的getSystemService()方法获取。getSystemService()通过接受一个字符串参数用于确定获取系统的哪个程序，这里我们传入Context.NOTIFICATION_SERVICE即可。    
        
        具体为：
            NotificationManager manager = (NotificationManager) 
            getSystemService(NOTIFICATION_SERVICE);
 - 2.接下来使用NotificationChannel类构建一个通知渠道，并调用之前的    NotificationManager 实例的 createNotificationChannel方法即可完成创建。      

        具体为：
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.
        VERSION_CODES.O) {
            //这里首先得判断系统的版本，若是8.0以后才需要创建通知渠道
            NotificationChannel channel = new NotificationChannel("normal",
            "Normal",NotificationManager.IMPORTANCE_HIGH);
            //创建一个Channel实例，接收三个参数，分别是渠道ID，渠道名称，重要程度。其中渠道ID只需要保证全局唯一即可
            manager.createNotificationChannel(channel);
            //调用Manager的createNotificationChannel方法，完成渠道的创建
            }

 - 3.具体创建Notification实例，并设置相关参数。
    首先需要一个Builder构造器来创建Notification对象，考虑到兼容性问题，我们使用AndroidX库中的NotificationCompat来创建：

            Notification notification = new NotificationCompat.Builder(this,
            "normal");

    此处该方法主要接收两个参数，分别是上下文context和渠道ID。接下来在对其参数进行配置，当然也可以在创建的时候就一起配置：

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

        小图标：必须提供，通过 setSmallIcon() 进行设置。  
        应用名称：由系统提供。  
        时间戳：由系统提供，但您可以使用 setWhen() 替换它或者使用 setShowWhen(false) 隐藏它。   
        大图标：可选内容（通常仅用于联系人照片，请勿将其用于应用图标），通过 setLargeIcon() 进行设置。  
        标题：可选内容，通过 setContentTitle() 进行设置。  
        文本：可选内容，通过 setContentText() 进行设置。   
        通知优先级：通过 setPriority() 设置。优先级确定通知在 Android 7.1 和更低版本上的干扰程度。（对于 Android 8.0 和更高版本，必须设置渠道重要性）

 - 4.最后调用NotificationManager的notify方法来让通知显示，该方法主要接收两个参数，第一个是通知ID，第二个是具体的notification实例：

        manager.notify(5,notification);

 这里的ID与上面的通知渠道的ID并不相同，这里是显示通知时的ID。
 这样就已经实现了最简单的一个通知的使用。  **需要注意的是，实际情况中通知有可能不显示，可以在系统的权限设置中将测试程序的相关权限打开即可。**                  


 ## 通知设置的一些拓展：  

 ### 显示更长的文本(官方例程)：
        默认情况下，通知的文本内容会被截断以放在一行。如果您想要更长的通知，可以使用 setStyle() 添加样式模板来启用可展开的通知。
        例如，以下代码会创建更大的文本区域：  

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...
                    "))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

 ### 设置通知的点击事件：   
    如果想要在点击通知时跳转到一个活动，这里可以借由intent实现跳转： 
        
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
    

这里getActivity方法接收四个参数，分别是Context上下文，请求码，具体的Intent实例，flag，这里我们简单地设置为零，其具体有四种状态：
- FLAG_ONE_SHOT
    当前描述的PendingIntent只能被使用一次，然后它就会被自动cancel，如果后续还有相同的PendingIntent，那么它们的send方法就会调用失败。对于通知栏消息来说，如果用此标记位，那么同类的通知也只能使用一次，后续的通知单机后将无法打开。  

- FLAG_NO_CREATE
    当前描述的PendingIntent不会主动创建，如果当前PendingIntent之前不存在，那么getActivity、getService和getBroadcast方法会直接返回null，即获取PendingIntent失败。这个标记位很少见，它无法单独使用，因此在日常开发中它并没有太多的使用意义   

- FLAG_CANCEL_CURRENT
    当前描述的PendingIntent如果已经存在，那么它们都会被cancle，然后系统会创建一个新的PendingIntent。对于通知栏消息来说，那么被cancel的消息单击后将无法打开。   

- FLAG_UPDATE_CURRENT
    当前描述的PendingIntent如果已经存在，那么它们都会被更新，即它们的Intent中的Extras会被替换成最新的。


    
           
     



    