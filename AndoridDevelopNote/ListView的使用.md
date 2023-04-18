# Android开发笔记:适配器(Adapter)以及ListView和RecycleView的简单使用  

在使用ListView和RecycleView之前，我们得先了解适配器的概念。  

## 适配器：
具体来说，适配器Adapter是一个接口。  
官方文档中是这样描述的： 
> An Adapter object acts as a bridge between an AdapterView and the underlying data for that view. The Adapter provides access to the data items. The Adapter is also responsible for making a android.view.View for each item in the data set.  

简单来说，适配器充当着数据与视图之间交互的桥梁，将数据以合适的方式显示出来。其属于MVC模式(数据模式，控制器，视图)的一种具体情况。  

MVC模式：  



### 适配器中还有许多重要的方法：  
1. getView(int position,View convertView,ViewGroup parent) :  
    这应该是适配器中最重要的方法了，这个方法会在每个子项被滚动到屏幕内的时候被调用，用于加载视图。    

2. getItem(int positon):   
    用于获取在数据集中第position位置的实例。  
    该方法也会在我们设置监听器后用来简单地获取数据。

3. getItemId(int position)  
    用于获取在数据集中第position位置的实例对应的ID。 
    不同getItem的是，某些方法（如onclicklistener的onclick方法）有id这个参数，而这个id参数就是取决于getItemId()这个返回值的。 

4. getCount()  
    返回一共有几项数据。


我们主要介绍的是ArrayAdapter类的使用，主要通过对该类进行继承重写来实现我们自己的适配器。


## ListView的简单用法:
- 1.设计一个布局以显示列表中的每一个子项    
    这里我简单地用一个图片+文本的形式显示一个子项，在layout下新建一个布局：  

        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout  
          xmlns:android="http://schemas.android.com/apk/res/android"
            android:orientation="horizontal"
            android:layout_width="match_parent"
            android:layout_height="50dp">

            <ImageView
                android:id="@+id/Image1"
                android:layout_width="50dp"
                android:layout_height="50dp"
                />
            <TextView
                android:id="@+id/Text1"
                android:layout_width="0dp"
                android:layout_height="50dp"
                android:layout_weight="1"
                />
         </LinearLayout>     

- 2.创建一个中间类以在适配器中加载布局，由于仅有一个图片和一个文本框，所以这里该类比较简单：    

        public class ItemOne {

            private int imageId; //用于存储图片的id值
            private String text; //用于存储文本框要显示的内容

            public ItemOne(String text,int imageId)
            {
                this.text = text;
                this.imageId = imageId;
            }

            public String getText()
            {
                return  text;
            }

            public int getImageId()
            {
                return imageId;
            }
        }         

- 3.**重点**：创建自己的适配器类:  

        public class MyAdapter extends ArrayAdapter<ItemOne> {

        private int layoutID;//仅仅用作一个中间变量，用于将构造方法中的ResoruceId(即步骤一中创建的子项布局Id)传递给getView方法

        public MyAdapter(Context context, int ResoruceId, List<ItemOne> data)
        {
            super(context,ResoruceId,data); //调用父类的一种构造方法
            layoutID = ResoruceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ItemOne itemOne = getItem(position); //获取具体的中间类的实例，
            //这里的position其实就是itemOne在具体的List或者Array(构造方法中传入的List)中的索引
            View view = LayoutInflater.from(getContext()).inflate(layoutID, parent,false);
            //此处是创建具体的子项，调用静态的LayoutInflater.from()从给定的上下文中获取LayoutInflater实例，再调用其inflate方法将具体的子项布局加载进来。
            //inflate方法的第一个参数是子项布局的Id，第二个是父布局。第三个是是否给这个View添加父布局，这里我们传入false就行了，因为一旦View有父布局，其就不能添加入ListView之中了。
            ImageView imageView = (ImageView) view.findViewById(R.id.Image1);
            TextView textView = (TextView) view.findViewById(R.id.Text1);
            //从之前加载入的子项的布局中获取具体的微件
            imageView.setImageResource(itemOne.getImageId());
            textView.setText(itemOne.getText());
            //为微件设置图片源和要显示的文本，此处要显示的内容与之前的中间类相关
            return view;
        }
    }     

- 4.在活动中具体加载和使用ListView: 
    - 1.首先我们要在主布局中创建一个ListView      

            <?xml version="1.0" encoding="utf-8"?>
            <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">
                <ListView
                    android:id="@+id/listview1"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                />


            </LinearLayout>   

    - 2.在主活动中调用并加载ListView  ：   

            public class MainActivity extends AppCompatActivity {

            private ArrayList<ItemOne> myList = new ArrayList<>();
            //一个数据列表，用于存储具体的数据
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                initMyItem();
                ListView listView = (ListView) findViewById(R.id.listview1);
                //获取ListView实例
                MyAdapter adapter = new MyAdapter(this,R.layout.item_layout,myList);
                //创建自定义的适配器
                listView.setAdapter(adapter);
                //给listView设置适配器
            }

            private void initMyItem()//初始化数据列表
            {
                for(int i = 0; i < 20;i++)
                {
                    ItemOne itemOne = new ItemOne("Item "+ i,R.drawable.qq1);
                    myList.add(itemOne);
                }
            }
          }         

这样就能具体实现一个简单的ListView:     

## 关于ListView性能的优化：
在上面的例子中其实ListView的运行效率是很低的，因为getView()方法每次都会将布局加载一遍，一旦数据量过大就会产生卡顿。   我们可以观察getView方法的参数中有一个convertView参数，该参数用于将之前加载好的布局进行缓存。所以我们可以判断convertView参数是否为空来决定是否加载布局以提升性能：     

        更改适配器中的getView方法
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ItemOne itemOne = getItem(position);
            View view;
            if(convertView != null)
            {
                view = convertView;
            }else{
                view = LayoutInflater.from(getContext()).inflate(layoutID, parent,false);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.Image1);
            TextView textView = (TextView) view.findViewById(R.id.Text1);
            imageView.setImageResource(itemOne.getImageId());
            textView.setText(itemOne.getText());
            return view;
        }                   

既然我们能够缓存之前的view，那我们还可以借助缓存的view继续对性能进行优化。
    
    public View getView(int position, View convertView, ViewGroup parent)
    {
            ItemOne itemOne = getItem(position);
            View view;
            ViewHolder viewHolder = new ViewHolder();
            if(convertView != null)
            {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();//重新获取viewHolder实例
            }else{
                view = LayoutInflater.from(getContext()).inflate(layoutID,parent,false);
                viewHolder.imageView= (ImageView) view.findViewById(R.id.Image1);
                viewHolder.textView = (TextView) view.findViewById(R.id.Text1);
                view.setTag(viewHolder); //将viewHolder存储在view中
            }
            viewHolder.imageView.setImageResource(itemOne.getImageId());
            viewHolder.textView.setText(itemOne.getText());
            return view;
        }

        class ViewHolder{
            ImageView imageView;
            TextView textView;
        }
    }

在这里我们自建了一个内部类ViewHolder用于存储具体的微件，并将其存储在view中，如果view已经被缓存过，则说明其中的微件已经绑定过，所以直接拿出来用就行了。  


## ListView的鼠标监听事件：
ListView的鼠标监听事件与Button的监听事件十分相似，不同的点就在于具体实现的接口，调用的方法不同：   

        修改onCreate方法
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initMyItem();
            ListView listView = (ListView) findViewById(R.id.listview1);
            MyAdapter adapter = new MyAdapter(this,R.layout.item_layout,myList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.
            OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,   
                int position, long id) {
                    ItemOne itemOne = myList.get(position);
                    //获取具体实例
                    Log.d("MainActivity",itemOne.getText());
                }
            });
        }

这里简单对ListView设置了一个适配器的监听器，每次点击就获取相应实例并且将其text通过日志打印出来.


## RecyclerView的简单用法： 
RecyclerView的用法和ListView十分相似：
- 1.创建具体的适配器


            public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
            private List<ItemOne> mList;

            static class ViewHolder extends RecyclerView.ViewHolder{
                ImageView imageView;
                TextView textView;

                public ViewHolder(View view)
                {
                    super(view);
                    imageView = (ImageView) view.findViewById(R.id.Image1);
                    textView = (TextView) view.findViewById(R.id.Text1);

                }
            }

            public RecycleAdapter(List<ItemOne> list)
            {
                mList = list;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_layout,parent,false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder,int position)
            {
                ItemOne itemOne = mList.get(position);
                holder.imageView.setImageResource(itemOne.getImageId());
                holder.textView.setText(itemOne.getText());
            }

            @Override
            public int getItemCount(){
                return mList.size();
            }
         }

    我们首先新创建一个适配器类继承RecyclerView.Adapter。在适配器中我们创建了一个静态内部类ViewHolder继承RecyclerView.ViewHold，这个类主要是在后面的方法中使用的。
        
    由于继承了RecyclerView.Adapter，该类需要重写三个方法分别用于创建ViewHolder,绑定ViewHolder和返回数据一共有多少项.

- 2.在主活动中配置适配器：

            public class MainActivity extends AppCompatActivity {

            private ArrayList<ItemOne> myList = new ArrayList<>();

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                initMyItem();
                RecyclerView recyclerView = (RecyclerView) 
                findViewById(R.id.Re1);
                LinearLayoutManager layoutManager = new 
                LinearLayoutManager(this);
                //设置线性布局管理器
                recyclerView.setLayoutManager(layoutManager);
                RecycleAdapter adapter = new RecycleAdapter(myList);
                recyclerView.setAdapter(adapter);

            }

            private void initMyItem()
            {
                for(int i = 0; i < 20;i++)
                {
                    ItemOne itemOne = new ItemOne("Item "+ i,R.drawable.qq1);
                    myList.add(itemOne);
                }
            }
        }

    与ListView不同的可能就是RecyclerView还需要额外设置布局方式，这里我们创建的是最简单的线性布局，效果与ListView一样。RecyclerView还有其他的布局模式，我们可以自行去体验。    