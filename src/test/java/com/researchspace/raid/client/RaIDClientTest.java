package com.researchspace.raid.client;


import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDIdentifier;
import com.researchspace.raid.model.RaIDServicePoint;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

class RaIDClientTest {

  private static final String API_KEY_TOKEN = "API_KEY_TOKEN";
  private static final Integer SERVICE_POINT_ID = 20000030;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private RaIDClientImpl raidClient;
  private MockRestServiceServer mockServer;

  @BeforeEach
  public void init() {
    raidClient = new RaIDClientImpl();
    mockServer = MockRestServiceServer.createServer(raidClient.getRestTemplate());
  }

  @Test
  public void getServicePointList() throws IOException {
    String jsonServicePointList = IOUtils.resourceToString("/json/service-point-list.json",
        Charset.defaultCharset());

    String jsonServicePointRSpace = IOUtils.resourceToString("/json/service-point-rspace.json",
        Charset.defaultCharset());
    RaIDServicePoint rspaceServicePoint = objectMapper.readValue(jsonServicePointRSpace,
        RaIDServicePoint.class);

    mockServer.expect(requestTo(endsWith("/service-point/")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(jsonServicePointList, MediaType.APPLICATION_JSON));

    Set<RaIDServicePoint> servicePointList = raidClient.getServicePointList(API_KEY_TOKEN);
    assertNotNull(servicePointList);
    assertEquals(36, servicePointList.size());
    assertTrue(servicePointList.contains(rspaceServicePoint));
  }

  @Test
  public void getServicePoint() throws IOException {
    String jsonServicePointRSpace = IOUtils.resourceToString("/json/service-point-rspace.json",
        Charset.defaultCharset());
    RaIDServicePoint rspaceServicePoint = objectMapper.readValue(jsonServicePointRSpace,
        RaIDServicePoint.class);

    mockServer.expect(requestTo(endsWith("/service-point/" + SERVICE_POINT_ID)))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(jsonServicePointRSpace, MediaType.APPLICATION_JSON));

    RaIDServicePoint servicePointUnique = raidClient.getServicePoint(API_KEY_TOKEN,
        SERVICE_POINT_ID);
    assertNotNull(servicePointUnique);
    assertEquals(rspaceServicePoint, servicePointUnique);
  }

  @Test
  public void getRaidList() throws IOException {
    String jsonRaidList = IOUtils.resourceToString("/json/raid-list.json",
        Charset.defaultCharset());

    String jsonRaidRSpace1 = IOUtils.resourceToString("/json/raid-test-1.json",
        Charset.defaultCharset());
    RaID rspaceRaid1 = objectMapper.readValue(jsonRaidRSpace1, RaID.class);

    String jsonRaidRSpace2 = IOUtils.resourceToString("/json/raid-test-2.json",
        Charset.defaultCharset());
    RaID rspaceRaid2 = objectMapper.readValue(jsonRaidRSpace2, RaID.class);

    mockServer.expect(requestTo(endsWith("/raid/")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(jsonRaidList, MediaType.APPLICATION_JSON));

    Set<RaID> raidList = raidClient.getRaidList(API_KEY_TOKEN);
    assertNotNull(raidList);
    assertEquals(2, raidList.size());
    assertTrue(raidList.contains(rspaceRaid1));
    assertTrue(raidList.contains(rspaceRaid2));
  }

  @Test
  public void getRaid() throws IOException {
    String jsonRaidRSpace = IOUtils.resourceToString("/json/raid-test-1.json",
        Charset.defaultCharset());
    RaID rspaceRaid = objectMapper.readValue(jsonRaidRSpace, RaID.class);
    RaIDIdentifier rspaceRaidIdentifier = rspaceRaid.getIdentifier();

    mockServer.expect(requestTo(endsWith("/raid/"
            + rspaceRaidIdentifier.getPrefix() +"/"
            + rspaceRaidIdentifier.getSuffix())))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(jsonRaidRSpace, MediaType.APPLICATION_JSON));

    RaID raidUnique = raidClient.getRaid(API_KEY_TOKEN, rspaceRaidIdentifier.getPrefix(),
        rspaceRaidIdentifier.getSuffix());
    assertNotNull(raidUnique);
    assertEquals(rspaceRaid, raidUnique);
  }

}