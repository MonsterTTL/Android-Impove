# Android������־

## ����������ͽ��������
>1.SDK�������߰汾��ƥ��  
  **�������**����Settings -�� System -�� Android�и���SDKTools����   
>2.build.gradle ���ļ������Ĳ�ͬ   
   û�й���������й�ƽ̨  
   �������Ҳ�в�ͬ


## �ʼǣ�
### 1.Android ��Ŀ���ļ��ṹ
  ����ProjectĿ¼�µ��ļ���  
  * 1. .gradle �� .idea �ļ���Android Studio �Զ����ɵ��ļ����������
  * 2. app �������Ŀ�еĴ��룬��Դ�ȣ������ǿ�����Ҫʹ�õ����ļ�   
  * 3. build ������һЩ����ʱ�Զ����ɵ��ļ�  
  * 4. gradle ����gradle warpper�������ļ�   
  * 5. .gitigonre ���ڰ汾���Ƶ��ļ�
  * 6. build.gradle ��Ŀȫ�ֵ�gradle�����ű�   
  * 7. gradle.properties ȫ�ֵ�gradle�����ļ������������õ����Խ�Ӱ�쵽��Ŀ�����е�gradle����ű�     
  * 8. gradlew��gradlew.bat�����������н�����ִ��gradle����ģ�����gradlew����Linux ��Mac ϵͳ��ʹ�õģ�gradlew.bat����Windows�����е�   
  * 9. MyApplication.iml��IDEA���Զ����ɵ�һ���ļ�   
  * 10. local.properties ����ָ�������е�Android SDK �ļ�·��.   
  * 11. settings.gradle ����ָ����Ŀ���������õ�ģ��.

### 2.app Ŀ¼�µĽṹ  
����appĿ¼�µ��ļ���  
* 1. build ����һЩ�ڱ���ʱ�Զ����ɵ��ļ�.   
* 2. libs �������Ŀ¼�µ�jar�����ᱻ�Զ���ӵ�����·����   
* 3. androidTest ������дAndroid Test���������ģ����Զ���Ŀ����һЩ�Զ�������  
* 4. java ������������Java����ĵط�    
* 5. res ��Ŀ������ʹ�õ���ͼƬ�����֣��ַ�������Դ    
* 6. AndroidMainifest.xml ֪ʶ����Android��Ŀ�������ļ��������ж�����Ĵ��������Ҫ������ע�ᣬ���⻹����������ļ��и�Ӧ�ó������Ȩ������.    
* 7. test ������дUnit Test ���������ģ� �Ƕ���Ŀ�����Զ������Ե���һ�ַ�ʽ.   
* 8. .gitigonre ���ڽ�appģ���ڵ�ָ����Ŀ¼���ļ��ų��ڰ汾����֮��.   
* 9. app.iml IDEA�Զ����ɵ��ļ�    
* 10. build.gradle ����appģ���gradle�����ű�    
* 11. proguard-rules.pro ����ָ����Ŀ����Ļ�������

### 3.Android�Ĵ������
>1.�(activity)����   
>2.����(Service)    
>3.�㲥������(BroadCast Receiver)���ⲿ������Ϣ  
>4.�����ṩ��(Content Provider)���ڲ�������Ϣ

### 4.Log��־���
 **Log.�ȼ�(tag,meg);**  
  -  ->5���ȼ�
      {
        v - ���   
        d   
        i   
        w   
        e - ���
      }
  -  tag һ���Ǹ����������ڹ���   
  - mes ��������Ҫ��ӡ������    
 **��ݼ�**  
 ��onCreate����������logt��������һ������������ʹ�á�  
 **������** 
 ���ڹ�����־  
