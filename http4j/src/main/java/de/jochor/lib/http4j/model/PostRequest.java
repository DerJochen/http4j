package de.jochor.lib.http4j.model;

import java.net.URI;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 * <p>
 * <b>Started:</b> 2015-08-19
 * </p>
 * @author Jochen Hormes
 *
 */
@Getter
@Setter
@RequiredArgsConstructor
public class PostRequest {

	private final URI uri;

	private int expectedStatus = 200;

	private String body;

}
