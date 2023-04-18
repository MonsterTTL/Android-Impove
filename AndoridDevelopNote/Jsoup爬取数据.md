# Android�����ʼǣ���Jsoup����ȡ��ҳ������  
  ��ʱ�������ڿ��������У������дһЩappʱ������ȱ����صĿ��õĽӿڸ������ṩ���ݣ����ʱ�����Ҫ����ȥ��ҳȥ��ȡһЩ���ݡ����ʱ�����ǾͿ���ʹ��һЩ��Դ���������������ɵ���ȡ���ݡ�

## Jsop��Դ��:   
   jsoup ��һ��Java ��HTML����������ֱ�ӽ���ĳ��URL��ַ��HTML�ı����ݡ����ṩ��һ�׷ǳ�ʡ����API����ͨ��DOM��CSS�Լ�������jQuery�Ĳ���������ȡ���Ͳ������ݡ�    

   - ��Ҫ���ܣ�   
      - 1.��һ��URL���ļ����ַ����н���HTML   
      - 2.ʹ��DOM��CSSѡ���������ҡ�ȡ������   
      - 3.�ɲ���HTMLԪ�ء����ԡ��ı�  

## Android Studio�е�����:     

    org.jsoup:jsoup:1.13.1    
  ���������󼴿�ʹ��Jsouop��    

## Jsoup��һЩ���ò�����  
  - 1.��ȡHtml�ĵ�  
        һ�������ҳurl�������ӻ�ȡ���ɣ�ʹ�þ�̬������ 

          Document doc = Jsoup.connet(url).connect();  
    Jsoup����url�������Ӳ��ҷ���һ��Documentʵ������ʾ���൱��һ��html�ĵ�����
    �а�������ҳ��������Ϣ�����������ǿ��������Documentʵ����ɸѡ��������Ҫ����
    ��

  - 2.��Documentʵ����ɸѡElementsԪ�صķ���������Ͳ��������﷨ѡ����ɸѡ�ˣ���Ϊ��Ҳ����css��jQury���﷨��  
    - 1.����Idɸѡ��һ��Element�� 

          getElementById(String id)

    - 2.����Tag��ǩɸѡ��Elements��  

          getElementsByTag(String tag)      

    - 3.����Class��ɸѡ��Elements�� 

          getElementsByClass(String className)      

    - 4.��������ɸѡ��Elements:  

          getElementsByAttribute(String key)       

   - 3.��ȡ����:
     - 1.��ȡ����   

           attr(String key)      
     - 2.��ȡ��������  

           attributes()   
     - 3.��ȡ�ı�����  

           text()
     - 4.��ȡ�������� �����磺script��style��ǩ) 

           data()                                   
        

## һ�����ȡ���̣�   
 ���������Ի��˵���ҳ����ҳΪ����
  ### 1.������ҳ���ݣ�  
  �򿪻�����ҳ����������а���F12���ɴ���ҳ�Ŀ���̨�����Բ鿴��ҳ��HTML�ĵ���
  ��׼����ȡ������Ѷ�е�ÿһ������������һ��RecycleView�С���ô����Ҫ��������Ҫ�����ݣ�  
  - 1.�����������ͼ   
  - 2.������ı��⣬����һ�мӴֵ�����   
  - 3.������ļ�飬������û�Ӵֵ����Ĳ���     
  
  ������������Ҫ�����ݺ������ڿ���̨���ֶ�ɸѡ��������Ҫ�����ݡ�
  ѡ��һ����ǩ�󣬸ñ�ǩ����������Ҳ����Ӧ���������ҳ������ʾ������  

  ���Ǿ��������ɸѡ��������Ҫ��Ԫ�أ�������ɸѡ��ÿһ����������ݣ� 


  ���Կ�����������Ҫ����������������ڣ�
    
    <div class="test-img-list-model infinite-container"> 

 ��һ���ϼ���ǩ���ˣ�����������Ҫ��ɸѡ���������ϼ���ǩ�� 

Ȼ�������ٻ�ȡÿһ���������е����ݣ�  



���Կ����������������ͼ�������ڣ�

    <div class="list-img">
     <a href="https://bbs.hupu.com/56423571.html" target="_blank">
      <img src=https://i1.hoopchina.com.cn/bbs-editor-web/16683251359206.
      jpg?x-oss-process=image/resize,m_fill,w_132,h_88>
     </a>
    </div>  
��ǩ��
   
���ⱻ�����ڣ�
    
    <div class="item-title-conent">
    ����Ļ�������棺�һ������ζ��� ���õ���Ϣ�ܹؼ�
    </div>

���ı������ڣ�  

     <div class="list-item-desc list-item-desc-has"> 
        <!-- -->
        ����������ɷñ�ʾ�����ζ����ǹؼ�����������Ͷ�����������Ϣ�úܺã����
        ��Ҳ�������ۡ�
        <!-- --> 
    </div>   

 ### 2.�ص㣺Jsoup��ȡ������  
 **����������Ҫ���������ǽ�����Ȩ�޸����ϣ�����Ҫ��**  
    �嵥�ļ��м��ϣ�  

        <uses-permission android:name="android.permission.INTERNET"/>

   
   �ϱ�˵������ֻ��ȡ����������������Ǵ���һ�������࣬������������װ������������ʵ���У�      

            public class NewsItem {
            public String img;//��������ͼ�����ӣ�������Glide���ؽ�ȥ  
            public String title;//���ŵı���
            public String content;//���ŵ�����
            }


   �Ҵ�����RecycleView������ȡ����������ʵ�������ȴ���һ��ÿһ��Ĳ��֣���Ϊ����Լ���������ģ���žͳ�������     


   �����������������ࣺ  

      public class MyAdapter extends RecyclerView.Adapter<MyAdapter.
      ViewHolder> {

      private ArrayList<NewsItem> mList;

      public MyAdapter(ArrayList<NewsItem> mList){
          this.mList = mList;
       }

      @NonNull
      @Override
      public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_item,parent,false);
        return new ViewHolder(view);
      }

      @Override
      public void onBindViewHolder(@NonNull ViewHolder holder, int 
      position) {
        NewsItem item = mList.get(position);
        holder.content.setText(item.content);
        holder.title.setText(item.title);
        Glide.with(holder.imageView).load(item.img).into(holder.imageView);
      }

      @Override
      public int getItemCount() {
         return mList.size();
      }

      public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView content;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.imageView);
            title = view.findViewById(R.id.news_title);
            content = view.findViewById(R.id.news_content);
        }
      }
    }            

  **���������Ҫ�ģ���ȡ���ݵľ���������**   
      ����Androidϵͳ���������������߳��н�������ͨ�ţ��������ǿ���һ���̣߳���
      �����߳��н���Jsoup������    

    new Thread(new Runnable() {
    @Override
    public void run() {
      try {
      Document doc = Jsoup.connect("https://www.hupu.com/").get();

      //��ȡ���е�������
      Elements elements = doc.body().getElementsByClass("list-item");

      //����ÿһ���������ɸѡ����Ҫ����Ϣ����װ��һ����Ϣ�࣬���������б���   
      for (Element e:elements){
            NewsItem i = new NewsItem();
            String title = e.getElementsByClass("item-title-conent").
            first().text();
            String content = e.getElementsByClass("list-item-desc
             list-item-desc-has").text();
            String imgURL = e.getElementsByClass
            ("list-img").first().getElementsByTag("img").attr("src");
            i.content = content;
            i.img = imgURL;
            i.title = title;
            }
            //����������ϣ�֪ͨhandle����ˢ��UI
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
      } catch (IOException e) {
      e.printStackTrace();}
      }}).start();   

  - �����Ƚ���Jsoup.connect�������ݲ�ͨ��get������ȡDocumentʵ�����Ը�ʾ������ɸѡ��ȡ��Ҫ�����ݡ�  
  - Ȼ��ɸѡ����������Ϊlist-item��Ԫ�أ�Elements�����������˿�֪ÿһ���������������ʹlist-item����������ɸѡ�ͻ�ȡ��һ���൱����Ԫ�ؼ��ϣ�Elements���������е�ÿһ��Ԫ�ذ��������ݾ��൱����ÿһ���Ԫ��  
  - Ȼ�����ÿһ��Ԫ�أ�ɸѡ�����⣬���ģ�����ͼ  ���������װ��һ����Ϣ�࣬
  װ��һ��List֮�в�����RecycleView���������С�


  Activity�е��������룺  
      
    public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<NewsItem> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.hupu.com/").get();

                    //��ȡ���е�������
                    Elements elements = doc.body().getElementsByClass
                    ("list-item");

                    //����ÿһ���������ɸѡ����Ҫ����Ϣ����װ��һ����Ϣ
                    �࣬���������б���
                    for (Element e:elements){
                        NewsItem i = new NewsItem();
                        String title = e.getElementsByClass
                        ("item-title-conent").first().text();
                        //Log.d("test",title);
                        String content = e.getElementsByClass
                        ("list-item-desc list-item-desc-has")
                                .text();
                        //Log.d("test",content);
                        String imgURL = e.getElementsByClass
                        ("list-img").first().getElementsByTag("img")
                                .attr("src");
                        //Log.d("test",imgURL);
                        i.content = content;
                        i.img = imgURL;
                        i.title = title;
                        list.add(i);
                    }
                    //����������ϣ�֪ͨhandle����ˢ��UI
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        adapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
     }
    }


��󼴿ɻ��һ�ż��Ե���Ϣ��
      


