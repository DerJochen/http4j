package de.jochor.lib.http4j.junit;

import lombok.Getter;
import lombok.Setter;
import de.jochor.lib.http4j.model.BaseRequest;

/**
 * Holder object that will contain the request after it has been processed.
 *
 * <p>
 * <b>Started:</b> 2015-08-21
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class RequestHolder {

	@Getter
	@Setter
	private BaseRequest request;

}
