# Android�����ʼǣ�ʹ��Activity Result API ��ˬ������Ȩ�� �� ����Activity

## ��飺

    ActivityResultApi��Android�°汾�������ȫ��API���ڹ�ȥ�����������Ҫ������һ��Activity ���� ����Ȩ��ʱ����Ҫ�õ�startActivityForResult �� requestPremissons ��������������API�����ǿ�����һ�׷ǳ���ˬ�ķ�ʽʵ���������ֲ�����

## �������ܣ�   

    ��Ȼ���� API ����� Activity ����ṩ�ײ� startActivityForResult() �� onActivityResult() API��������ǿ�ҽ�����ʹ�� AndroidX Activity �� Fragment ������� Activity Result API��

    Activity Result API �ṩ������ע��������������Լ���ϵͳ���ɽ���������д���������   
 ���ڸ�APIǿ�������ԣ����������Ҫѧϰʹ�ø�API����Ȩ�����룬����ص��Ȳ�����

## ������Ҫ���ࣺ  

 ���Ҫʹ�����API���ǽ�Ҫʹ�õ���һ�����ķ�������registerForActivityResult�������÷�������ΪActivityע�Ტ����һ����������ͨ���������ص����������Ǽ���������Activity��ת����Ȩ�����롣   
 
 ��registerForResult��������Ҫ����������API�е������    
 **ActivityResultContract** �� **ActivityResultCallback**   

  ### 1.ActivityResultContract   
  Contract ��ζ��ͬ����Լ���ڸ�API�У�ActivityResultContract���ڹ涨����������Ҫ����ʲô���͵Ĳ����Լ��ص���Χʲô���͵Ľ����һ����˵�����ǲ�����Ҫ����ʵ������࣬��ΪAndroid�ٷ��Ѿ�������������͵�Contract��һ������������ǿ����еľ��������������Ƿֱ��ǣ�

* StartActivityForResult: ͨ�õ�Contract,�����κ�ת����Intent��Ϊ���룬ActivityResult��Ϊ�������Ҳ����õ�һ��Э����
* RequestMultiplePermissions�� ��������һ��Ȩ��
* RequestPermission: �������󵥸�Ȩ��
* TakePicturePreview: ����MediaStore.ACTION_IMAGE_CAPTURE���գ�����ֵΪBitmapͼƬ
* TakePicture: ����MediaStore.ACTION_IMAGE_CAPTURE���գ�����ͼƬ���浽������Uri��ַ������true��ʾ����ɹ���
* TakeVideo: ����MediaStore.ACTION_VIDEO_CAPTURE ������Ƶ�����浽������Uri��ַ������һ������ͼ��
* PickContact: ��ͨѶ¼APP��ȡ��ϵ��
* GetContent: ��ʾ��ѡ��һ�����ݣ�����һ��ͨ��ContentResolver#openInputStream(Uri)����ԭ�����ݵ�Uri��ַ��content://��ʽ�� ��Ĭ������£��������� Intent#CATEGORY_OPENABLE, ���ؿ��Ա�ʾ�������ݡ�
* CreateDocument: ��ʾ�û�ѡ��һ���ĵ�������һ��(file:/http:/content:)��ͷ��Uri��
* OpenMultipleDocuments: ��ʾ�û�ѡ���ĵ�������ѡ���������ֱ𷵻����ǵ�Uri����List����ʽ��
* OpenDocumentTree: ��ʾ�û�ѡ��һ��Ŀ¼���������û�ѡ�����Ϊһ��Uri���أ�Ӧ�ó��������ȫ������Ŀ¼�е��ĵ���

   ### 2.ActivityResultCallback  
   ����Ȼ���ò������ڴ���ص��ģ�ActivityResultCallback �ǵ�һ�����ӿڣ����� onActivityResult() �������ɽ��� ActivityResultContract �ж����**�������**�Ķ���������ڸûص��д���õ��ķ��ؽ����


   ### 3.ActivityResultLauncher  
   �����������࣬��registerForActivityResult�������ص��࣬ͨ��������������ת����Ȩ������

   ����ٷ����ԣ�
   >��Ȼ registerForActivityResult() ��ע�����Ļص�������**����**������һ�� activity ���������������Щ�����ɷ��ص� ActivityResultLauncher ʵ������

����ж��ʹ�ò�ͬЭ������Ҫ�����ص��� activity ������ã�����Զ�ε��� registerForActivityResult()����ע���� ActivityResultLauncher ʵ����ÿ�δ��� fragment �� activity ʱ�������밴����ͬ��˳����� registerForActivityResult()������ȷ�������ɵĽ�����ݸ���ȷ�Ļص���

## ��ˬ������Ȩ�ޣ�
  
  �������Ҫ��Fragment������Ȩ������ܻ���Ҫʹ��onRequestPermissionsResult�Ļص���������ᷢ���䱻���Ϊ�˷����ѹ�ʱ�����ҹٷ�������ʹ��ActivityResultApi���������

  ���ģ���ܿ�ͻ����鵽ActivityResultAPI������֮��.��������ͨ����Fragment������¼��Ȩ����˵����   
  ### 1.���ȣ�������Ҫ��ע�������������һ�ȡ�������������

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.
    RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result == true){
                        //������������
                    }else{
                            Toast.makeText(requireContext(), "��¼��Ȩ�ޣ�", 
                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });

   **�ر���Ҫע���һ����:�������ڴ��� fragment �� activity ֮ǰ���� registerForActivityResult()���� fragment �� activity �� Lifecycle �ﵽ CREATED ֮ǰ�������޷����� ActivityResultLauncher��**     Ҳ����˵������������У�������Ҫ��Fragment���������ڵ���onViewCreatedǰע���������������ͻᱨ����������onCreateView��ע�᣺

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
                            Toast.makeText(requireContext(), "��¼��Ȩ�ޣ�", 
                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
      }        

  ### 2.ͨ������������Ȩ������  
 ֮ǰ����˵���������ActivityResultContract�涨����������ͷ��ز��������ͣ������Ǵ���Ĺٷ����õ�ActivityResultContracts.RequestPermission() �͹涨�˴���Ĳ�����һ��String���͵Ĳ�����������Ȩ���еĲ�������һ�£�Manifest.permisson./** ����Ȩ���� **/����һ��String���͵Ĳ����������ص���һ���������͵�ֵ��˵��Ȩ���Ǳ���ɹ����룬��Ҳ������֮ǰ�Ĵ����� �������ķ���������String���ͣ��� �ص������ķ��������ǲ������͵�ԭ��
 Ȼ������ֻ��Ҫ����Ҫ����Ȩ�޵ĵط�����launch�������ɣ�
    
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

  ����launcher������������һ��String���͵Ĳ���������˵��Ҫ�������ʲôȨ�ޡ�      


  ## ����Activity�����ؽ��  
  ������һ�� activity����������Ӧ���е� activity ��������Ӧ���е� activity����һ���ǵ��������ͨ����API������ͬ�����Դ���ԭ����startActivityForResult�����������÷������������Ȩ�޲�࣬��ͬ�ĵ����ڴ���ActivityResultContracts���Ĳ�ͬ:

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
  ����������Activity��������Ǵ����ActivityResultContract��StartActivityForResult()��
