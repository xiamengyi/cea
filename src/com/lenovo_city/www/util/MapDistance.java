package com.lenovo_city.www.util;

public class MapDistance {

	public static double distanceFromCoordinate(double latitude1,double longitude1,
			double latitude2,double longitude2){
		  	final double PI = 3.1415926535898;
		  	final double EARTH_RADIUS= 6378.137;
			double radLat1 = latitude1 * PI / 180.0;
			double radLat2 = latitude2 * PI / 180.0;
			double a = radLat1 - radLat2;
			double b = (longitude1-longitude2) * PI / 180.0;
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
					Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;

			return s*1000;
		}
}
