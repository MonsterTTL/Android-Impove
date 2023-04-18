# Android�����ʼǣ�Intent�ĳ���ʹ��

## Intent���õĸ�����
  Intent��һ����Ϣ���ݶ�����Ҫ�����ڸ��ֻ֮�����ת�������ת�������Ļ�����൱�ڸ������֮��ͨ�ŵ�ý�顣
## Intent�Ļ�������:
* 1.�������Activity��
* 2.��������Service�� 
* 3.���ݹ㲥��Broadcast��

## Intent����������  
* ��ʽIntent��ͨ���ṩĿ��Ӧ�õ���������ƻ���ȫ�޶������������ָ���ɴ��� Intent ��Ӧ�á�ͨ�����������Լ���Ӧ����ʹ����ʽ Intent �����������������Ϊ��֪��Ҫ������ Activity ���������������磬�����ܻ�������Ӧ���ڵ��� Activity ����Ӧ�û����������������������ں�̨�����ļ���  

* ��ʽIntent������ָ���ض����������������Ҫִ�еĳ���������Ӷ���������Ӧ���е�������������磬�����ڵ�ͼ�����û���ʾλ�ã������ʹ����ʽ Intent��������һ���д˹��ܵ�Ӧ���ڵ�ͼ����ʾָ����λ�á�  

## Intent��������
- ������ʽIntent�С�Intent ��������Ӧ���嵥�ļ���AndroidManifest.xml���е�һ�����ʽ������ָ�������Ҫ���յ� Intent ���͡����磬�������嵥�ļ��о����Activity��ǩ��Ϊ�û���Intent��������ʹ�����������ͨ�����������������������**���û��Ϊ�û�����κι���������ûֻ��ͨ����ʽIntent����**��  

- ������ Intent ���������ݣ���ϵͳ����ʾһ���Ի���֧���û�ѡȡҪʹ�õ�Ӧ�á� 


## Intent����Activity �� ���ַ�ʽ��  

### * 1.��ʽ������    
  ����ȷҪ�����ľ��������ַ�ʽ�����˵��Ϊ������ˡ�**��Ҫע����ǣ�Intent������ Service ʱ��Ӧʼ��ָ��������ơ��������޷�ȷ������������Ӧ Intent�����û��޷��������������������**   

  - 1.������ʽIntent��  
    Intent(Context, Class) ���캯���ֱ�ΪӦ�ú�����ṩ Context �� Class ���󡣵�һ��Context��Ҫ�����Ļ��������ģ�����ǰһ���������һ���ʱ��ֻҪ����ǰһ�����ʾ�������ˡ��ڶ���ΪҪ�����Ļ���ࡣ  

  - 2.��Intent������Ҫ�Ĳ��������紫�ݵ����ݵȡ�  

  - 3.����startActivity�����������������startActivityForResult������.  

    - ʾ����    
  
  ---------------------------------------------------------------------
    Intent newIntent = new Intent(this, NextActivity.class);
    newIntent.putExtra(��,ֵ);//��ֵ���룬��������ֵ��Ӧ����  
    startActivity(newIntent);//������һ���


### * 2.��ʽ������  
   �û�����û���κ�Ӧ�ô��������͵� startActivity() ����ʽ Intent�����ߣ����������ļ����ƻ����Աִ�е����ã������޷�����Ӧ�á�����������������������ʧ�ܣ�Ӧ��Ҳ�������Ҫ��֤ Activity �Ƿ����� Intent����� Intent ������� resolveActivity()��������Ϊ�ǿգ���������һ��Ӧ���ܹ������ Intent�����ҿ��԰�ȫ���� startActivity()��������Ϊ�գ���Ҫʹ�ø� Intent�����п��ܣ���Ӧͣ�÷����� Intent �Ĺ��ܡ�  

  - 1.����Intent  
      û��ʲô����֮�����ù��췽��û�в�����ֱ��new �����ˡ�  

  - 2.������������Activity�ı�ǩ,����action , category ��,��Ҫ˵�����������Ҫ��ʽ����Activity,ActivityӦ���������һ��Ĭ�ϵ�category :  
  < category android:name="android.intent.category.DEFAULT" />   

  - 3.����Ҫ���ݵ����ݵȲ�����   

  - 4.����startActivity�����������
    
    - �ٷ�ʾ�� :  
    ----------------------------------------------------------------------
         // Create the text message with a string
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND); //����ACTION��ǩ���������Ҫִ�еĲ���
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);//���ø�������
            sendIntent.setType("text/plain");//������������

            // ��֤��Intent���ٻᱻһ��Activity��Ӧ
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }

    ��ʾ����֤�������Intent�����ʱ��������һ��Activity��Ӧ ,����Ӧ�ñ���.       

   

