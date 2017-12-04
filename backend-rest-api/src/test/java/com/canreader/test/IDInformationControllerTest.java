package com.canreader.test;

/**
 * Uses JsonPath: http://goo.gl/nwXpb, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitsoft.ecommerce.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class IDInformationControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost:8080/api/idinformation";


    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

//    @Test
//    public void shouldHaveEmptyDB() throws Exception {
//        mvc.perform(get("/api/sniffwrites")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }

    @Test
    public void testRetrieve() throws Exception {
        
        //RETRIEVE API
        mvc.perform(get(RESOURCE_LOCATION_PATTERN)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); 
        
        // compare detail response
        // TODO
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

}
