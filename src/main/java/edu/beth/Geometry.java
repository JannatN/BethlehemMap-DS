package edu.beth;

import java.util.ArrayList;
import java.util.List;

public class Geometry {
	private String type = "LineString";
	private List<List<Float>> coordinates = new ArrayList();

	public void addCoordinate(float lon, float lat) {
		List<Float> innerListCoordinates = new ArrayList<Float>();
		innerListCoordinates.add(lon);
		innerListCoordinates.add(lat);
		coordinates.add(innerListCoordinates);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<List<Float>> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<List<Float>> coordinates) {
		this.coordinates = coordinates;
	}

}
