package com.nitsoft.ecommerce.api.rest.information;

import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.api.rest.AbstractRestHandler;
import com.nitsoft.ecommerce.domain.IDInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * RESTful API endpoints using Spring MVC
 */
@RestController
@RequestMapping(value = "/api/idinformation")
@Api(value = "idinformation", description = " API")
public class IDInformationController extends AbstractRestHandler {

    @Autowired
    private IDInformationService service;
    

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "id information.", notes = "")
    public @ResponseBody
    String getInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) {  
        // Create response data
        StatusResponse statusResponse;
        if (service.getPackets(id) != null) {
            IDInformationResponse informationResponse = new IDInformationResponse();
            informationResponse.setArbID(id);
            informationResponse.setPackets(service.getPackets(id));
            // Set status response
            statusResponse = utilsResponse.successResponse(informationResponse, "OK");
        }
        else
             statusResponse = utilsResponse.errorResponse(404,null, "Request infomation not found");
        
        return writeObjectToJson(statusResponse);
    }
}
