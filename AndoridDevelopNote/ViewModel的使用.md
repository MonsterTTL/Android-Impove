# Android：JetPack开发笔记：ViewModel的简介和使用（上）    

## 什么是ViewModel?    
   
   ViewModel 类旨在以注重生命周期的方式存储和管理界面相关数据。ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存。  
   简单来说，ViewModel就是一个用来管理界面数据的特殊类，它有着特殊的生命周期，与Activity的生命周期有所不同。     

## 为何要使用ViewModel:      

   Activity保留的UI界面会在切换主题，屏幕旋转时被销毁然后重新创建，若用Activity保存界面数据就会导致数据丢失。但是ViewModel在这个过程中就不会被重新创建，就能防止数据的丢失。  
  
   虽然原来也有使用savedInstanceState来保存数据，但是数据较多时就繁琐且不适用了。   
   
   ViewModel的生命周期：     
   
## ViewModel的基本用法：
  这里我们创建一个计数器的Demo，保证在屏幕旋转时数据不丢失。
- 1.创建一个类去继承ViewModel，该类中包含界面中包含的数据：

        public class MyViewModel extends ViewModel{
        
             public int count = 0;

        }

- 2.编写Activity的逻辑，在Activity中将ViewModel获取并且使用：    

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

    注意这里具体获取ViewModel是使用ViewModelProvider中的方法：   
    myviewmodel = new ViewModelProvider(this).get(MyViewModel.class);
    其中this为你的Activity或者Fragment实例（具体的绑定的需要处理的生命周期变化的宿主组件），MyViewModel.class即为你创建的具体ViewModel类。

- 3. 运行结果：  
   因为ViewModel在整个Activity被完全销毁前都有且只有一个实例，只有在当所有者 Activity 完成时，框架才会调用 ViewModel 对象的 onCleared() 方法，以便它可以清理资源。   这时ViewModel才是真正被销毁了。

   屏幕翻转，数据不丢失：   

## 向ViewModel传递参数：  
上面我们创建ViewModel时并没有加入参数构造，而是空参构造，如果我们需要有参构造，可以对上面的代码稍加修改，这里我们同样借助一个小Demo来帮助理解：退出应用时，计数器上的数字自动保存，下次再打开应用时自动读取该数据。  


- 1.首先修改我们的ViewModel中的构造方法：   

        public class MyViewModel extends ViewModel {
        public MyViewModel(int countReserved){
            count = countReserved;
        }
        public int count = 0;
      }

  此处将无参构造换成了有参构造。  

- 2.创建一个工厂类，实现ViewModelProvider.Factory接口：   

    

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

 这里我们重写了create方法，并且工厂类的构造方法中也有ViewModel类中有参构造的参数，主要是因为我们需要借助工厂类来构造ViewModel的具体实例，工厂类会自动在合适的时机创建ViewModel的实例。  

 - 3.修改Activity中的逻辑：   
    
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
    这里由于数据很简单，我们借助SharePreference来存储数据，在onPause方法中保存下计数器中的数值，保证数据被记录下。   而在初始化时，我们也会优先去读取记录下的计数器的值，并用该值去初始化ViewModel.    

    这里创建ViewModel实例的方法有有所不同，主要是this后面跟了一个工厂类实例，该实例就是我们用于构造有参ViewModel的中间桥梁。

