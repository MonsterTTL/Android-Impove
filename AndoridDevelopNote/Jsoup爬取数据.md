# Android开发笔记：用Jsoup简单爬取网页的数据  
  有时候我们在开发过程中，比如仿写一些app时，可能缺少相关的可用的接口给我们提供数据，这个时候据需要我们去网页去爬取一些数据。这个时候我们就可以使用一些开源库来帮助我们轻松地爬取数据。

## Jsop开源库:   
   jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。    

   - 主要功能：   
      - 1.从一个URL，文件或字符串中解析HTML   
      - 2.使用DOM或CSS选择器来查找、取出数据   
      - 3.可操作HTML元素、属性、文本  

## Android Studio中的依赖:     

    org.jsoup:jsoup:1.13.1    
  加载依赖后即可使用Jsouop库    

## Jsoup的一些常用操作：  
  - 1.获取Html文档  
        一般根据网页url开启链接获取即可，使用静态方法： 

          Document doc = Jsoup.connet(url).connect();  
    Jsoup根据url开启链接并且返回一个Document实例，该示例相当于一个html文档，其
    中包含着网页的所有信息。接下来我们可以用这个Document实例来筛选出我们需要的内
    容

  - 2.在Document实例中筛选Elements元素的方法，这里就不介绍用语法选择器筛选了（因为我也不会css和jQury的语法）  
    - 1.根据Id筛选出一个Element： 

          getElementById(String id)

    - 2.根据Tag标签筛选出Elements：  

          getElementsByTag(String tag)      

    - 3.根据Class类筛选出Elements： 

          getElementsByClass(String className)      

    - 4.根据属性筛选出Elements:  

          getElementsByAttribute(String key)       

   - 3.获取内容:
     - 1.获取属性   

           attr(String key)      
     - 2.获取所有属性  

           attributes()   
     - 3.获取文本内容  

           text()
     - 4.获取数据内容 （例如：script和style标签) 

           data()                                   
        

## 一般的爬取过程：   
 这里我们以虎扑的网页版首页为例。
  ### 1.分析网页数据：  
  打开虎扑首页，在浏览器中按下F12即可打开网页的控制台，可以查看网页的HTML文档。
  我准备爬取虎扑资讯中的每一项，并将其加载入一个RecycleView中。那么就需要分析我需要的数据：  
  - 1.新闻项的缩略图   
  - 2.新闻项的标题，即第一行加粗的文字   
  - 3.新闻项的简介，即后面没加粗的正文部分     
  
  分析了我们需要的数据后，我们在控制台中手动筛选出我们需要的内容。
  选中一个标签后，该标签包含的内容也会相应地在浏览器页面中显示出来：  

  我们就这样层层筛选出我们需要的元素，我们先筛选出每一条整体的数据： 


  可以看到，我们需要的所有数据项被包含在：
    
    <div class="test-img-list-model infinite-container"> 

 这一个上级标签中了，所以我们需要先筛选出这个大的上级标签。 

然后，我们再获取每一个具体项中的数据：  



可以看出，新闻项的缩略图被包含在：

    <div class="list-img">
     <a href="https://bbs.hupu.com/56423571.html" target="_blank">
      <img src=https://i1.hoopchina.com.cn/bbs-editor-web/16683251359206.
      jpg?x-oss-process=image/resize,m_fill,w_132,h_88>
     </a>
    </div>  
标签中
   
标题被包含在：
    
    <div class="item-title-conent">
    【字幕】东契奇：我会坚持信任队友 良好的休息很关键
    </div>

正文被包含在：  

     <div class="list-item-desc list-item-desc-has"> 
        <!-- -->
        东契奇赛后采访表示，信任队友是关键，即便他们投不进；这次休息得很好，打很
        久也不觉得累。
        <!-- --> 
    </div>   

 ### 2.重点：Jsoup爬取数据项  
 **首先由于需要联网，我们将网络权限给加上（很重要）**  
    清单文件中加上：  

        <uses-permission android:name="android.permission.INTERNET"/>

   
   上边说道我们只爬取三个数据项，所以我们创建一个数据类，把三个数据组装到这个数据类的实例中：      

            public class NewsItem {
            public String img;//新闻缩略图的链接，待会用Glide加载进去  
            public String title;//新闻的标题
            public String content;//新闻的正文
            }


   我打算用RecycleView将所爬取的数据项现实，所以先创建一个每一项的布局，因为是用约束布局做的，大概就长这样：     


   接下来创建适配器类：  

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

  **最后是最重要的，获取数据的具体做法：**   
      由于Android系统不允许我们在主线程中进行网络通信，所以我们开启一个线程，并
      且在线程中进行Jsoup解析：    

    new Thread(new Runnable() {
    @Override
    public void run() {
      try {
      Document doc = Jsoup.connect("https://www.hupu.com/").get();

      //获取所有的新闻项
      Elements elements = doc.body().getElementsByClass("list-item");

      //遍历每一个新闻项，并筛选出需要的信息，组装成一个信息类，加入数组列表中   
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
            //数据请求完毕，通知handle进行刷新UI
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
      } catch (IOException e) {
      e.printStackTrace();}
      }}).start();   

  - 这里先进行Jsoup.connect请求数据并通过get方法获取Document实例，对该示例进行筛选获取需要的数据。  
  - 然后筛选出所有类名为list-item的元素（Elements），分析过滤可知每一个新闻项的类名即使list-item，所以这样筛选就获取了一个相当于是元素集合（Elements），集合中的每一个元素包含的数据就相当于是每一项的元素  
  - 然后遍历每一个元素，筛选出标题，正文，缩略图  ，并将其包装成一个信息类，
  装入一个List之中并传入RecycleView的适配器中。


  Activity中的完整代码：  
      
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

                    //获取所有的新闻项
                    Elements elements = doc.body().getElementsByClass
                    ("list-item");

                    //遍历每一个新闻项，并筛选出需要的信息，组装成一个信息
                    类，加入数组列表中
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
                    //数据请求完毕，通知handle进行刷新UI
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


最后即可获得一排简略的信息：
      


