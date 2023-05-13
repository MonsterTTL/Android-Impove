# Android�����ʼǣ�XML���ݵĽ���

## ��ΪXML���ݣ�
    
XML ָ����չ������ԣ�eXtensible Markup Language����   
����չ������ԣ�Ӣ�Extensible Markup Language����ƣ�XML����һ�ֱ�����ԣ��Ǵӱ�׼ͨ�ñ�����ԣ�SGML���м��޸ĳ����ġ�  **������Ҫ�Ĺ��ܾ���Ϊ�˷������ݵĴ����뽻����**     


***��Android�����У�������ʱ��Ҳ��Ҫ�ӷ������ϻ�ȡxml���ݲ����Խ���***


## ��ν���XML���ݣ�

### 1.Pull����   
�������Ǹ��ݹȸ�ٷ��Ŀ������ĵ��ṩ�ķ�����Ҳ�����Ƽ��ķ���������xml���ݡ�

- 1.����Feed�и���Ȥ�ı�ǩ����  
    ���磺

        <?xml version="1.0" encoding="utf-8"?>
        <feed xmlns="http://www.w3.org/2005/Atom" xmlns:creativeCommons="http://backend.userland.com/creativeCommonsRssModule" ...">
        <title type="text">newest questions tagged android - Stack Overflow</title>
        ...
            <entry>
            ...
            </entry>
            <entry>
                <id>http://stackoverflow.com/q/9439999</id>
                <re:rank scheme="http://stackoverflow.com">0</re:rank>
                <title type="text">Where is my data file?</title>
                <category scheme="http://stackoverflow.com/feeds/tag?
                tagnames=android&sort=newest/tags" term="android"/>
                <category scheme="http://stackoverflow.com/feeds/tag?
                tagnames=android&sort=newest/tags" term="file"/>
                <author>
                    <name>cliff2310</name>
                    <uri>http://stackoverflow.com/users/1128925</uri>
                </author>
                <link rel="alternate" href="http://stackoverflow.com/
                questions/9439999/where-is-my-data-file" />
                <published>2012-02-25T00:30:54Z</published>
                <updated>2012-02-25T00:30:54Z</updated>
                <summary type="html">
                    <p>I have an Application that requires a data 
                    file...</p>

                </summary>
            </entry>
            <entry>
            ...
            </entry>
        ...
        </feed>

����һ��xml���ݣ�����������Ӧ<>�е����ݼ�Ϊһ����ǩ�е����ݣ�����˵entry��ǩ��Ƕ�׵�id��ǩΪ��

        <id>http://stackoverflow.com/q/9439999</id>

�����ݼ�Ϊhttp://stackoverflow.com/q/9439999


- 2.ѡ�������   
    Ϊ�˽���xml���ݣ�������Ҫѡ��һЩ���������������Ƿ������ݡ�

    �ٷ��ĵ����ᵽ��

        XmlPullParser������һ���� Android �Ͻ��� XML �ĸ�Ч�ҿ�ά���ķ�ʽ����ǰ��Android �д˽ӿڵ�����ʵ�֣�

        KXmlParser��ͨ�� XmlPullParserFactory.newPullParser()����
        ExpatPullParser��ͨ�� Xml.newPullParser()����
        ��һѡ�񶼿��ԡ��˲����е�ʾ��ʹ�� ExpatPullParser��ͨ�� Xml.newPullParser()����


- 3.ʵ����������   

    ǰ���ᵽ�����о�������ַ�ʽʵ�������������ֱ��� �ù��������� ���� ֱ����ʵ�����ɡ����磺

        ���������ɣ�

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();        

        ʵ�����ɣ�

            XmlPullParser xmlPullParser = Xml.newPullParser();


    ������ѡ��һ���ɡ�

 - 4.Ȼ�����ǿ��Ծ�������һ�½�����

            public class StackOverflowXmlParser {
            
            private static final String ns = null;

            public List parse(InputStream in) throws XmlPullParserException, IOException {
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.
                    FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    parser.nextTag();
                    return readFeed(parser);
                } finally {
                    in.close();
                }
             }
                ...
            }

    - ���У�parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);��һ���������������ƿռ�ģ���ȻsetFeature�����������������ý�����������һЩ���������Բ鿴�ٷ����ĵ���   
    - parser.setInput(in, null);�������˾�����������ͱ����ʽ���������Ϊnull��ʹ��ϵͳĬ�ϵı��롣�˴�������ֻ����һ���ֽ���(Reader),���磺

            xmlPullParser.setInput(new StringReader(xmlData));

    - parser.nextTag();���� nextTag() ��ʼ�������� ,�ٷ����ĵ��������������ģ�  
    > Call next() and return event if it is START_TAG or END_TAG otherwise throw an exception. It will skip whitespace TEXT before actual tag if any.  

    ����˵�������START_TAG��END_TAG�������next()�������¼��������׳��쳣����������ʵ�ʱ��֮ǰ�Ŀհ�TEXT(����еĻ�)�����ʴ��룺

             int eventType = next();
            if(eventType == TEXT &&  isWhitespace()) {   // skip whitespace
                eventType = next();
            }
            if (eventType != START_TAG &&  eventType != END_TAG) {
                throw new XmlPullParserException("expected start or end 
                tag", this, null);
            }
            return eventType;
                
    - next()��������������ȡ��һ�������¼��ġ�

- 5.��������ķ�������������

        private List readFeed(XmlPullParser parser) throws 
        XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("entry")) {
                    entries.add(readEntry(parser));
                } else {
                    skip(parser);
                }
            }
            return entries;
        }

    - ����һ��ʼ��require�������������������ģ������������������ֱ���Ԥ�ڵ��¼����ͣ����ƿռ䣬���ơ�
    �������Ե�ǰ�¼��Ƿ����ڸ��������ͣ��Լ����ƿռ�������Ƿ�ƥ�䡣Null��ƥ���κ����ƿռ���κ����ơ��������δͨ�������׳��쳣���쳣�ı�ָʾ������λ�á�Ԥ���¼��Ͳ���������ĵ�ǰ�¼���      

    - �����next�¼����ܷ��ص�����ֵ��XmlPullParser.START_TAG �� XmlPullParser.END_TAG �ֱ��Ӧ���ǿ�ʼ����һ���ڵ�����һ���ڵ�Ľ����ı�־������һ����Ҫ�ı�־��XmlPullParser.END_DOCUMENT,��Ӧ���ǽ���������ɡ�

    - skip����������������������Ȥ�ı�ǩ�ġ�  

            private void skip(XmlPullParser parser) throws 
            XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
                }
            }
          }

    �����������뵽entry��ǩ�н��н�һ���Ľ�����

        public static class Entry {
        public final String title;
        public final String link;
        public final String summary;

        private Entry(String title, String summary, String link) {
            this.title = title;
            this.summary = summary;
            this.link = link;
        }
       }

        // Parses the contents of an entry. If it encounters a title,
         summary, or link tag, hands them off
        // to their respective "read" methods for processing. 
        Otherwise, skips the tag.
        private Entry readEntry(XmlPullParser parser) throws 
        XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, ns, "entry");
            String title = null;
            String summary = null;
            String link = null;
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("title")) {
                    title = readTitle(parser);
                } else if (name.equals("summary")) {
                    summary = readSummary(parser);
                } else if (name.equals("link")) {
                    link = readLink(parser);
                } else {
                    skip(parser);
                }
            }
            return new Entry(title, summary, link);
        }

        // Processes title tags in the feed.
        private String readTitle(XmlPullParser parser) throws 
        IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "title");
            String title = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "title");
            return title;
        }

        // Processes link tags in the feed.
        private String readLink(XmlPullParser parser) throws 
        IOException, XmlPullParserException {
            String link = "";
            parser.require(XmlPullParser.START_TAG, ns, "link");
            String tag = parser.getName();
            String relType = parser.getAttributeValue(null, "rel");
            if (tag.equals("link")) {
                if (relType.equals("alternate")){
                    link = parser.getAttributeValue(null, "href");
                    parser.nextTag();
                }
            }
            parser.require(XmlPullParser.END_TAG, ns, "link");
            return link;
        }

        // Processes summary tags in the feed.
        private String readSummary(XmlPullParser parser) throws 
        IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "summary");
            String summary = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "summary");
            return summary;
        }

        // For the tags title and summary, extracts their text values.
        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }
        ...
        }

    ����ٷ�ʾ��д�ıȽϸ��ӣ�����ģ�黯���ĽϺá����ﴴ����һ����̬�ڲ���Entry��������֯���������ݡ�  ���ȿ�ʼ��ȡ������getName()��������ȡ�ڵ�����ƣ�����ÿ������Ľڵ㣬Ҳ�ֱ�д�˲�ͬ�ķ�������ȡ��  

    - 1. ������������������Ķ�ȡ����readText��������ʵ�����ǶԽ������ṩ��getText�����ķ�װ������������һ���¼�ΪTEXTʱ��������getText�����������ݷ��س�ȥ��

    - 2. ������ʵ�����Ľ�����������ͬС�죬����Ҫ���ݱ�ǩ�ڲ��������������ƾ���Ľ����߼�������ʵ���бȽ������readLink������������һ��getAttributeValue�������Ǹ������ƿռ�;����������������ȡ����ֵ�ģ�
    ��������Ĵ��룺 

          String relType = parser.getAttributeValue(null, "rel");

    �������������ƿռ䣬��ȡ��Link��ǩ��rel�ľ����ֵ��
    Ȼ���ٸ���rel��ֵ�������Ƿ����href��ֵ��   

 ### 2.�򵥵�Pull����
  �����Pull����δ��̫����������Android��һ�д�����Ҳ�н�Ϊ�򵥵Ľ�����  
   Ҫ���������ݣ�

        <apps>
            <app>
                <id>1</id>
                <name>Google Maps</name>
                <version>1.0</version>
            </app>
            <app>
                <id>2</id>
                <name>Chrome</name>
                <version>2.1</version>
            </app>
            <app>
                <id>3</id>
                <name>Google Play</name>
                <version>2.3</version>
            </app>
        </apps>
    
  ����Ľ���������
    


    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while(eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                    }
                    break;

                    case XmlPullParser.END_TAG: {
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is "+id);
                            Log.d("MainActivity","name is "+name);
                            Log.d("MainActivity","version is "+version);
                        }

                    }
                    break;

                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }   

  ����ʡȥ��һЩ�����ļ�⣬���ƿռ�����õȣ�������XmlPullParser.END_DOCUMENT��Ϊ�жϽ����¼��Ƿ���ɵı�־��


  ### 3.SAX����  
   SAX������Pull�������ƣ������¼������ġ��������ʽ�������������ȡͬ���������ľͽ������ġ�   

   Ҫ��SAX���������Ǿ���Ҫ�õ��ӿ�ContentHandle��һ������£����ǿ��Լ̳�ϵͳ�Դ���DefaultDocument��������д���е����������

   �������ǽ�������2�м�Pull�����е����ݣ���дDefaultHandle�ࣺ

    public class MyHandle extends DefaultHandler {

    private final String TAG = "MainActivity";
    private String nodeName;

    private StringBuilder id;

    private StringBuilder name;

    private StringBuilder version;

    @Override
    public void startDocument() throws SAXException{
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException{
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch,int start,int length)throws SAXException{
        if("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if("version".equals(nodeName)){
            version.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri,String localName,String qName)throws SAXException{
        if("app".equals(localName)){
            Log.d(TAG,"id is "+ id.toString().trim());
            Log.d(TAG,"name is "+name.toString().trim());
            Log.d(TAG,"version is "+version.toString().trim());

            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    @Override
    public void endDocument() throws  SAXException{
        super.endDocument();
     }
    }
 SAX���������ڴ�������ÿ������������һ���ض���ʱ�򱻵��ã�startDocument����
 ���ڿ�ʼ��������ʱִ�У�startElement�������ڿ�ʼ����һ���ڵ�ʱִ�У�characters�Ǿ�������ڵ�Ĺ��̣�endElement��������һ���ڵ�������ʱִ�У�endDocument����������������������ɺ�ִ�С�

 �ܵ���˵��SAX������ģ��ȽϹ̶�������Ҳ�Ƚ�������



