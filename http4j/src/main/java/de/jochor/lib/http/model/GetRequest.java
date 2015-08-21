package de.jochor.lib.http.model;

import java.net.URI;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jochen Hormes
 * @start 2015-08-19
 *
 */
@Getter
@Setter
@RequiredArgsConstructor
public class GetRequest {

	private final URI uri;
	
	private int expectedStatus = 200;

}
