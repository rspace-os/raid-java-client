package com.researchspace.raid.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("We leave the test Disabled so we can potentially run it "
    + "manually by pasting the bearer token here below")
public class RaIDClientRealConnectionTest {

  private final RaIDClientImpl raidClientImpl = new RaIDClientImpl();
  private static final String API_TOKEN_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGbV9lQURuNVpOZmczTzJTR0ZybG4tUTFKMTJmVmctZmRpQ2oxTEFOX2FFIn0.eyJleHAiOjE3NjA2MTA4MDgsImlhdCI6MTc2MDUyNDU1NiwiYXV0aF90aW1lIjoxNzYwNTI0NDA5LCJqdGkiOiJlY2Y1YzZlMS0xMzFjLTQ4MWEtOTE1ZS1jZWQ2ODZlMDIzZWMiLCJpc3MiOiJodHRwczovL2lhbS5kZW1vLnJhaWQub3JnLmF1L3JlYWxtcy9yYWlkIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImY0MDhkNzg5LTNhNTMtNGVmOC05OWY1LWRmZDgwNjUwN2EyYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InJhaWQtYXBpIiwibm9uY2UiOiIyN2UxZjUxYy0yZTE0LTQzOWEtOGU4My1mMjY3Y2Y3OTU1MTQiLCJzZXNzaW9uX3N0YXRlIjoiYWRmOWE4MmItOTZkYS00MDgyLTg3MzQtYjU1NjMzMWQ2ODhiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL2FwcC5kZW1vLnJhaWQub3JnLmF1IiwiaHR0cHM6Ly9hcHAzLmRlbW8ucmFpZC5vcmcuYXUiLCIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZ3JvdXAtYWRtaW4iLCJvZmZsaW5lX2FjY2VzcyIsInNlcnZpY2UtcG9pbnQtdXNlciIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1yYWlkIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgc2VydmljZV9wb2ludF9ncm91cF9pZCBwcm9maWxlIiwic2lkIjoiYWRmOWE4MmItOTZkYS00MDgyLTg3MzQtYjU1NjMzMWQ2ODhiIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiTmljb2xhIEZlcnJhbnRlIiwicHJlZmVycmVkX3VzZXJuYW1lIjoibmljb2xhLmZlcnJhbnRlQGdtYWlsLmNvbSIsImdpdmVuX25hbWUiOiJOaWNvbGEiLCJmYW1pbHlfbmFtZSI6IkZlcnJhbnRlIiwic2VydmljZV9wb2ludF9ncm91cF9pZCI6ImRiOGU5NTFhLWQwYWYtNDgzZi1hYjU3LWM0NWVmMGNjZDVhMiIsImVtYWlsIjoibmljb2xhLmZlcnJhbnRlQGdtYWlsLmNvbSJ9.gUpJoNaZQua4O6VQNLO_VlgBinSZSMpRtNMAQd0qqq7H-7psuE1srtDxZg5n6XVSHh9afM4Mc-ZWiCxJ4J7zxrkwAeNFuf97PyrCpEdjXT_j8PVwy81pYddpnPoCq9FFr9Na-quhtY_rSGQdD5-P3jhERR_YlyRfra2-bEdEWtQ9vosGYkEFnTeTzf3UvRRV3trWKzaJJiEb6Q-lF1mJUBjxyBTfObGJCx_pzhdLjRnt3tEhVbu8RPcbBB4js2mebaBc6FRQzewm-P9G3-e4nkIX8HfvwwNvwRqExkfVs0cYpor4vc9xHBr22usRpQrhZl8OFEZVaxTxALVUyze3pg";
  private static final String RAID_PREFIX = "10.83334";
  private static final String RAID_SUFFIX = "c74980b1";
  private static final Integer SERVICE_POINT_ID = 20000030;

  @BeforeEach
  public void setUp() {
    raidClientImpl.setRaidBaseUrl("https://api.demo.raid.org.au");
  }

  @Test
  public void getServicePointList() {
    Set<RaIDServicePoint> result = raidClientImpl.getServicePointList(API_TOKEN_TOKEN);
    assertNotNull(result);
  }

  @Test
  public void getServicePoint() {
    RaIDServicePoint result = raidClientImpl.getServicePoint(API_TOKEN_TOKEN, SERVICE_POINT_ID);
    assertNotNull(result);
  }

  @Test
  public void getRaidList() {
    Set<RaID> result = raidClientImpl.getRaidList(API_TOKEN_TOKEN);
    assertNotNull(result);
  }

  @Test
  public void getRaid() {
    RaID result = raidClientImpl.getRaid(API_TOKEN_TOKEN, RAID_PREFIX, RAID_SUFFIX);
    assertNotNull(result);
  }


}