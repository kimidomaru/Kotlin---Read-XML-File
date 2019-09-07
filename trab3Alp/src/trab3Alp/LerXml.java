package trab3Alp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LerXml {
	public static void main(String argv[]) {

	    try {

			File fXmlFile = new File("teste.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
	
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nodeList = doc.getElementsByTagName("datafield");
					
			System.out.println("----------------------------");
			int contador=0;
			for (int i = 0; i < nodeList.getLength(); i++) {
	
				Node node = nodeList.item(i);
						
				//System.out.println("\nCurrent Element :" + node.getNodeName());
				//System.out.println(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					if(eElement.hasAttribute("tag") && eElement.getAttribute("tag").equals("650")) {
						System.out.println("Música!");
						Element e2 = (Element)eElement.getElementsByTagName("subfield").item(0);
						if(e2.hasAttribute("code") && e2.getAttribute("code").equals("a")) {
							System.out.println("Teste: "+e2.getTextContent());
							contador++;
						}
					}
					
					//System.out.println("Teste: " + eElement.getElementsByTagName("subfield").item(0).getTextContent());
					/*
					System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
					*/
					
					//System.out.println("Teste: "+ getValue("subfield",eElement));
	
				}
		}
		System.out.println("Musicas encontradas: "+contador);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	  }
	/*
	 static String getValue(String tag, Element element) {
		 NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		 Node node = (Node) nodes.item(0);
		 return node.getNodeValue();
	 }
	 */
}
