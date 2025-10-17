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
  private static final String API_TOKEN_TOKEN = "____PASTE_TOKEN_HERE____";
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