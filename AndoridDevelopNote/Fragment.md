# Android开发笔记：探究Fragment(碎片)

## 何为碎片？   

官方文档中提到：
> A fragment represents a **modular portion of the user interface** within an activity. A fragment has its own lifecycle, receives its own input events, and you can add or remove fragments while the containing activity is running.

里面提到的重要的一点便是碎片是模块化的，是UI的一部分。同时碎片定义和管理自己的布局，具有自己的生命周期，并且可以处理自己的输入事件。    

我们可以对其特点进行一些总结：
### 特点：
* 1.表示应用界面中可重复使用的一部分  
* 2.相对独立，有自己的布局，输入事件，生命周期等  
* 3.Fragment 不能独立存在，而是必须由 Activity 或另一个 Fragment 托管。Fragment 的视图层次结构会成为宿主的视图层次结构的一部分，或附加到宿主的视图层次结构。且其生命周期受到宿主Activity的影响。  

## 如何引入碎片？

### 静态引入：  
* 1.首先我们为碎片创建一个布局   
* 2.接着新建一个类承载该布局，该类要继承于Fragment类，且在该类中加载布局文件：  

        public class MyFragment extends Fragment{

            @override
            public View onCreateView(LayoutInflater inflater,ViewGroup 
            container,Bundle savedInstanceState){
                View view = 
                inflater.inflate(R.layout.myfragment,container,flase);
                return view;
            }
        }

* 3.然后在Activity的布局文件之中引用该碎片：  
          
        <LinearLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal"
            >
            <fragment
                android:id="@+id/myFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:name="com.example.fragmenttest.MyFragment"
                />//包名+上面新建的碎片类的名称
        </LinearLayout>
     通过android：name属性来显式指明要添加的碎片类名。  
这样就成功地在活动之中静态地引入了一个碎片了。    

### 动态加载：
在动态加载的过程中我们会需要一个容器来承载碎片，所以我们先在上面的布局的基础上进行修改：  

        <LinearLayout 
        xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="horizontal"
            >
            <fragment
                android:id="@+id/myFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:name="com.example.fragmenttest.MyFragment"
                android:layout_weight="1"
                />//包名+上面新建的碎片类的名称

            <FrameLayout
                android:id="@+id/new_fragmentLayout"
                android:layout_weight="1"
                android:width="0dp"
                android:height="match_parent"
                >    
            </FrameLayout>

        </LinearLayout>

接着的添加方法，我们参照《Android:第一行代码》中的步骤，即：
> 1.创建待添加的碎片实例  
> 2.获取FragmentManager,在活动中可以直接通过调用getSupportFragmentManager()  
方法得到。  
> 3.开启一个事务，通过调用beginTransaction()方法开启。   
> 4.向容器内添加或替换碎片，一般使用replace()方法实现，需要穿入容器的id和待添加的碎片实例。  
> 5.提交事务,调用commit()方法来完成。  

具体的方法：

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaciton = fragmentManager.beginTransaction();
        transaction.replace(R.id.new_fragmentLayout,fragment);
        transaction.commit();
    }

## 碎片的通信：

### 活动和碎片间的通信：  
虽然碎片是依赖于活动的，但是两者之间还是存在相对的独立性，两者拥有不同的布局文件，存在于不同的类中，比如如果想在活动中使用碎片中的方法，就需要两者间进行通信。  

幸运的是，FragmentManager中提供了一个findFragmentById()的方法，可以让我们获取相应碎片的实例，这样我们就可以调用碎片中的方法了。（PS：此处别忘了向下转型,
MyFragment fragment = (MyFragment) getSupportFragmentManager()
.findFragmentById(R.id.new_fragmentLayout);）

### 碎片和活动间的通信:
和上面的方法类似，在碎片之中也能轻松地调用活动之中的方法，可以使用getActivity()方法获得关联的Activity。     
        
        MainActivty activity = (MainActivity) getActivity();

### 碎片和碎片间的通信：    
碎片和碎片之间的通信无非是上面两种通信的结合，碎片先和活动进行通信，在用这个活动和另一个碎片进行通信。
