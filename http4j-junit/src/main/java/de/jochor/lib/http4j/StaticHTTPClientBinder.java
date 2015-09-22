package de.jochor.lib.http4j;

import de.jochor.lib.http4j.apache.HTTPClientJUnit;

/**
*
* @author Jochen Hormes
* @start 2015-09-03
*
*/
public class StaticHTTPClientBinder {

	public static HTTPClient create(){
		HTTPClientJUnit httpClient = new HTTPClientJUnit();
		return httpClient;
	}
	
}
