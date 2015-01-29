package me.pedrazas.dropangular;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import me.pedrazas.dropangular.om.Ping;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PingResourceTest {
	private static final PingDAO dao = mock(PingDAO.class);
	private final PingResource resource = mock(PingResource.class);
	private static final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
	final Response mockResponse = mock(Response.class);
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListPings() {
		List<Ping> expected = dao.findAll();
		List<Ping> actual = resource.listPings();
		assertThat(expected, equalTo(actual));
	}
	
	@Test
	public void testCreatePing(){		
		when(mockResponse.getStatus()).thenReturn(201);
		when(resource.createPing(mockRequest)).thenReturn(mockResponse);		
		Response actual = resource.createPing(mockRequest);
		Response expected = Response.created(UriBuilder.fromPath("/api/ping").build()).build();
		assertThat(actual.getStatus(), equalTo(expected.getStatus()));
	}

}
