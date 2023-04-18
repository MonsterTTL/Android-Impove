# Android开发笔记：XML数据的解析

## 何为XML数据？
    
XML 指可扩展标记语言（eXtensible Markup Language）。   
可扩展标记语言（英语：Extensible Markup Language，简称：XML）是一种标记语言，是从标准通用标记语言（SGML）中简化修改出来的。  **其最主要的功能就是为了方便数据的传输与交换。**     


***在Android开发中，我们有时候也需要从服务器上获取xml数据并加以解析***


## 如何解析XML数据？

### 1.Pull解析   
这里我们根据谷歌官方的开发者文档提供的方法，也是其推荐的方法来解析xml数据。

- 1.分析Feed中感兴趣的标签内容  
    例如：

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

就是一份xml数据，其中两个对应<>中的内容即为一个标签中的内容，比如说entry标签中嵌套的id标签为：

        <id>http://stackoverflow.com/q/9439999</id>

其内容即为http://stackoverflow.com/q/9439999


- 2.选择解析器   
    为了解析xml数据，我们需要选择一些解析器来帮助我们分析数据。

    官方文档中提到：

        XmlPullParser，这是一种在 Android 上解析 XML 的高效且可维护的方式。以前，Android 有此接口的两个实现：

        KXmlParser（通过 XmlPullParserFactory.newPullParser()）。
        ExpatPullParser（通过 Xml.newPullParser()）。
        任一选择都可以。此部分中的示例使用 ExpatPullParser（通过 Xml.newPullParser()）。


- 3.实例化解析器   

    前文提到过，有具体的两种方式实例化解析器，分别是 用工厂类生成 或者 直接用实例生成。比如：

        工厂类生成：

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();        

        实例生成：

            XmlPullParser xmlPullParser = Xml.newPullParser();


    两者任选其一即可。

 - 4.然后我们可以具体配置一下解析器

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

    - 其中，parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);这一行是用来禁用名称空间的，当然setFeature方法还可以用来配置解析器的其他一些参数，可以查看官方的文档。   
    - parser.setInput(in, null);是设置了具体的数据流和编码格式，如果设置为null则使用系统默认的编码。此处还可以只设置一个字节流(Reader),比如：

            xmlPullParser.setInput(new StringReader(xmlData));

    - parser.nextTag();调用 nextTag() 开始解析过程 ,官方的文档里是这样描述的：  
    > Call next() and return event if it is START_TAG or END_TAG otherwise throw an exception. It will skip whitespace TEXT before actual tag if any.  

    就是说，如果是START_TAG或END_TAG，则调用next()并返回事件，否则抛出异常。它将跳过实际标记之前的空白TEXT(如果有的话)。本质代码：

             int eventType = next();
            if(eventType == TEXT &&  isWhitespace()) {   // skip whitespace
                eventType = next();
            }
            if (eventType != START_TAG &&  eventType != END_TAG) {
                throw new XmlPullParserException("expected start or end 
                tag", this, null);
            }
            return eventType;
                
    - next()方法则是用来获取下一个解析事件的。

- 5.创建具体的方法来解析数据

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

    - 这里一开始的require方法是用来测试条件的，它接受三个参数，分别是预期的事件类型，名称空间，名称。
    它将测试当前事件是否属于给定的类型，以及名称空间和名称是否匹配。Null将匹配任何名称空间和任何名称。如果测试未通过，则抛出异常。异常文本指示解析器位置、预期事件和不满足需求的当前事件。      

    - 这里的next事件可能返回的两个值，XmlPullParser.START_TAG 和 XmlPullParser.END_TAG 分别对应的是开始解析一个节点和完成一个节点的解析的标志。还有一个重要的标志是XmlPullParser.END_DOCUMENT,对应的是解析工作完成。

    - skip函数则是用来跳过不感兴趣的标签的。  

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

    接下来则会进入到entry标签中进行进一步的解析：

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

    这里官方示例写的比较复杂，但是模块化做的较好。这里创建了一个静态内部类Entry来辅助组织并返回数据。  首先开始读取后利用getName()方法来获取节点的名称，对于每个具体的节点，也分别写了不同的方法来读取。  

    - 1. 我们首先来看最基本的读取方法readText方法，这实际上是对解析器提供的getText方法的封装，当解析的下一个事件为TEXT时，即利用getText方法将其内容返回出去。

    - 2. 接着其实其他的解析方法都大同小异，但都要根据标签内部具体的内容来设计具体的解析逻辑，比如实例中比较特殊的readLink方法，其中有一个getAttributeValue方法，是根据名称空间和具体的属性名称来获取属性值的，
    比如上面的代码： 

          String relType = parser.getAttributeValue(null, "rel");

    就是设置无名称空间，获取了Link标签里rel的具体的值。
    然后再根据rel的值来决定是否解析href的值。   

 ### 2.简单的Pull解析
  上面的Pull解析未免太过繁琐，在Android第一行代码中也有较为简单的解析：  
   要解析的数据：

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
    
  具体的解析方法：
    


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

  这里省去了一些繁琐的检测，名称空间的设置等，并且用XmlPullParser.END_DOCUMENT作为判断解析事件是否完成的标志。


  ### 3.SAX解析  
   SAX解析与Pull解析类似，是由事件驱动的。其采用流式解析，解析与读取同步，读到哪就解析到哪。   

   要用SAX解析，我们就需要用到接口ContentHandle，一般情况下，我们可以继承系统自带的DefaultDocument，并且重写其中的五个方法。

   这里我们解析上面2中简单Pull解析中的数据，重写DefaultHandle类：

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
 SAX解析类似于触发器，每个方法都会在一个特定的时候被调用，startDocument方法
 会在开始解析任务时执行，startElement方法会在开始解析一个节点时执行，characters是具体解析节点的过程，endElement方法会在一个节点解析完成时执行，endDocument方法会在整个解析过程完成后执行。

 总的来说，SAX解析的模版比较固定，语义也比较清晰。



