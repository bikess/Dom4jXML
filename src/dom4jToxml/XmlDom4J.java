package dom4jToxml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.spi.XmlWriter;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

// 本程序是利用dom4j创建，修改，并解析xml文档
/*
 * 创建的xml文档：
 * <?xml version="1.0" encoding="UTF-8"?> 
	<catalog> 
	<!--An XML Catalog--> 
	<?target instruction?>
  	<journal title="XML Zone" 
                  publisher="IBM developerWorks"> 
	<article level="Intermediate" date="December-2001">
 	<title>Java configuration with XML Schema</title> 
 	<author> 
     <firstname>Marcello</firstname> 
     <lastname>Vitaletti</lastname> 
 	</author>
  	</article>
  	</journal> 
	</catalog>
 */
public class XmlDom4J {

	/**
	 * @param args
	 */
	public void createDocumentXml(){
//		使用 DocumentHelper 类创建一个文档实例。 DocumentHelper 是生成 XML 文档节点的 dom4j API 工厂类
		Document document = DocumentHelper.createDocument();
//		使用 addElement() 方法创建根元素 catalog 。 addElement() 用于向 XML 文档中增加元素。
		Element catalogElement = document.addElement("catalog");
//		在 catalog 元素中使用 addComment() 方法添加注释“An XML catalog”。
		catalogElement.addComment("An XML catalog");
//		在 catalog 元素中使用 addProcessingInstruction() 方法增加一个处理指令。
		catalogElement.addProcessingInstruction("target", "instruction");
//		在 catalog 元素中使用 addElement() 方法增加 journal 元素。
		Element journalElement = catalogElement.addElement("journal");
//		使用 addAttribute() 方法向 journal 元素添加 title 和 publisher 属性。
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");
//		向 journal 元素中添加 article元素。
		Element articleElement = journalElement.addElement("article");
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");
//		向article中添加title元素
		Element titleElement = articleElement.addElement("title");
		titleElement.setText("Java configuration with XML Schema");
//		向article中添加author元素
		Element authorElement = articleElement.addElement("author");
//		向author元素中添加firstname元素并设置文本值
		Element firstnameElement = authorElement.addElement("firstname");
		firstnameElement.setText("Marcello");
//		向author元素中添加firstname元素并设置文本值
		Element lastnameElement = authorElement.addElement("lastname");
		lastnameElement.setText("Vitaletti");
//		可以使用 addDocType() 方法添加文档类型说明。
//		document.addDocType("catalog", null,"file://E:/eclipse4.2ws/catalog.xml");
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File("E:/eclipse4.2ws/catalog.xml")));
			xmlWriter.write(document);
			xmlWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void notifyDocumentXml(){
		try {
//			使用SAXReader解析XML文档catalog.xml
			SAXReader saxReader = new SAXReader();
			File inputXml = new File("E:/eclipse4.2ws/catalog.xml");
			Document document = saxReader.read(inputXml);
//			使用 XPath 表达式从 article 元素中获得 level节点列表。如果 level 属性值是“Intermediate”则改为“Introductory”
//		//返回符合表达式的节点LIST,//article表示从任意位置的节点上选择名称为 article 的节点
			List list = document.selectNodes("//article/@level");
			Iterator iter = list.iterator();
			while(iter.hasNext()){
				Attribute attribute = (Attribute)iter.next();
				if(attribute.getValue().equals("Intermediate"));
				{
					attribute.setValue("Introductory");
				}
			}
//			从article元素中活动data节点列表，对date的属性值进行修改
			list = document.selectNodes("//article/@date");
			iter = list.iterator();
			while(iter.hasNext()){
				Attribute attribute = (Attribute)iter.next();
				if(attribute.getValue().equals("December-2001")){
					attribute.setValue("October-2002");
				}
			}
//			获取 article 元素列表，从 article 元素中的 title 元素得到一个迭代器，并修改 title 元素的文本。
			list = document.selectNodes("//article");
			iter = list.iterator();
			while(iter.hasNext()){
				Element element = (Element)iter.next();
				Iterator iterator = element.elementIterator("title");
				while(iterator.hasNext()){
					Element titleElement = (Element)iterator.next();
					if(titleElement.getText().equals("Java configuration with XML Schema")){
						titleElement.setText("Create flexible and extensible XML schema");
					}
				}
			}
//			这表示选择article元素下面的子元素author
			list = document.selectNodes("//article/author");
			iter = list.iterator();
			while(iter.hasNext()){
				Element element = (Element)iter.next();
				Iterator iterator = element.elementIterator("firstname");
				while(iterator.hasNext()){
					Element firstElement = (Element)iterator.next();
					if(firstElement.getText().equals("Marcello")){
						firstElement.setText("Ayesha");
					}
				}
			}
			XMLWriter writer = new XMLWriter(new FileWriter(new File("E:/eclipse4.2ws/catalogmodify.xml")));
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XmlDom4J xmlDom4J = new XmlDom4J();
	    xmlDom4J.createDocumentXml();
			
		XmlDom4J.notifyDocumentXml();
	}

}
