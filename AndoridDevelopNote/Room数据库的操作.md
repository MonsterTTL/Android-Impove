# JetPack开发笔记：Room数据库的详解与基础使用    

## 简介：   
Android开发中我们常常会用到持久化数据保存手段，即将一些数据保存到本地。  
    其中比较常用的有两种：    
-    1.通过SharedPreferences这种轻量化手段，利用键值对存储一些关系并不复杂的数据。   
-    2.通过Android内置的SQLite数据库进行关系较为复杂的数据的存储。    

Android提供了API帮助我们操作SQLite数据库，但是这种方法较为复杂。而利用JetPack中
的Room数据库，我们可以较为方便地操作SQLite数据库。   

> Room 持久性库在 SQLite 上提供了一个抽象层，以便在充分利用 SQLite 的强大功能的同时，能够流畅地访问数据库。具体来说，Room 具有以下优势：
>- 1.针对 SQL 查询的编译时验证。   
>- 2.可最大限度减少重复和容易出错的样板代码的方便注解。  
>- 3.简化了数据库迁移路径。  

**出于这些方面的考虑，现在Google官方已经建议我们使用Room，而不是直接使用SQLite
API**  

## 添加依赖：   
 我们需要在应用的build.gradle文件下添加几行依赖：   

    implementation 'androidx.room:room-common:2.3.0'
    implementation 'androidx.room:room-runtime:2.4.3'
    annotationProcessor "androidx.room:room-compiler:2.4.3"  

## Room数据库的三个主要组件：  
   Room的三个主要组件分别对应SQLite数据库的数据库本身，表以及对数据库的操作：   
   - 1. 数据库类，用于保存数据库并作为应用持久性数据底层连接的主要访问点。  
   对应数据库本身。   

   - 2. 数据实体，用于表示应用的数据库中的表。  
   对应数据库中的表。  

   - 3. 数据访问对象 (DAO)，提供您的应用可用于查询、更新、插入和删除数据库中的数据的方法。    
   对应具体的操作方法    

   在具体使用Room时，我们也需要具体实现这三个类，以帮助Room数据库创建，读写数据。   

## 开始使用Room数据库   

### 1.定义实体类  
 上面说到，数据实体对应的是数据库中的表，那么数据实体中的字段就对应的是表中每一列的数据，例如：
    
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

此示例就实现了一个简单的数据实体类，对应的就是一张具有四列，n行的数据表:

在定义数据实体类时，我们需要加上@Entity注解，表示该类为一个数据实体类。默认情况下，Room将类名用作数据库表的名称，如果我们希望自定义表名，可以 **@Entity**注解的**tableName** 属性。      
   比如，我们修改上述代码为：

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

   此时，表的名称就为"MyTable"了。      

类似的，默认情况下Room也会将每一个字段的名称用作数据库表中列的名称。我们上述代码中默认的列名就分别为:first_Name,last_Name,score。但我们也可以将@ColumnInfo注解添加到该字段并设置**name** 属性，比如我们设置了每一个变量的@ColumnInfo注解的name属性，这样数据库表中的每一列名称就是我们设置的name属性了。      

另外，每一张数据表也总会有一个主键，对应到数据实体中我们需要给对应到主键的字段加上@PrimaryKey注解，如果我们需要Room为实体实例分配自动ID，可以将@PrimaryKey的autoGenerate属性设为true。

 #### 定义复合主键： 
 > 如果您需要通过多个列的组合对实体实例进行唯一标识，则可以通过列出 @Entity 的 primaryKeys 属性中的以下列定义一个复合主键：

        @Entity(primaryKeys = {"firstName", "lastName"})
            public class User {
            public String firstName;
            public String lastName;
            }

 #### 忽略字段：  
  如果我们有时想忽略掉实体类中的一些字段，让其不被存入Room数据库表中，我们可以使用@Ignore注解为这些字段添加注解，官方示例：

        @Entity
        public class User {
            @PrimaryKey
            public int id;

            public String firstName;
            public String lastName;

            @Ignore
            Bitmap picture;
        }           
 
 ### 创建数据访问对象(DAO):   
 >官方简介：当您使用 Room 持久性库存储应用的数据时，您可以通过定义数据访问对象 (DAO) 与存储的数据进行交互。每个 DAO 都包含一些方法，这些方法提供对应用数据库的抽象访问权限。在编译时，Room 会自动为您定义的 DAO 生成实现。 

 我们可以用接口或者抽象类来定义DAO，一般我们使用的是接口(interface)，切我们需要在类前加上@DAO注解。 一个具体的DAO中定义了一个或多个方法来实现对数据库表的增删改查的操作。   

 #### 插入操作
 借助 **@Insert**注释，我们可以定义将其参数插入到数据库中的相应表中的方法。我们无需实现具体的操作，而只需要定义方法的形式即可。实例：  
    
    @Dao
    public interface UserDao {
        @Insert
        public void insertUsers(User... users);

        @Insert
        public void insertBothUsers(User user1, User user2);

        @Insert
        public void insertUsersAndFriends(User user, List<User> friends);
    }


 @Insert方法的每个参数**必须**是带有**@Entity注解**的Room数据实体类的实例或数据实体类实例的集合。调用 @Insert 方法时，Room 会将每个传递的实体实例插入到相应的数据库表中。    

 如果 @Insert 方法接收单个参数，则会返回 long 值，这是插入项的新 rowId。如果参数是数组或集合，则该方法应改为返回由 long 值组成的数组或集合，并且每个值都作为其中一个插入项的 rowId。   

 #### 更新操作   
  借助@Update注释，我们可以实现更新数据库表中特定的行的方法。@Update 方法接受数据实体实例作为参数。 官方示例：   
    
    @Dao
    public interface UserDao {
        @Update
        public void updateUsers(User... users);
    }
 
 需要注意的是，Room是根据主键来判断哪一行数据需要进行更新的，当传入的实体的主键与数据库表中的某一行的主键相匹配时，Room才会进行修改，否则将不会进行修改。@Update 方法可以选择性地返回 int 值，该值指示成功更新的行数。   

 #### 删除操作   
  借助@Delete注释    
  删除操作与更新操作也相类似，也是根据传入实体的主键来匹配需要删除的数据行。  
  官方示例：   
    
    @Dao
    public interface UserDao {
        @Delete
        public void deleteUsers(User... users);
    }

 #### 查询操作   
  借助@Query注释  
  以下代码定义了一个方法，该方法使用简单的 SELECT 查询返回数据库中的所有 User 对象,即查询User表中的所有数据：   

        @Query("SELECT * FROM user")//SQLite中不区分大小写
        public User[] loadAllUsers();   

  简单的按参数查询：具体的查询条件有以下几种：  
  * 1. 大于 或 小于 或 等于，对应的是 > , < , =  

            @Query("SELECT * FROM user WHERE age > :minAge")
            public User[] loadAllUsersOlderThan(int minAge);

  * 2. 相等 可以使用LIKE 关键字  

            @Query("SELECT * FROM user WHERE first_name LIKE :search " +
                "OR last_name LIKE :search")
            public List<User> findUserWithName(String search);

  * 3. 条件在一个区间之内 可以使用BETWEEN 关键字   

            @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
            public User[] loadAllUsersBetweenAges(int minAge, int maxAge);

  * 4. 条件是表中是否含有所给的集合之中的数据，即是否有交集    

            @Query("SELECT * FROM user WHERE region IN (:regions)")
            public List<User> loadUsersFromRegions(List<String> regions);  

    #### 返回子集：  
      有时我们查询数据但是并不需要表中每一行中的所有数据，通过Room我们就可以只返回一行数据中的几个数据。 我们需要通过具体的映射来将需要的数据映射到一个具体的类中：  

            public class NameTuple {
            @ColumnInfo(name = "first_name")
            public String firstName;

            @ColumnInfo(name = "last_name")
            @NonNull
            public String lastName;
           }             

     利用ColumnInfo的name属性，我们将数据表中的first_name 列映射到  
     NameTuple的firstName 字段， last_name 映射到 lastName 字段 ，然后，我们可以从查询方法中返回该子集：   

            public class NameTuple {
            @ColumnInfo(name = "first_name")
            public String firstName;

            @ColumnInfo(name = "last_name")
            @NonNull
            public String lastName;
            }       
 ### 创建数据库类：
   该类会具有一些规范：  
   > AppDatabase 定义数据库配置，并作为应用对持久性数据的主要访问点。数据库类必须满足以下条件：  
   > 1.该类必须带有 @Database 注解，该注解包含列出所有与数据库关联的数据实体的 entities 数组。   
   > 2.该类必须是一个抽象类，用于扩展 RoomDatabase。       
   > 3.对于与数据库关联的每个 DAO 类，数据库类必须定义一个具有零参数的抽象方法，并返回 DAO 类的实例。       

   示例代码：   

        @Database(entities = {User.class}, version = 1)
        public abstract class AppDatabase extends RoomDatabase {
            public abstract UserDao userDao();
        }

    该数据库中只含有一张User表。     

   ### 具体使用：   
   以上三个准备工作完成了后，我们就可以在具体的代码中使用Room数据库了。

        首先我们需要创建数据库示例：       
                         
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();

   其中，第一个参数为整个应用程序的顶层上下文，第二个参数为数据库类，第三个参数为数据库的名称，最后调用build方法完成创建。   

   有了具体的数据库后我们就可以使用DAO来对其进行具体的操作了，还记得我们在
   数据库类中有一个返回相应的DAO的方法，我们可以用其来获取DAO：  

            UserDao userDao = db.userDao();

   最后，我们就可以调用之前定义在DAO中的方法来具体地操作数据库了：     

           List<User> users = userDao.getAll();         




    


