package de.jochor.lib.http4j;

import org.junit.Assert;
import org.junit.Test;

import de.jochor.lib.http4j.model.ContentType;

/**
 * Test for the {@link UnknownContentTypeException}.
 *
 * <p>
 * <b>Started:</b> 2015-11-27
 * </p>
 *
 * @author Jochen Hormes
 *
 */
public class UnknownContentTypeExceptionTest {

	@Test
	public void testUnknownContentTypeException_ContentType() {
		UnknownContentTypeException e = new UnknownContentTypeException(ContentType.APPLICATION_JSON);
		Assert.assertNotNull(e);
		Assert.assertEquals("Unknown content type: " + ContentType.APPLICATION_JSON.name(), e.getMessage());
		Assert.assertNull(e.getOriginal());
	}

	@Test
	public void testUnknownContentTypeException_null() {
		UnknownContentTypeException e = new UnknownContentTypeException(null);
		Assert.assertNotNull(e);
		Assert.assertEquals("Unknown content type: null", e.getMessage());
		Assert.assertNull(e.getOriginal());
	}

	@Test
	public void testUnknownContentTypeException_originalException() {
		Exception original = new Exception();
		UnknownContentTypeException e = new UnknownContentTypeException(ContentType.APPLICATION_JSON, original);
		Assert.assertNotNull(e);
		Assert.assertEquals("Unknown content type: " + ContentType.APPLICATION_JSON.name(), e.getMessage());
		Assert.assertSame(original, e.getOriginal());
	}

}
