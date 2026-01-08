package com.researchspace.raid.client;


import static java.net.URLEncoder.encode;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDIdentifier;
import com.researchspace.raid.model.RaIDRelatedObject;
import com.researchspace.raid.model.RaIDServicePoint;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

class RaIDClientTest {

  private static final String INSTANCE_BASE_URL = "https://base.raid.url";
  private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
  private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
  private static final Integer SERVICE_POINT_ID = 20000030;
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final String CLIENT_ID = "client Id";
  private static final String CLIENT_SECRET = "client Secret";
  private static final String AUTHORIZATION_CODE = "authorizationCode_dfvyi";
  private static final String REDIRECT_URI = "http://redirect.uri/callback";
  private static final String SERVER_ALIAS = "server Alias";

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

    List<RaIDServicePoint> servicePointList = raidClient.getServicePointList(INSTANCE_BASE_URL,
        ACCESS_TOKEN);
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

    RaIDServicePoint servicePointUnique = raidClient.getServicePoint(INSTANCE_BASE_URL,
        ACCESS_TOKEN,
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

    List<RaID> raidList = raidClient.getRaIDList(INSTANCE_BASE_URL, ACCESS_TOKEN);
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
            + rspaceRaidIdentifier.getPrefix() + "/"
            + rspaceRaidIdentifier.getSuffix())))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(jsonRaidRSpace, MediaType.APPLICATION_JSON));

    RaID raidUnique = raidClient.getRaID(INSTANCE_BASE_URL, ACCESS_TOKEN,
        rspaceRaidIdentifier.getPrefix(),
        rspaceRaidIdentifier.getSuffix());
    assertNotNull(raidUnique);
    assertEquals(rspaceRaid, raidUnique);
  }

  @Test
  public void updateRaidRelatedObject() throws IOException {
    String oldRaidRSpaceJson = IOUtils.resourceToString("/json/raid-test-1.json",
        Charset.defaultCharset());
    RaID oldRspaceRaid = objectMapper.readValue(oldRaidRSpaceJson, RaID.class);
    RaIDIdentifier rspaceRaidIdentifier = oldRspaceRaid.getIdentifier();

    RaIDRelatedObject newRaIDRelatedObject = objectMapper.readValue(
        IOUtils.resourceToString("/json/related-object-1.json", Charset.defaultCharset()),
        RaIDRelatedObject.class);

    String newRaidRSpaceJson = IOUtils.resourceToString("/json/raid-test-1-with-related-object.json",
        Charset.defaultCharset());
    RaID expectedNewRaid = objectMapper.readValue(newRaidRSpaceJson, RaID.class);

    mockServer.expect(requestTo(endsWith("/raid/"
            + rspaceRaidIdentifier.getPrefix() + "/"
            + rspaceRaidIdentifier.getSuffix())))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(oldRaidRSpaceJson, MediaType.APPLICATION_JSON));

    mockServer.expect(requestTo(endsWith("/raid/"
            + rspaceRaidIdentifier.getPrefix() + "/"
            + rspaceRaidIdentifier.getSuffix())))
        .andExpect(content().json(newRaidRSpaceJson))
        .andExpect(method(HttpMethod.PUT))
        .andRespond(withSuccess(newRaidRSpaceJson, MediaType.APPLICATION_JSON));

    // WHEN
    RaID actualNewRaid = raidClient.updateRaIDRelatedObject(INSTANCE_BASE_URL, ACCESS_TOKEN,
        rspaceRaidIdentifier.getPrefix(), rspaceRaidIdentifier.getSuffix(),
        newRaIDRelatedObject.getId());

    // THEN
    assertNotNull(actualNewRaid);
    assertEquals(expectedNewRaid, actualNewRaid);
  }

  @Test
  public void clearRaidRelatedObject() throws IOException {
    String oldRaidRSpaceJson = IOUtils.resourceToString("/json/raid-test-1-with-related-object.json",
        Charset.defaultCharset());
    RaID oldRspaceRaid = objectMapper.readValue(oldRaidRSpaceJson, RaID.class);
    RaIDIdentifier rspaceRaidIdentifier = oldRspaceRaid.getIdentifier();

    String newRaidRSpaceJson = IOUtils.resourceToString("/json/raid-test-1.json",
        Charset.defaultCharset());
    RaID expectedNewRaid = objectMapper.readValue(newRaidRSpaceJson, RaID.class);

    mockServer.expect(requestTo(endsWith("/raid/"
            + rspaceRaidIdentifier.getPrefix() + "/"
            + rspaceRaidIdentifier.getSuffix())))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(oldRaidRSpaceJson, MediaType.APPLICATION_JSON));

    mockServer.expect(requestTo(endsWith("/raid/"
            + rspaceRaidIdentifier.getPrefix() + "/"
            + rspaceRaidIdentifier.getSuffix())))
        .andExpect(content().json(newRaidRSpaceJson))
        .andExpect(method(HttpMethod.PUT))
        .andRespond(withSuccess(newRaidRSpaceJson, MediaType.APPLICATION_JSON));

    // WHEN
    RaID actualNewRaid = raidClient.clearRaIDRelatedObject(INSTANCE_BASE_URL, ACCESS_TOKEN,
        rspaceRaidIdentifier.getPrefix(), rspaceRaidIdentifier.getSuffix());

    // THEN
    assertNotNull(actualNewRaid);
    assertEquals(expectedNewRaid, actualNewRaid);
  }


  @Test
  public void testGetRedirectUriToConnect() throws URISyntaxException {
    String expectedResult = INSTANCE_BASE_URL + "/auth" +
        "?client_id=" + encode(CLIENT_ID, StandardCharsets.UTF_8) +
        "&redirect_uri=" + encode(REDIRECT_URI, StandardCharsets.UTF_8) +
        "&response_type=code&scope=openid&state=" + encode(SERVER_ALIAS, StandardCharsets.UTF_8);
    String actualResult = raidClient.getRedirectUriToConnect(INSTANCE_BASE_URL, CLIENT_ID,
        REDIRECT_URI, SERVER_ALIAS);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testGetAccessToken() throws URISyntaxException, IOException {
    String expectedJsonAccessToken = IOUtils.resourceToString("/json/access-token-response.json",
        Charset.defaultCharset());

    Map<String, String> expectedFormData = new HashMap<>();
    expectedFormData.put("grant_type", "authorization_code");
    expectedFormData.put("code", AUTHORIZATION_CODE);

    mockServer.expect(requestTo(endsWith("/token")))
        .andExpect(content().formDataContains(expectedFormData))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(expectedJsonAccessToken, MediaType.APPLICATION_JSON));

    String actualJsonAccessToken = raidClient.getAccessToken(INSTANCE_BASE_URL, CLIENT_ID,
        CLIENT_SECRET, AUTHORIZATION_CODE, REDIRECT_URI);

    assertEquals(expectedJsonAccessToken, actualJsonAccessToken);
  }

  @Test
  public void testGetRefreshToken() throws URISyntaxException, IOException {
    String expectedJsonAccessToken = IOUtils.resourceToString("/json/access-token-response.json",
        Charset.defaultCharset());

    Map<String, String> expectedFormData = new HashMap<>();
    expectedFormData.put("grant_type", "refresh_token");
    expectedFormData.put("refresh_token", REFRESH_TOKEN);

    mockServer.expect(requestTo(endsWith("/token")))
        .andExpect(content().formDataContains(expectedFormData))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(expectedJsonAccessToken, MediaType.APPLICATION_JSON));

    String actualJsonAccessToken = raidClient.getRefreshToken(INSTANCE_BASE_URL, CLIENT_ID,
        CLIENT_SECRET, REFRESH_TOKEN, REDIRECT_URI);

    assertEquals(expectedJsonAccessToken, actualJsonAccessToken);
  }

}