package de.jochor.lib.http4j;

import de.jochor.lib.http4j.apache.HttpClientJUnit;

/**
*
* @author Jochen Hormes
* @start 2015-09-03
*
*/
public class StaticHttpClientBinder {

	public static HttpClient create(){
		HttpClientJUnit httpClient = new HttpClientJUnit();
		return httpClient;
	}
	
}
