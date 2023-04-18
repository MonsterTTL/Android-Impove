# Android��JetPack�����ʼǣ�ViewModel�ļ���ʹ�ã��ϣ�    

## ʲô��ViewModel?    
   
   ViewModel ��ּ����ע���������ڵķ�ʽ�洢�͹������������ݡ�ViewModel �������ݿ��ڷ�����Ļ��ת�����ø��ĺ�������档  
   ����˵��ViewModel����һ����������������ݵ������࣬������������������ڣ���Activity����������������ͬ��     

## Ϊ��Ҫʹ��ViewModel:      

   Activity������UI��������л����⣬��Ļ��תʱ������Ȼ�����´���������Activity����������ݾͻᵼ�����ݶ�ʧ������ViewModel����������оͲ��ᱻ���´��������ܷ�ֹ���ݵĶ�ʧ��  
  
   ��Ȼԭ��Ҳ��ʹ��savedInstanceState���������ݣ��������ݽ϶�ʱ�ͷ����Ҳ������ˡ�   
   
   ViewModel���������ڣ�     
   
## ViewModel�Ļ����÷���
  �������Ǵ���һ����������Demo����֤����Ļ��תʱ���ݲ���ʧ��
- 1.����һ����ȥ�̳�ViewModel�������а��������а��������ݣ�

        public class MyViewModel extends ViewModel{
        
             public int count = 0;

        }

- 2.��дActivity���߼�����Activity�н�ViewModel��ȡ����ʹ�ã�    

        public class MainActivity extends AppCompatActivity implements View.
        OnClickListener {

        TextView CounterText;
        Button addOne;
        Button Clear;
        MyViewModel myViewModel;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            CounterText = findViewById(R.id.textView);
            myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
            addOne = findViewById(R.id.addOne);
            Clear = findViewById(R.id.Clear);
            addOne.setOnClickListener(this);
            Clear.setOnClickListener(this);
            CounterText.setText(String.valueOf(myViewModel.count));

        }

        public void onClick(View v){
            switch (v.getId()){
                case R.id.Clear:
                    myViewModel.count = 0;
                    CounterText.setText(String.valueOf(myViewModel.count));
                    break;
                case R.id.addOne:
                    myViewModel.count++;
                    CounterText.setText(String.valueOf(myViewModel.count));
                    break;
                default:
                    break;
            }
        }
      }

    ע����������ȡViewModel��ʹ��ViewModelProvider�еķ�����   
    myviewmodel = new ViewModelProvider(this).get(MyViewModel.class);
    ����thisΪ���Activity����Fragmentʵ��������İ󶨵���Ҫ������������ڱ仯�������������MyViewModel.class��Ϊ�㴴���ľ���ViewModel�ࡣ

- 3. ���н����  
   ��ΪViewModel������Activity����ȫ����ǰ������ֻ��һ��ʵ����ֻ���ڵ������� Activity ���ʱ����ܲŻ���� ViewModel ����� onCleared() �������Ա�������������Դ��   ��ʱViewModel���������������ˡ�

   ��Ļ��ת�����ݲ���ʧ��   

## ��ViewModel���ݲ�����  
�������Ǵ���ViewModelʱ��û�м���������죬���ǿղι��죬���������Ҫ�вι��죬���Զ�����Ĵ����Լ��޸ģ���������ͬ������һ��СDemo��������⣺�˳�Ӧ��ʱ���������ϵ������Զ����棬�´��ٴ�Ӧ��ʱ�Զ���ȡ�����ݡ�  


- 1.�����޸����ǵ�ViewModel�еĹ��췽����   

        public class MyViewModel extends ViewModel {
        public MyViewModel(int countReserved){
            count = countReserved;
        }
        public int count = 0;
      }

  �˴����޲ι��컻�����вι��졣  

- 2.����һ�������࣬ʵ��ViewModelProvider.Factory�ӿڣ�   

    

        public class MyViewModelFactory implements ViewModelProvider.Factory {

        private int counter = 0;
        public MyViewModelFactory(int counter){
            this.counter = counter;
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass,
         CreationExtras extras) {
            return (T) new MyViewModel(counter);
        }
        }

 ����������д��create���������ҹ�����Ĺ��췽����Ҳ��ViewModel�����вι���Ĳ�������Ҫ����Ϊ������Ҫ����������������ViewModel�ľ���ʵ������������Զ��ں��ʵ�ʱ������ViewModel��ʵ����  

 - 3.�޸�Activity�е��߼���   
    
        public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        TextView CounterText;
        Button addOne;
        Button Clear;
        MyViewModel myViewModel;
        SharedPreferences preferences;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            int savedCounter = preferences.getInt("saved_data",0);
            CounterText = findViewById(R.id.textView);
            myViewModel = new ViewModelProvider(this,new MyViewModelFactory
            (savedCounter)).
                    get(MyViewModel.class);
            addOne = findViewById(R.id.addOne);
            Clear = findViewById(R.id.Clear);
            addOne.setOnClickListener(this);
            Clear.setOnClickListener(this);
            CounterText.setText(String.valueOf(myViewModel.count));

        }

        protected void onPause(){
            super.onPause();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("saved_data", myViewModel.count);
            editor.apply();
        }

        public void onClick(View v){
            switch (v.getId()){
                case R.id.Clear:
                    myViewModel.count = 0;
                    CounterText.setText(String.valueOf(myViewModel.count));
                    break;
                case R.id.addOne:
                    myViewModel.count++;
                    CounterText.setText(String.valueOf(myViewModel.count));
                    break;
                default:
                    break;
            }
        }
       }
    �����������ݺܼ򵥣����ǽ���SharePreference���洢���ݣ���onPause�����б����¼������е���ֵ����֤���ݱ���¼�¡�   ���ڳ�ʼ��ʱ������Ҳ������ȥ��ȡ��¼�µļ�������ֵ�����ø�ֵȥ��ʼ��ViewModel.    

    ���ﴴ��ViewModelʵ���ķ�����������ͬ����Ҫ��this�������һ��������ʵ������ʵ�������������ڹ����в�ViewModel���м�������

