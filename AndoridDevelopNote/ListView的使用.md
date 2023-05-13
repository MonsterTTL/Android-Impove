# Android�����ʼ�:������(Adapter)�Լ�ListView��RecycleView�ļ�ʹ��  

��ʹ��ListView��RecycleView֮ǰ�����ǵ����˽��������ĸ��  

## ��������
������˵��������Adapter��һ���ӿڡ�  
�ٷ��ĵ��������������ģ� 
> An Adapter object acts as a bridge between an AdapterView and the underlying data for that view. The Adapter provides access to the data items. The Adapter is also responsible for making a android.view.View for each item in the data set.  

����˵���������䵱����������ͼ֮�佻�����������������Ժ��ʵķ�ʽ��ʾ������������MVCģʽ(����ģʽ������������ͼ)��һ�־��������  

MVCģʽ��  



### �������л��������Ҫ�ķ�����  
1. getView(int position,View convertView,ViewGroup parent) :  
    ��Ӧ����������������Ҫ�ķ����ˣ������������ÿ�������������Ļ�ڵ�ʱ�򱻵��ã����ڼ�����ͼ��    

2. getItem(int positon):   
    ���ڻ�ȡ�����ݼ��е�positionλ�õ�ʵ����  
    �÷���Ҳ�����������ü������������򵥵ػ�ȡ���ݡ�

3. getItemId(int position)  
    ���ڻ�ȡ�����ݼ��е�positionλ�õ�ʵ����Ӧ��ID�� 
    ��ͬgetItem���ǣ�ĳЩ��������onclicklistener��onclick��������id��������������id��������ȡ����getItemId()�������ֵ�ġ� 

4. getCount()  
    ����һ���м������ݡ�


������Ҫ���ܵ���ArrayAdapter���ʹ�ã���Ҫͨ���Ը�����м̳���д��ʵ�������Լ�����������


## ListView�ļ��÷�:
- 1.���һ����������ʾ�б��е�ÿһ������    
    �����Ҽ򵥵���һ��ͼƬ+�ı�����ʽ��ʾһ�������layout���½�һ�����֣�  

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

- 2.����һ���м��������������м��ز��֣����ڽ���һ��ͼƬ��һ���ı��������������Ƚϼ򵥣�    

        public class ItemOne {

            private int imageId; //���ڴ洢ͼƬ��idֵ
            private String text; //���ڴ洢�ı���Ҫ��ʾ������

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

- 3.**�ص�**�������Լ�����������:  

        public class MyAdapter extends ArrayAdapter<ItemOne> {

        private int layoutID;//��������һ���м���������ڽ����췽���е�ResoruceId(������һ�д����������Id)���ݸ�getView����

        public MyAdapter(Context context, int ResoruceId, List<ItemOne> data)
        {
            super(context,ResoruceId,data); //���ø����һ�ֹ��췽��
            layoutID = ResoruceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ItemOne itemOne = getItem(position); //��ȡ������м����ʵ����
            //�����position��ʵ����itemOne�ھ����List����Array(���췽���д����List)�е�����
            View view = LayoutInflater.from(getContext()).inflate(layoutID, parent,false);
            //�˴��Ǵ��������������þ�̬��LayoutInflater.from()�Ӹ������������л�ȡLayoutInflaterʵ�����ٵ�����inflate���������������ּ��ؽ�����
            //inflate�����ĵ�һ������������ֵ�Id���ڶ����Ǹ����֡����������Ƿ�����View��Ӹ����֣��������Ǵ���false�����ˣ���Ϊһ��View�и����֣���Ͳ��������ListView֮���ˡ�
            ImageView imageView = (ImageView) view.findViewById(R.id.Image1);
            TextView textView = (TextView) view.findViewById(R.id.Text1);
            //��֮ǰ�����������Ĳ����л�ȡ�����΢��
            imageView.setImageResource(itemOne.getImageId());
            textView.setText(itemOne.getText());
            //Ϊ΢������ͼƬԴ��Ҫ��ʾ���ı����˴�Ҫ��ʾ��������֮ǰ���м������
            return view;
        }
    }     

- 4.�ڻ�о�����غ�ʹ��ListView: 
    - 1.��������Ҫ���������д���һ��ListView      

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

    - 2.������е��ò�����ListView  ��   

            public class MainActivity extends AppCompatActivity {

            private ArrayList<ItemOne> myList = new ArrayList<>();
            //һ�������б����ڴ洢���������
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                initMyItem();
                ListView listView = (ListView) findViewById(R.id.listview1);
                //��ȡListViewʵ��
                MyAdapter adapter = new MyAdapter(this,R.layout.item_layout,myList);
                //�����Զ����������
                listView.setAdapter(adapter);
                //��listView����������
            }

            private void initMyItem()//��ʼ�������б�
            {
                for(int i = 0; i < 20;i++)
                {
                    ItemOne itemOne = new ItemOne("Item "+ i,R.drawable.qq1);
                    myList.add(itemOne);
                }
            }
          }         

�������ܾ���ʵ��һ���򵥵�ListView:     

## ����ListView���ܵ��Ż���
���������������ʵListView������Ч���Ǻܵ͵ģ���ΪgetView()����ÿ�ζ��Ὣ���ּ���һ�飬һ������������ͻ�������١�   ���ǿ��Թ۲�getView�����Ĳ�������һ��convertView�������ò������ڽ�֮ǰ���غõĲ��ֽ��л��档�������ǿ����ж�convertView�����Ƿ�Ϊ���������Ƿ���ز������������ܣ�     

        �����������е�getView����
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

��Ȼ�����ܹ�����֮ǰ��view�������ǻ����Խ��������view���������ܽ����Ż���
    
    public View getView(int position, View convertView, ViewGroup parent)
    {
            ItemOne itemOne = getItem(position);
            View view;
            ViewHolder viewHolder = new ViewHolder();
            if(convertView != null)
            {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();//���»�ȡviewHolderʵ��
            }else{
                view = LayoutInflater.from(getContext()).inflate(layoutID,parent,false);
                viewHolder.imageView= (ImageView) view.findViewById(R.id.Image1);
                viewHolder.textView = (TextView) view.findViewById(R.id.Text1);
                view.setTag(viewHolder); //��viewHolder�洢��view��
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

�����������Խ���һ���ڲ���ViewHolder���ڴ洢�����΢����������洢��view�У����view�Ѿ������������˵�����е�΢���Ѿ��󶨹�������ֱ���ó����þ����ˡ�  


## ListView���������¼���
ListView���������¼���Button�ļ����¼�ʮ�����ƣ���ͬ�ĵ�����ھ���ʵ�ֵĽӿڣ����õķ�����ͬ��   

        �޸�onCreate����
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
                    //��ȡ����ʵ��
                    Log.d("MainActivity",itemOne.getText());
                }
            });
        }

����򵥶�ListView������һ���������ļ�������ÿ�ε���ͻ�ȡ��Ӧʵ�����ҽ���textͨ����־��ӡ����.


## RecyclerView�ļ��÷��� 
RecyclerView���÷���ListViewʮ�����ƣ�
- 1.���������������


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

    ���������´���һ����������̳�RecyclerView.Adapter���������������Ǵ�����һ����̬�ڲ���ViewHolder�̳�RecyclerView.ViewHold���������Ҫ���ں���ķ�����ʹ�õġ�
        
    ���ڼ̳���RecyclerView.Adapter��������Ҫ��д���������ֱ����ڴ���ViewHolder,��ViewHolder�ͷ�������һ���ж�����.

- 2.�������������������

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
                //�������Բ��ֹ�����
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

    ��ListView��ͬ�Ŀ��ܾ���RecyclerView����Ҫ�������ò��ַ�ʽ���������Ǵ���������򵥵����Բ��֣�Ч����ListViewһ����RecyclerView���������Ĳ���ģʽ�����ǿ�������ȥ���顣    