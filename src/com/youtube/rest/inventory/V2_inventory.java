package com.youtube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import com.youtube.dao.*;

@Path("/v2/inventory")
public class V2_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand)
			throws Exception {
		String string = null;
		JSONArray jsonArray = new JSONArray();

		try {
			if (brand == null)
				return Response
						.status(400)
						.entity("Error: Please specify the brand for this search")
						.build();

			SchemaRESTfulMySQL dao = new SchemaRESTfulMySQL();
			jsonArray = dao.queryReturnBrandParts(brand);
			string = jsonArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(string).build();
	}

	@GET
	@Path("/{brand}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand)
			throws Exception {
		String string = null;
		JSONArray jsonArray = new JSONArray();

		try {
			if (brand == null)
				return Response
						.status(400)
						.entity("Error: Please specify the brand for this search")
						.build();

			SchemaRESTfulMySQL dao = new SchemaRESTfulMySQL();
			jsonArray = dao.queryReturnBrandParts(brand);
			string = jsonArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(string).build();
	}

	@GET
	@Path("/{brand}/{item_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem(
			@PathParam("brand") String brand,
			@PathParam("item_number") int item_number) 
					throws Exception {
		String string = null;
		JSONArray jsonArray = new JSONArray();

		try {
			if (brand == null)
				return Response
						.status(400)
						.entity("Error: Please specify the brand for this search")
						.build();

			SchemaRESTfulMySQL dao = new SchemaRESTfulMySQL();
			jsonArray = dao.queryReturnBrandItemNumber(brand, item_number);
			string = jsonArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		return Response.ok(string).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {
		
		String returnString = null;
		//JSONArray jsonArray = new JSONArray(); //not needed
		SchemaRESTfulMySQL dao = new SchemaRESTfulMySQL();
		
		try {
			System.out.println("incomingData: " + incomingData);
			
			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http request
			 * and data bind it to a Java Class.
			 */
			
			ObjectMapper mapper = new ObjectMapper();  
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int http_code = dao.insertIntoPC_PARTS(itemEntry.PC_PARTS_TITLE, 
													itemEntry.PC_PARTS_CODE, 
													itemEntry.PC_PARTS_MAKER, 
													itemEntry.PC_PARTS_AVAIL, 
													itemEntry.PC_PARTS_DESC );
			
			if( http_code == 200 ) {
				//returnString = jsonArray.toString();
				returnString = "Item inserted";
			} else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * returnErrorOnBrand() throws Exception { return Response .status(400)
	 * .entity("Error: Please specify the brand for this search") .build(); }
	 */

}

class ItemEntry {
	
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
	
}
