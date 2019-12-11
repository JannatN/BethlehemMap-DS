//package edu.beth;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Hashtable;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.geotools.referencing.GeodeticCalculator;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
package edu.beth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.geotools.referencing.GeodeticCalculator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Location;

public class MainAdjacencyGraph {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String pathname = "C:\\Users\\ncc\\Downloads\\map.osm";
		Graph graph = createGraph(pathname);
		Dijkestra d = new Dijkestra();
		Json j = new Json();
		Path path = d.shortestPath(graph, 5307143378L, 925169405);
		System.out.println("number of nodes " + graph.getNumberOfNodes());
		System.out.println("number of edges " + graph.getNumberOfEdges());
		String json1 = j.json(graph, path);
		System.out.println(json1);

	}

	public static Graph createGraph(String pathname) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File(pathname);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		Graph g = new Graph();
		NodeList nList = doc.getElementsByTagName("node");
		int length = nList.getLength();
		for (int i = 0; i < length; i++) {
			Element elementNode = (Element) nList.item(i);
			g.addNode(Long.parseLong(elementNode.getAttribute("id")), null,
					Float.parseFloat(elementNode.getAttribute("lon")),
					Float.parseFloat(elementNode.getAttribute("lat")));

		}
		NodeList ways = doc.getElementsByTagName("way");
		for (int i = 0; i < ways.getLength(); i++) {
			Element elementWay = (Element) ways.item(i);
			double speed = speedKMH(elementWay) * 1000 / 3600.0;
			NodeList wayNodes = elementWay.getElementsByTagName("nd");
			for (int j = 1; j < wayNodes.getLength(); j++) {
				Element prevWayNodeElement = (Element) wayNodes.item(j - 1);
				Element wayNodeElement = (Element) wayNodes.item(j);
				Long source = Long.parseLong(wayNodeElement.getAttribute("ref"));
				Long destination = Long.parseLong(prevWayNodeElement.getAttribute("ref"));
				double distance = calculateDistance(g.getNodes().get(source).getLon(),
						g.getNodes().get(source).getLat(), g.getNodes().get(destination).getLon(),
						g.getNodes().get(destination).getLat());
				double timeInSec = (distance / speed);
				g.addEdge(source, destination, timeInSec, distance, true);

			}

		}
		return g;
	}

	private static double speedKMH(Element elementWay) {
		NodeList tags = elementWay.getElementsByTagName("tag");
		for (int j = 0; j < tags.getLength(); j++) {
			Element wayTagItem = (Element) tags.item(j);
			if (wayTagItem.getAttribute("k").equals("highway")) {
				switch (wayTagItem.getAttribute("v").toLowerCase().trim()) {
				case "motorway":
					return 120;
				case "primary":
					return 80;
				case "secondary":
					return 50;
				case "tertiary ":
					return 40;
				case "residential":
					return 20;
				default:
					return 30;
				}
			}
		}
		return 30;
	}

	private static double calculateDistance(double srcLon, double srcLat, double dstLon, double dstLat) {
		GeodeticCalculator calculator = new GeodeticCalculator();
		calculator.setStartingGeographicPoint(srcLon, srcLat);
		calculator.setDestinationGeographicPoint(dstLon, dstLat);
		return (double) calculator.getOrthodromicDistance();
	}

}
