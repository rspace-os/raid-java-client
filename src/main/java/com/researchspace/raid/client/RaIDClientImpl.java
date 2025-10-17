package com.researchspace.raid.client;


import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Data
public class RaIDClientImpl implements RaIDClient {

  @Value("${raid.api.url}")
  private String raidBaseUrl;

  private RestTemplate restTemplate;


  public RaIDClientImpl() {
    restTemplate = new RestTemplate();
  }

  @Override
  public Set<RaIDServicePoint> getServicePointList(String accessToken)
      throws HttpServerErrorException {
    return new HashSet<>(Arrays.asList(Objects.requireNonNull(restTemplate
        .exchange(
            raidBaseUrl + "/service-point/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaIDServicePoint[].class)
        .getBody())));
  }

  @Override
  public RaIDServicePoint getServicePoint(String accessToken, Integer servicePointId)
      throws HttpServerErrorException {
    return restTemplate
        .exchange(
            raidBaseUrl + "/service-point/" + servicePointId,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaIDServicePoint.class)
        .getBody();
  }

  @Override
  public Set<RaID> getRaidList(String accessToken) throws HttpServerErrorException {
    return new HashSet<>(Arrays.asList(Objects.requireNonNull(restTemplate
        .exchange(
            raidBaseUrl + "/raid/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaID[].class)
        .getBody())));
  }

  @Override
  public RaID getRaid(String accessToken, String raidPrefix, String raidSuffix) throws HttpServerErrorException {
    return restTemplate
        .exchange(
            raidBaseUrl + "/raid/" + raidPrefix + "/" + raidSuffix,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaID.class)
        .getBody();  }

  private static HttpHeaders createHttpHeaders(String shortLivedToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", String.format("Bearer %s", shortLivedToken));
    return headers;
  }
}
