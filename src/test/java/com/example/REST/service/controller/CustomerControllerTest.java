package com.example.REST.service.controller;

import com.example.REST.service.expection.CustomerNotFoundException;
import com.example.REST.service.model.Customer;
import com.example.REST.service.model.assembler.CustomerModelAssembler;
import com.example.REST.service.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;


    @Test
    void httpGet_returnsGivenCustomer2() throws Exception {
        var mockRepository = mock(CustomerRepository.class);
        when(mockRepository.findById(anyLong())).thenReturn(Optional.empty());

        CustomerModelAssembler assembler = new CustomerModelAssembler();
        var toTest = new CustomerController(mockRepository,assembler);

        var exception = catchThrowable(() ->toTest.one(anyLong()));
        System.out.println(exception.fillInStackTrace());
         assertThat(exception)
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Couldn't find customer");
    }


    @Test
    void httpPut_addNewCustomer_shouldReturn201() throws Exception {
        String url = "http://localhost:8080/customers";
        Customer anCustomer = new Customer();
        anCustomer.setFirstname("firstname");
        anCustomer.setLastname("lastname");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anCustomer);
        mockMvc.perform(post(URI.create(url)).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());
    }





}