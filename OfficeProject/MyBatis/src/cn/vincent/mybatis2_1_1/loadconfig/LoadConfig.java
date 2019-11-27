package cn.vincent.mybatis2_1_1.loadconfig;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class LoadConfig {
    public static void main(String[] args)
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // 开启验证
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(false);
        documentBuilderFactory.setCoalescing(false);
        documentBuilderFactory.setExpandEntityReferences(true);
        // 创建DocumentBuilder
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        // 设置异常处理对象
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("error :" + exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("fatalError :" + exception.getMessage());
            }

            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("WARN :" + exception.getMessage());
            }
        });
        // 将文档加载到一个Document 对象中
        Document doc = builder.parse("MyBatis/src/cn/vincent/mybatis2_1_1/loadconfig/inventory.xml");

        // 创建XPathFactory
        XPathFactory factory = XPathFactory.newInstance();
        // 创建XPath 对象
        XPath xpath = factory.newXPath();
        // 编译XPath 表达式
        XPathExpression expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
        // 通过XPath 表达式得到结采，第一个参数指定了XPath 表达式进行查询的上下文节点，也就是在指定
        // 节点下查找符合XPath 的节点。本例中的上下文节点是整个文档；第二个参数指定了XPath 表达式
        // 的返回类型。
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        System.out.println("查询作者为Neal Stephenson 的图书的标题：");
        NodeList nodes = (NodeList) result; // 强制类型转换
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("查询1997 年之后的图书的标题");
        nodes = (NodeList) xpath.evaluate("//book[@year>1997]/title/text()", doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("查询1997 年之后的图书的属性和标题:");
        nodes = (NodeList) xpath.evaluate("//book[@year>1997]/@*|//book [@year>1997]/title/text()", doc,
                XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
    }
}
// 运行报：error :必须声明元素类型 "***"
// xml的头约束：dtd网址的问题
// <!DOCTYPE inventory PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
