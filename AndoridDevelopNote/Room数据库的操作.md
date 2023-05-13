# JetPack�����ʼǣ�Room���ݿ����������ʹ��    

## ��飺   
Android���������ǳ������õ��־û����ݱ����ֶΣ�����һЩ���ݱ��浽���ء�  
    ���бȽϳ��õ������֣�    
-    1.ͨ��SharedPreferences�����������ֶΣ����ü�ֵ�Դ洢һЩ��ϵ�������ӵ����ݡ�   
-    2.ͨ��Android���õ�SQLite���ݿ���й�ϵ��Ϊ���ӵ����ݵĴ洢��    

Android�ṩ��API�������ǲ���SQLite���ݿ⣬�������ַ�����Ϊ���ӡ�������JetPack��
��Room���ݿ⣬���ǿ��Խ�Ϊ����ز���SQLite���ݿ⡣   

> Room �־��Կ��� SQLite ���ṩ��һ������㣬�Ա��ڳ������ SQLite ��ǿ���ܵ�ͬʱ���ܹ������ط������ݿ⡣������˵��Room �����������ƣ�
>- 1.��� SQL ��ѯ�ı���ʱ��֤��   
>- 2.������޶ȼ����ظ������׳�����������ķ���ע�⡣  
>- 3.�������ݿ�Ǩ��·����  

**������Щ����Ŀ��ǣ�����Google�ٷ��Ѿ���������ʹ��Room��������ֱ��ʹ��SQLite
API**  

## ���������   
 ������Ҫ��Ӧ�õ�build.gradle�ļ�����Ӽ���������   

    implementation 'androidx.room:room-common:2.3.0'
    implementation 'androidx.room:room-runtime:2.4.3'
    annotationProcessor "androidx.room:room-compiler:2.4.3"  

## Room���ݿ��������Ҫ�����  
   Room��������Ҫ����ֱ��ӦSQLite���ݿ�����ݿⱾ�����Լ������ݿ�Ĳ�����   
   - 1. ���ݿ��࣬���ڱ������ݿⲢ��ΪӦ�ó־������ݵײ����ӵ���Ҫ���ʵ㡣  
   ��Ӧ���ݿⱾ��   

   - 2. ����ʵ�壬���ڱ�ʾӦ�õ����ݿ��еı�  
   ��Ӧ���ݿ��еı�  

   - 3. ���ݷ��ʶ��� (DAO)���ṩ����Ӧ�ÿ����ڲ�ѯ�����¡������ɾ�����ݿ��е����ݵķ�����    
   ��Ӧ����Ĳ�������    

   �ھ���ʹ��Roomʱ������Ҳ��Ҫ����ʵ���������࣬�԰���Room���ݿⴴ������д���ݡ�   

## ��ʼʹ��Room���ݿ�   

### 1.����ʵ����  
 ����˵��������ʵ���Ӧ�������ݿ��еı���ô����ʵ���е��ֶξͶ�Ӧ���Ǳ���ÿһ�е����ݣ����磺
    
    @Entity
    public class User {
        @PrimaryKey
        public int uid;

        @ColumnInfo(name = "firstName")
        public String firstName;

        @ColumnInfo(name = "lastName")
        public String lastName;

        @ColumnInfo(name = "score")
        public int score;
            
            }

��ʾ����ʵ����һ���򵥵�����ʵ���࣬��Ӧ�ľ���һ�ž������У�n�е����ݱ�:

�ڶ�������ʵ����ʱ��������Ҫ����@Entityע�⣬��ʾ����Ϊһ������ʵ���ࡣĬ������£�Room�������������ݿ������ƣ��������ϣ���Զ������������ **@Entity**ע���**tableName** ���ԡ�      
   ���磬�����޸���������Ϊ��

     @Entity(tableName = "MyTable")
     public class User {
        @PrimaryKey
        public int uid;

        @ColumnInfo(name = "firstName")
        public String first_Name;

        @ColumnInfo(name = "lastName")
        public String last_Name;

        @ColumnInfo(name = "score")
        public int score;
            
            }

   ��ʱ��������ƾ�Ϊ"MyTable"�ˡ�      

���Ƶģ�Ĭ�������RoomҲ�Ὣÿһ���ֶε������������ݿ�����е����ơ���������������Ĭ�ϵ������ͷֱ�Ϊ:first_Name,last_Name,score��������Ҳ���Խ�@ColumnInfoע����ӵ����ֶβ�����**name** ���ԣ���������������ÿһ��������@ColumnInfoע���name���ԣ��������ݿ���е�ÿһ�����ƾ����������õ�name�����ˡ�      

���⣬ÿһ�����ݱ�Ҳ�ܻ���һ����������Ӧ������ʵ����������Ҫ����Ӧ���������ֶμ���@PrimaryKeyע�⣬���������ҪRoomΪʵ��ʵ�������Զ�ID�����Խ�@PrimaryKey��autoGenerate������Ϊtrue��

 #### ���帴�������� 
 > �������Ҫͨ������е���϶�ʵ��ʵ������Ψһ��ʶ�������ͨ���г� @Entity �� primaryKeys �����е������ж���һ������������

        @Entity(primaryKeys = {"firstName", "lastName"})
            public class User {
            public String firstName;
            public String lastName;
            }

 #### �����ֶΣ�  
  ���������ʱ����Ե�ʵ�����е�һЩ�ֶΣ����䲻������Room���ݿ���У����ǿ���ʹ��@Ignoreע��Ϊ��Щ�ֶ����ע�⣬�ٷ�ʾ����

        @Entity
        public class User {
            @PrimaryKey
            public int id;

            public String firstName;
            public String lastName;

            @Ignore
            Bitmap picture;
        }           
 
 ### �������ݷ��ʶ���(DAO):   
 >�ٷ���飺����ʹ�� Room �־��Կ�洢Ӧ�õ�����ʱ��������ͨ���������ݷ��ʶ��� (DAO) ��洢�����ݽ��н�����ÿ�� DAO ������һЩ��������Щ�����ṩ��Ӧ�����ݿ�ĳ������Ȩ�ޡ��ڱ���ʱ��Room ���Զ�Ϊ������� DAO ����ʵ�֡� 

 ���ǿ����ýӿڻ��߳�����������DAO��һ������ʹ�õ��ǽӿ�(interface)����������Ҫ����ǰ����@DAOע�⡣ һ�������DAO�ж�����һ������������ʵ�ֶ����ݿ�����ɾ�Ĳ�Ĳ�����   

 #### �������
 ���� **@Insert**ע�ͣ����ǿ��Զ��彫��������뵽���ݿ��е���Ӧ���еķ�������������ʵ�־���Ĳ�������ֻ��Ҫ���巽������ʽ���ɡ�ʵ����  
    
    @Dao
    public interface UserDao {
        @Insert
        public void insertUsers(User... users);

        @Insert
        public void insertBothUsers(User user1, User user2);

        @Insert
        public void insertUsersAndFriends(User user, List<User> friends);
    }


 @Insert������ÿ������**����**�Ǵ���**@Entityע��**��Room����ʵ�����ʵ��������ʵ����ʵ���ļ��ϡ����� @Insert ����ʱ��Room �Ὣÿ�����ݵ�ʵ��ʵ�����뵽��Ӧ�����ݿ���С�    

 ��� @Insert �������յ�����������᷵�� long ֵ�����ǲ�������� rowId���������������򼯺ϣ���÷���Ӧ��Ϊ������ long ֵ��ɵ�����򼯺ϣ�����ÿ��ֵ����Ϊ����һ��������� rowId��   

 #### ���²���   
  ����@Updateע�ͣ����ǿ���ʵ�ָ������ݿ�����ض����еķ�����@Update ������������ʵ��ʵ����Ϊ������ �ٷ�ʾ����   
    
    @Dao
    public interface UserDao {
        @Update
        public void updateUsers(User... users);
    }
 
 ��Ҫע����ǣ�Room�Ǹ����������ж���һ��������Ҫ���и��µģ��������ʵ������������ݿ���е�ĳһ�е�������ƥ��ʱ��Room�Ż�����޸ģ����򽫲�������޸ġ�@Update ��������ѡ���Եط��� int ֵ����ֵָʾ�ɹ����µ�������   

 #### ɾ������   
  ����@Deleteע��    
  ɾ����������²���Ҳ�����ƣ�Ҳ�Ǹ��ݴ���ʵ���������ƥ����Ҫɾ���������С�  
  �ٷ�ʾ����   
    
    @Dao
    public interface UserDao {
        @Delete
        public void deleteUsers(User... users);
    }

 #### ��ѯ����   
  ����@Queryע��  
  ���´��붨����һ���������÷���ʹ�ü򵥵� SELECT ��ѯ�������ݿ��е����� User ����,����ѯUser���е��������ݣ�   

        @Query("SELECT * FROM user")//SQLite�в����ִ�Сд
        public User[] loadAllUsers();   

  �򵥵İ�������ѯ������Ĳ�ѯ���������¼��֣�  
  * 1. ���� �� С�� �� ���ڣ���Ӧ���� > , < , =  

            @Query("SELECT * FROM user WHERE age > :minAge")
            public User[] loadAllUsersOlderThan(int minAge);

  * 2. ��� ����ʹ��LIKE �ؼ���  

            @Query("SELECT * FROM user WHERE first_name LIKE :search " +
                "OR last_name LIKE :search")
            public List<User> findUserWithName(String search);

  * 3. ������һ������֮�� ����ʹ��BETWEEN �ؼ���   

            @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
            public User[] loadAllUsersBetweenAges(int minAge, int maxAge);

  * 4. �����Ǳ����Ƿ��������ļ���֮�е����ݣ����Ƿ��н���    

            @Query("SELECT * FROM user WHERE region IN (:regions)")
            public List<User> loadUsersFromRegions(List<String> regions);  

    #### �����Ӽ���  
      ��ʱ���ǲ�ѯ���ݵ��ǲ�����Ҫ����ÿһ���е��������ݣ�ͨ��Room���ǾͿ���ֻ����һ�������еļ������ݡ� ������Ҫͨ�������ӳ��������Ҫ������ӳ�䵽һ����������У�  

            public class NameTuple {
            @ColumnInfo(name = "first_name")
            public String firstName;

            @ColumnInfo(name = "last_name")
            @NonNull
            public String lastName;
           }             

     ����ColumnInfo��name���ԣ����ǽ����ݱ��е�first_name ��ӳ�䵽  
     NameTuple��firstName �ֶΣ� last_name ӳ�䵽 lastName �ֶ� ��Ȼ�����ǿ��ԴӲ�ѯ�����з��ظ��Ӽ���   

            public class NameTuple {
            @ColumnInfo(name = "first_name")
            public String firstName;

            @ColumnInfo(name = "last_name")
            @NonNull
            public String lastName;
            }       
 ### �������ݿ��ࣺ
   ��������һЩ�淶��  
   > AppDatabase �������ݿ����ã�����ΪӦ�öԳ־������ݵ���Ҫ���ʵ㡣���ݿ��������������������  
   > 1.���������� @Database ע�⣬��ע������г����������ݿ����������ʵ��� entities ���顣   
   > 2.���������һ�������࣬������չ RoomDatabase��       
   > 3.���������ݿ������ÿ�� DAO �࣬���ݿ�����붨��һ������������ĳ��󷽷��������� DAO ���ʵ����       

   ʾ�����룺   

        @Database(entities = {User.class}, version = 1)
        public abstract class AppDatabase extends RoomDatabase {
            public abstract UserDao userDao();
        }

    �����ݿ���ֻ����һ��User��     

   ### ����ʹ�ã�   
   ��������׼����������˺����ǾͿ����ھ���Ĵ�����ʹ��Room���ݿ��ˡ�

        ����������Ҫ�������ݿ�ʾ����       
                         
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();

   ���У���һ������Ϊ����Ӧ�ó���Ķ��������ģ��ڶ�������Ϊ���ݿ��࣬����������Ϊ���ݿ�����ƣ�������build������ɴ�����   

   ���˾�������ݿ�����ǾͿ���ʹ��DAO��������о���Ĳ����ˣ����ǵ�������
   ���ݿ�������һ��������Ӧ��DAO�ķ��������ǿ�����������ȡDAO��  

            UserDao userDao = db.userDao();

   ������ǾͿ��Ե���֮ǰ������DAO�еķ���������ز������ݿ��ˣ�     

           List<User> users = userDao.getAll();         




    


