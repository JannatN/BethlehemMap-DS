package edu.bu.datastructures.springboot;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.geotools.referencing.GeodeticCalculator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.beth.Dijkestra;
import edu.beth.Edge;
import edu.beth.Graph;
import edu.beth.Json;
import edu.beth.Node;
import edu.beth.Path;

@RestController
public class Controller {

	@GetMapping("/shortestpath_lat_lon")
	@CrossOrigin(origins = "*")
	public String[] getShortestPathFromLatLon(@RequestParam double srcLon, @RequestParam double srcLat,
			@RequestParam double dstLon, @RequestParam double dstLat)
			throws JsonProcessingException, ParserConfigurationException, SAXException, IOException {
		String pathname = "C:\\Users\\ncc\\Downloads\\map.osm";
		Graph g = createGraph(pathname);
		System.out.println("Graph Node Count: " + g.getNumberOfNodes());

		long sourceNodeId = getNearestNodeId(g, srcLon, srcLat);
		long destinationNodeId = getNearestNodeId(g, dstLon, dstLat);

		String srcDstJson = String.format("[%f,%f,%f,%f]", g.getNodes().get(sourceNodeId).getLat(),
				g.getNodes().get(sourceNodeId).getLon(), g.getNodes().get(destinationNodeId).getLat(),
				g.getNodes().get(destinationNodeId).getLon());

		System.out.println(
				"sourceNodeId: " + sourceNodeId + " EdgeCount: " + g.getNodes().get(sourceNodeId).getEdges().size());
		for (Edge e : g.getNodes().get(sourceNodeId).getEdges()) {
			System.out.println("WayType: " + e.getWayType());
		}
		System.out.println("destinationNodeId: " + destinationNodeId + " EdgeCount: "
				+ g.getNodes().get(destinationNodeId).getEdges().size());
		for (Edge e : g.getNodes().get(destinationNodeId).getEdges()) {
			System.out.println("WayType: " + e.getWayType());
		}

		System.out.println("srcDstLatLng: " + srcDstJson);

		Json j = new Json();
		Dijkestra d = new Dijkestra();

		Path path = d.shortestPath(g, sourceNodeId, destinationNodeId);

		if (path == null) {
			System.out.println("No path found! Path is null.");
			return new String[] { srcDstJson, "{}" };
		}

		String json1 = j.toJson(g, path);
		return new String[] { srcDstJson, json1 };

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
			String wayType = getWayType(elementWay);

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
				g.addEdge(source, destination, timeInSec, distance, true, wayType);

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
				case "tertiary":
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

	private static String getWayType(Element elementWay) {
		NodeList tags = elementWay.getElementsByTagName("tag");
		for (int j = 0; j < tags.getLength(); j++) {
			Element wayTagItem = (Element) tags.item(j);
			if (wayTagItem.getAttribute("k").equals("highway")) {
				String type = wayTagItem.getAttribute("v").toLowerCase().trim();
				switch (type) {
				case "motorway":
				case "primary":
				case "secondary":
				case "tertiary":
				case "residential":
				case "service":
				case "track":
					return type;
				default:
					System.out.println("Ignoring wayType: " + type);
					return "other";
				}
			}

		}

		return "other";
	}

	private static double calculateDistance(double srcLon, double srcLat, double dstLon, double dstLat) {
		GeodeticCalculator calculator = new GeodeticCalculator();
		calculator.setStartingGeographicPoint(srcLon, srcLat);
		calculator.setDestinationGeographicPoint(dstLon, dstLat);
		return (double) calculator.getOrthodromicDistance();

	}

	private static long getNearestNodeId(Graph g, double lon, double lat) {
		long nearestNodeId = -1;
		double shortestDistance = 0;

		Set<Long> nodeIds = g.getNodes().keySet();

		for (Long nid : nodeIds) {
			Node tempNode = g.getNodes().get(nid);

			if (tempNode.getEdges().size() == 0)
				continue;

			boolean hasGoodEdge = false;
			for (Edge edge : tempNode.getEdges()) {
				if (!edge.getWayType().equals("other")) {
					hasGoodEdge = true;
					break;
				}
			}

			if (hasGoodEdge == false)
				continue;

			double distance = calculateDistance(lon, lat, tempNode.getLon(), tempNode.getLat());

			if (nearestNodeId == -1 || distance < shortestDistance) {
				nearestNodeId = tempNode.getNodeId();
				shortestDistance = distance;
			}
		}

		System.out.println("shortestDistance: " + shortestDistance);
		return nearestNodeId;
	}
}
