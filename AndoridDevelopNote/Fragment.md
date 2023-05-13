# Android�����ʼǣ�̽��Fragment(��Ƭ)

## ��Ϊ��Ƭ��   

�ٷ��ĵ����ᵽ��
> A fragment represents a **modular portion of the user interface** within an activity. A fragment has its own lifecycle, receives its own input events, and you can add or remove fragments while the containing activity is running.

�����ᵽ����Ҫ��һ�������Ƭ��ģ�黯�ģ���UI��һ���֡�ͬʱ��Ƭ����͹����Լ��Ĳ��֣������Լ����������ڣ����ҿ��Դ����Լ��������¼���    

���ǿ��Զ����ص����һЩ�ܽ᣺
### �ص㣺
* 1.��ʾӦ�ý����п��ظ�ʹ�õ�һ����  
* 2.��Զ��������Լ��Ĳ��֣������¼����������ڵ�  
* 3.Fragment ���ܶ������ڣ����Ǳ����� Activity ����һ�� Fragment �йܡ�Fragment ����ͼ��νṹ���Ϊ��������ͼ��νṹ��һ���֣��򸽼ӵ���������ͼ��νṹ���������������ܵ�����Activity��Ӱ�졣  

## ���������Ƭ��

### ��̬���룺  
* 1.��������Ϊ��Ƭ����һ������   
* 2.�����½�һ������ظò��֣�����Ҫ�̳���Fragment�࣬���ڸ����м��ز����ļ���  

        public class MyFragment extends Fragment{

            @override
            public View onCreateView(LayoutInflater inflater,ViewGroup 
            container,Bundle savedInstanceState){
                View view = 
                inflater.inflate(R.layout.myfragment,container,flase);
                return view;
            }
        }

* 3.Ȼ����Activity�Ĳ����ļ�֮�����ø���Ƭ��  
          
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
                />//����+�����½�����Ƭ�������
        </LinearLayout>
     ͨ��android��name��������ʽָ��Ҫ��ӵ���Ƭ������  
�����ͳɹ����ڻ֮�о�̬��������һ����Ƭ�ˡ�    

### ��̬���أ�
�ڶ�̬���صĹ��������ǻ���Ҫһ��������������Ƭ������������������Ĳ��ֵĻ����Ͻ����޸ģ�  

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
                />//����+�����½�����Ƭ�������

            <FrameLayout
                android:id="@+id/new_fragmentLayout"
                android:layout_weight="1"
                android:width="0dp"
                android:height="match_parent"
                >    
            </FrameLayout>

        </LinearLayout>

���ŵ���ӷ��������ǲ��ա�Android:��һ�д��롷�еĲ��裬����
> 1.��������ӵ���Ƭʵ��  
> 2.��ȡFragmentManager,�ڻ�п���ֱ��ͨ������getSupportFragmentManager()  
�����õ���  
> 3.����һ������ͨ������beginTransaction()����������   
> 4.����������ӻ��滻��Ƭ��һ��ʹ��replace()����ʵ�֣���Ҫ����������id�ʹ���ӵ���Ƭʵ����  
> 5.�ύ����,����commit()��������ɡ�  

����ķ�����

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaciton = fragmentManager.beginTransaction();
        transaction.replace(R.id.new_fragmentLayout,fragment);
        transaction.commit();
    }

## ��Ƭ��ͨ�ţ�

### �����Ƭ���ͨ�ţ�  
��Ȼ��Ƭ�������ڻ�ģ���������֮�仹�Ǵ�����ԵĶ����ԣ�����ӵ�в�ͬ�Ĳ����ļ��������ڲ�ͬ�����У�����������ڻ��ʹ����Ƭ�еķ���������Ҫ���߼����ͨ�š�  

���˵��ǣ�FragmentManager���ṩ��һ��findFragmentById()�ķ��������������ǻ�ȡ��Ӧ��Ƭ��ʵ�����������ǾͿ��Ե�����Ƭ�еķ����ˡ���PS���˴�����������ת��,
MyFragment fragment = (MyFragment) getSupportFragmentManager()
.findFragmentById(R.id.new_fragmentLayout);��

### ��Ƭ�ͻ���ͨ��:
������ķ������ƣ�����Ƭ֮��Ҳ�����ɵص��û֮�еķ���������ʹ��getActivity()������ù�����Activity��     
        
        MainActivty activity = (MainActivity) getActivity();

### ��Ƭ����Ƭ���ͨ�ţ�    
��Ƭ����Ƭ֮���ͨ���޷�����������ͨ�ŵĽ�ϣ���Ƭ�Ⱥͻ����ͨ�ţ�������������һ����Ƭ����ͨ�š�
