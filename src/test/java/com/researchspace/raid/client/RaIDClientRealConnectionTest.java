package com.researchspace.raid.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import com.researchspace.raid.model.TestAccessToken;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@Disabled("We leave the test Disabled so we can potentially run it "
    + "manually by pasting the CLIENT_ID, CLIENT_SECRET, "
    + "and AUTHORIZATION_CODE (or ACCESS_TOKEN if running single tests)")
@TestMethodOrder(OrderAnnotation.class)
public class RaIDClientRealConnectionTest {

  private final RaIDClientImpl raidClientImpl = new RaIDClientImpl();
  private static final String API_BASE_URL = "https://api.demo.raid.org.au";
  private static final String OAUTH_BASE_URL = "https://iam.demo.raid.org.au/realms/raid/protocol/openid-connect";
  private static String ACCESS_TOKEN = "______PASTE_ACCESS_TOKEN_TO_RUN_ONLY_API_TESTS_____";
  private static String REFRESH_TOKEN = "";
  private final String AUTHORIZATION_CODE = "______PASTE_AUTHORIZATION_CODE_HERE_TO_RUN_ALL_SUITE_____";
  private static final String CLIENT_ID = "___PASTE_CLIENT_ID___";
  private static final String CLIENT_SECRET = "___PASTE_CLIENT_SECRET___";
  private static final String REDIRECT_URI = "https://researchspace.eu.ngrok.io/apps/raid/callback";
  private static final String RAID_PREFIX = "10.83334";
  private static final String RAID_SUFFIX = "5b94e1cf";
  private static final Integer SERVICE_POINT_ID = 20000030;

  @Test
  @Order(1)
  public void testGetAccessToken() throws URISyntaxException, JsonProcessingException {
    String result = raidClientImpl.getAccessToken(OAUTH_BASE_URL, CLIENT_ID,
        CLIENT_SECRET, AUTHORIZATION_CODE, REDIRECT_URI);
    assertNotNull(result);
    ObjectMapper mapper = new ObjectMapper();
    ACCESS_TOKEN = mapper.readValue(result, TestAccessToken.class).getAccessToken();
    REFRESH_TOKEN = mapper.readValue(result, TestAccessToken.class).getRefreshToken();
    assertNotNull(ACCESS_TOKEN);
    assertNotNull(REFRESH_TOKEN);
    assertTrue(StringUtils.isNotBlank(ACCESS_TOKEN));
    assertTrue(StringUtils.isNotBlank(REFRESH_TOKEN));
    System.out.println("ACCESS_TOKEN=" + ACCESS_TOKEN);
    System.out.println("REFRESH_TOKEN=" + REFRESH_TOKEN);

  }

  @Test
  @Order(2)
  public void testRefreshAccessToken() throws JsonProcessingException, URISyntaxException {
    String result = raidClientImpl.getRefreshToken(OAUTH_BASE_URL, CLIENT_ID,
        CLIENT_SECRET, REFRESH_TOKEN, REDIRECT_URI);
    assertNotNull(result);
    ObjectMapper mapper = new ObjectMapper();
    ACCESS_TOKEN = mapper.readValue(result, TestAccessToken.class).getAccessToken();
    REFRESH_TOKEN = mapper.readValue(result, TestAccessToken.class).getRefreshToken();
    assertNotNull(ACCESS_TOKEN);
    assertNotNull(REFRESH_TOKEN);
    assertTrue(StringUtils.isNotBlank(ACCESS_TOKEN));
    assertTrue(StringUtils.isNotBlank(REFRESH_TOKEN));
    System.out.println("ACCESS_TOKEN=" + ACCESS_TOKEN);
    System.out.println("REFRESH_TOKEN=" + REFRESH_TOKEN);
  }


  @Test
  @Order(3)
  public void testGetServicePointList() {
    List<RaIDServicePoint> result = raidClientImpl.getServicePointList(API_BASE_URL,
        ACCESS_TOKEN);
    assertNotNull(result);
  }

  @Test
  @Order(4)
  public void testGetServicePoint() {
    RaIDServicePoint result = raidClientImpl.getServicePoint(API_BASE_URL, ACCESS_TOKEN,
        SERVICE_POINT_ID);
    assertNotNull(result);
  }

  @Test
  @Order(5)
  public void testGetRaidList() {
    List<RaID> result = raidClientImpl.getRaIDList(API_BASE_URL, ACCESS_TOKEN);
    assertNotNull(result);
  }

  @Test
  @Order(6)
  public void testGetRaid() {
    RaID result = raidClientImpl.getRaID(API_BASE_URL, ACCESS_TOKEN, RAID_PREFIX,
        RAID_SUFFIX);
    assertNotNull(result);
  }

  @Test
  @Order(7)
  public void testUpdateRaidRelatedObject() {
    Random rand = new Random();
    // Obtain a number between [100 - 999].
    int randomNumber = rand.nextInt(900) + 100;
    String doiLink = "https://doi.org/10.70122/FK2/IQJ" + randomNumber;
    System.out.println("Updating Raid " + RAID_PREFIX + "/" + RAID_SUFFIX +
        " with the following DOI link: " + doiLink);
    RaID result = raidClientImpl.updateRaIDRelatedObject(API_BASE_URL, ACCESS_TOKEN, RAID_PREFIX,
        RAID_SUFFIX, doiLink);

    assertNotNull(result);
    assertEquals(1, result.getRelatedObject().size());
    assertEquals(doiLink, result.getRelatedObject().get(0).getId());
  }

  @Test
  @Order(8)
  public void testClearRaidRelatedObject() {
    System.out.println("Clearing Raid " + RAID_PREFIX + "/" + RAID_SUFFIX + " Related Objects");
    RaID result = raidClientImpl.clearRaIDRelatedObject(API_BASE_URL, ACCESS_TOKEN, RAID_PREFIX,
        RAID_SUFFIX);

    assertNotNull(result);
    assertEquals(0, result.getRelatedObject().size());
  }


}