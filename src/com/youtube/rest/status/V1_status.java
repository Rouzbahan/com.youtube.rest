package com.youtube.rest.status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/status") // or @Path("/v1/status/*")
public class V1_status {
	
	private static final String version = "00.01.00";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String returnTitle() {
		return "<p>Java Web Service</p>";
	}
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String returnVersion() {
		return "<p>Version: " + version + "</p>";
	}

}
