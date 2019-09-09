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

			File fXmlFile = new File("Music.2016.combined-lite.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			MapaMusicas mapa = new MapaMusicas();
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
	
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nodeList = doc.getElementsByTagName("datafield");
					
			System.out.println("----------------------------");
			
			int contador=0;
			double tempoIni = System.currentTimeMillis();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
			
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					if(eElement.hasAttribute("tag") && eElement.getAttribute("tag").equals("650")) {
						Element e2 = (Element)eElement.getElementsByTagName("subfield").item(0);
						if(e2.hasAttribute("code") && e2.getAttribute("code").equals("a")) {
							String nomeGenero = e2.getTextContent();
							if(mapa.existInMap(nomeGenero)) {
								mapa.countMap(nomeGenero);
							}
							else {
								mapa.addToMap(nomeGenero, 1);
							}
							contador++;
						}
					}
				}
				
			}
			mapa.showMap();
			System.out.println("Musicas encontradas: "+contador);
			double tempoFinal = System.currentTimeMillis();
			double tempoTot = tempoFinal - tempoIni;
			System.out.println("Tempo de Execução(Ms): "+ tempoTot);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	  }
	
}
