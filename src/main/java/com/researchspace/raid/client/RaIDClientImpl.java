package com.researchspace.raid.client;


import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.client.HttpServerErrorException;

public class RaIDClientImpl implements RaIDClient {


  @Override
  public List<RaIDServicePoint> getServicePointList(String accessToken)
      throws MalformedURLException, URISyntaxException, HttpServerErrorException {
    return null;
  }

  @Override
  public RaIDServicePoint getServicePoint(String accessToken, String servicePointId)
      throws MalformedURLException, URISyntaxException, HttpServerErrorException {
    return null;
  }

  @Override
  public List<RaID> getRaidList(String accessToken)
      throws MalformedURLException, URISyntaxException, HttpServerErrorException {
    return null;
  }

  @Override
  public RaID getRaid(String accessToken, String raidPrefix, String raidSuffix)
      throws MalformedURLException, URISyntaxException, HttpServerErrorException {
    return null;
  }
}
