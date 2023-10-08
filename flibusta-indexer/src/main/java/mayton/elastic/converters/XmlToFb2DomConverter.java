package mayton.elastic.converters;

import mayton.elastic.Fb2Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

public class XmlToFb2DomConverter implements Function<InputStream, Fb2Document> {

    static Logger logger = LoggerFactory.getLogger(XmlToFb2DomConverter.class);

    @Override
    public Fb2Document apply(InputStream inputStream) {
        Fb2Document document = new Fb2Document();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            //Evaluate XPath against Document itself
            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList nodes = (NodeList)xPath.evaluate(
                    "/FictionBook/description/title-info/author/first-name",
                    doc,
                    XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); ++i) {
                Element e = (Element) nodes.item(i);
            }
        } catch (ParserConfigurationException e) {
            logger.error("", e);
        } catch (SAXException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } catch (XPathExpressionException e) {
            logger.error("", e);
        }
        return document;
    }

}
