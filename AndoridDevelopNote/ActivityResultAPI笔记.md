# Android开发笔记：使用Activity Result API 清爽地请求权限 和 启动Activity

## 简介：

    ActivityResultApi是Android新版本中引入的全新API。在过去，如果我们需要启动另一个Activity 或者 申请权限时可能要用到startActivityForResult 和 requestPremissons 方法。但在这套API中我们可以用一套非常清爽的方式实现以上两种操作。

## 官网介绍：   

    虽然所有 API 级别的 Activity 类均提供底层 startActivityForResult() 和 onActivityResult() API，但我们强烈建议您使用 AndroidX Activity 和 Fragment 中引入的 Activity Result API。

    Activity Result API 提供了用于注册结果、启动结果以及在系统分派结果后对其进行处理的组件。   
 由于该API强大的灵活性，因此我们需要学习使用该API处理权限申请，结果回调等操作。

## 三种主要的类：  

 如果要使用这个API我们将要使用到的一个核心方法就是registerForActivityResult方法，该方法用于为Activity注册并返回一个启动器，通过方法返回的启动器我们即可以启动Activity跳转或者权限申请。   
 
 而registerForResult方法就需要接收两个该API中的类对象：    
 **ActivityResultContract** 和 **ActivityResultCallback**   

  ### 1.ActivityResultContract   
  Contract 意味合同，契约。在该API中，ActivityResultContract用于规定启动方法需要接受什么类型的参数以及回调范围什么类型的结果。一般来说，我们并不需要亲自实现这个类，因为Android官方已经内置了许多类型的Contract，一般可以满足我们开发中的绝大多数情况，他们分别是：

* StartActivityForResult: 通用的Contract,不做任何转换，Intent作为输入，ActivityResult作为输出，这也是最常用的一个协定。
* RequestMultiplePermissions： 用于请求一组权限
* RequestPermission: 用于请求单个权限
* TakePicturePreview: 调用MediaStore.ACTION_IMAGE_CAPTURE拍照，返回值为Bitmap图片
* TakePicture: 调用MediaStore.ACTION_IMAGE_CAPTURE拍照，并将图片保存到给定的Uri地址，返回true表示保存成功。
* TakeVideo: 调用MediaStore.ACTION_VIDEO_CAPTURE 拍摄视频，保存到给定的Uri地址，返回一张缩略图。
* PickContact: 从通讯录APP获取联系人
* GetContent: 提示用选择一条内容，返回一个通过ContentResolver#openInputStream(Uri)访问原生数据的Uri地址（content://形式） 。默认情况下，它增加了 Intent#CATEGORY_OPENABLE, 返回可以表示流的内容。
* CreateDocument: 提示用户选择一个文档，返回一个(file:/http:/content:)开头的Uri。
* OpenMultipleDocuments: 提示用户选择文档（可以选择多个），分别返回它们的Uri，以List的形式。
* OpenDocumentTree: 提示用户选择一个目录，并返回用户选择的作为一个Uri返回，应用程序可以完全管理返回目录中的文档。

   ### 2.ActivityResultCallback  
   很显然，该参数用于处理回调的，ActivityResultCallback 是单一方法接口，带有 onActivityResult() 方法，可接受 ActivityResultContract 中定义的**输出类型**的对象。你可以在该回调中处理得到的返回结果。


   ### 3.ActivityResultLauncher  
   这是启动器类，即registerForActivityResult方法返回的类，通过该类来进行跳转或者权限请求。

   正如官方所言：
   >虽然 registerForActivityResult() 会注册您的回调，但它**不会**启动另一个 activity 并发出结果请求。这些操作由返回的 ActivityResultLauncher 实例负责。

如果有多个使用不同协定或需要单独回调的 activity 结果调用，则可以多次调用 registerForActivityResult()，以注册多个 ActivityResultLauncher 实例。每次创建 fragment 或 activity 时，都必须按照相同的顺序调用 registerForActivityResult()，才能确保将生成的结果传递给正确的回调。

## 清爽地申请权限：
  
  如果你想要在Fragment中申请权限你可能会想要使用onRequestPermissionsResult的回调，但是你会发现其被标记为了方法已过时，并且官方建议你使用ActivityResultApi进行替代：

  别担心，你很快就会体验到ActivityResultAPI的神奇之处.这里我们通过在Fragment中请求录音权限来说明。   
  ### 1.首先，我们需要先注册启动器，并且获取到这个启动器：

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.
    RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result == true){
                        //进行语音处理
                    }else{
                            Toast.makeText(requireContext(), "无录音权限！", 
                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });

   **特别需要注意的一点是:您必须在创建 fragment 或 activity 之前调用 registerForActivityResult()；在 fragment 或 activity 的 Lifecycle 达到 CREATED 之前，您将无法启动 ActivityResultLauncher。**     也就是说，在这个例子中，我们需要在Fragment的生命周期到达onViewCreated前注册好启动器，否则就会报错，这里我在onCreateView中注册：

     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
      ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_navfragment,container,
        false);

         ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.
        RequestPermission()
                , new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if(result == true){
                            new AudioHelper(home_navFragment.this).useSpeech
                            (requireContext());
                        }else{
                            Toast.makeText(requireContext(), "无录音权限！", 
                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
      }        

  ### 2.通过启动器启动权限申请  
 之前我们说过，传入的ActivityResultContract规定了输入参数和返回参数的类型，在我们传入的官方内置的ActivityResultContracts.RequestPermission() 就规定了传入的参数是一个String类型的参数，与请求权限中的参数类型一致（Manifest.permisson./** 具体权限名 **/就是一个String类型的参数），返回的是一个布尔类型的值，说明权限是被否成功申请，这也是我们之前的代码中 启动器的泛型类型是String类型，而 回调函数的泛型类型是布尔类型的原因。
 然后我们只需要在需要申请权限的地方调用launch方法即可：
    
    audioInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(requireActivity().checkSelfPermission(Manifest.
                permission.RECORD_AUDIO) !=
                        PackageManager.PERMISSION_GRANTED){
                    launcher.launch(Manifest.permission.RECORD_AUDIO);
                    }

            }
        });

  这里launcher启动器接收了一个String类型的参数，用于说明要请求的是什么权限。      


  ## 启动Activity并返回结果  
  启动另一个 activity（无论是您应用中的 activity 还是其他应用中的 activity）不一定是单向操作。通过该API，我们同样可以代替原来的startActivityForResult方法，基本用法和上面的请求权限差不多，不同的点在于传入ActivityResultContracts类别的不同:

    public class FirstActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> requestDataLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    String data = result.getData().getStringExtra("data");
                    // Handle data from SecondActivity
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button firstButton = findViewById(R.id.first_button);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                requestDataLauncher.launch(intent);
            }
        });
        }

    }
  由于是启动Activity，因此我们传入的ActivityResultContract是StartActivityForResult()。
