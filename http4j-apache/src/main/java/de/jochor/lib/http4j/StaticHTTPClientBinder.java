package de.jochor.lib.http4j;

import de.jochor.lib.http4j.apache.HTTPClientApacheCommens;

/**
*
* @author Jochen Hormes
* @start 2015-09-03
*
*/
public class StaticHTTPClientBinder {

	public static HTTPClient create(){
		HTTPClientApacheCommens httpClient = new HTTPClientApacheCommens();
		return httpClient;
	}
	
}
