# Android开发笔记：Intent的初步使用

## Intent作用的概述：
  Intent是一个消息传递对象，主要是用于各种活动之间的跳转（主活动跳转到其他的活动），相当于各种组件之间通信的媒介。
## Intent的基本用例:
* 1.启动活动（Activity）
* 2.启动服务（Service） 
* 3.传递广播（Broadcast）

## Intent的两种类型  
* 显式Intent：通过提供目标应用的软件包名称或完全限定的组件类名来指定可处理 Intent 的应用。通常，您会在自己的应用中使用显式 Intent 来启动组件，这是因为您知道要启动的 Activity 或服务的类名。例如，您可能会启动您应用内的新 Activity 以响应用户操作，或者启动服务以在后台下载文件。  

* 隐式Intent：不会指定特定的组件，而是声明要执行的常规操作，从而允许其他应用中的组件来处理。例如，如需在地图上向用户显示位置，则可以使用隐式 Intent，请求另一具有此功能的应用在地图上显示指定的位置。  

## Intent过滤器：
- 用于隐式Intent中。Intent 过滤器是应用清单文件（AndroidManifest.xml）中的一个表达式，用于指定该组件要接收的 Intent 类型。比如，可以在清单文件中具体的Activity标签中为该活动添加Intent过滤器，使其他组件可以通过这个过滤器来启动这个活动。**如果没有为该活动声明任何过滤器，则该活动只能通过显式Intent启动**。  

- 如果多个 Intent 过滤器兼容，则系统会显示一个对话框，支持用户选取要使用的应用。 


## Intent启动Activity 的 两种方式：  

### * 1.显式启动：    
  即明确要启动的具体活动，这种方式相对来说更为简洁明了。**需要注意的是，Intent在启动 Service 时，应始终指定组件名称。否则，您无法确定哪项服务会响应 Intent，且用户无法看到哪项服务已启动。**   

  - 1.构建显式Intent：  
    Intent(Context, Class) 构造函数分别为应用和组件提供 Context 和 Class 对象。第一个Context是要启动的活动类的上下文，在用前一个活动启动另一个活动时，只要传入前一个活动的示例就行了。第二个为要启动的活动的类。  

  - 2.对Intent进行需要的操作，比如传递的数据等。  

  - 3.调用startActivity方法启动活动。（或者startActivityForResult方法）.  

    - 示例：    
  
  ---------------------------------------------------------------------
    Intent newIntent = new Intent(this, NextActivity.class);
    newIntent.putExtra(键,值);//将值存入，并将键和值对应起来  
    startActivity(newIntent);//启动下一个活动


### * 2.隐式启动：  
   用户可能没有任何应用处理您发送到 startActivity() 的隐式 Intent。或者，由于配置文件限制或管理员执行的设置，可能无法访问应用。如果发生这样的情况，调用失败，应用也会崩溃。要验证 Activity 是否会接收 Intent，请对 Intent 对象调用 resolveActivity()。如果结果为非空，则至少有一个应用能够处理该 Intent，并且可以安全调用 startActivity()。如果结果为空，不要使用该 Intent。如有可能，您应停用发出该 Intent 的功能。  

  - 1.构建Intent  
      没有什么特殊之处，该构造方法没有参数，直接new 就行了。  

  - 2.设置能启动的Activity的标签,比如action , category 等,需要说明的是如果想要隐式启动Activity,Activity应该至少添加一个默认的category :  
  < category android:name="android.intent.category.DEFAULT" />   

  - 3.设置要传递的数据等操作。   

  - 4.调用startActivity方法启动活动。
    
    - 官方示例 :  
    ----------------------------------------------------------------------
         // Create the text message with a string
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND); //设置ACTION标签，代表具体要执行的操作
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);//设置附加数据
            sendIntent.setType("text/plain");//设置数据类型

            // 验证该Intent至少会被一个Activity响应
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }

    该示例保证了如果用Intent启动活动时至少能有一个Activity响应 ,避免应用崩溃.       

   

