package edu.beth;

import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
	static ObjectMapper mapper = new ObjectMapper();

	public String toJson(Graph graph, Path path) throws JsonProcessingException {
		Geometry geometry = new Geometry();
		for (Long nodeId : path.getPathNodes()) {
			Node node = graph.get(nodeId);
			float lat =  node.getLat();
			float lon =  node.getLon();
			geometry.addCoordinate(lon, lat);
		}
		path.setGeometry(geometry);
		path.addProperties("Time", "1000");
		path.addProperties("distance", "200");
		String result = mapper.writeValueAsString(path);
		return result;
	}

}
