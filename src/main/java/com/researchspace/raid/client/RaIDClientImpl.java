package com.researchspace.raid.client;


import static java.net.URLEncoder.encode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDRelatedObject;
import com.researchspace.raid.model.RaIDServicePoint;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Data
public class RaIDClientImpl implements RaIDClient {

  private static final String RELATED_OBJECT_NODE_NAME = "relatedObject";

  private RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  public RaIDClientImpl() {
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }

  @Override
  public List<RaIDServicePoint> getServicePointList(String instanceBaseUrl, String accessToken)
      throws HttpServerErrorException {
    return Arrays.asList(Objects.requireNonNull(restTemplate
        .exchange(
            instanceBaseUrl + "/service-point/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaIDServicePoint[].class)
        .getBody()));
  }

  @Override
  public RaIDServicePoint getServicePoint(String instanceBaseUrl, String accessToken,
      Integer servicePointId)
      throws HttpServerErrorException {
    return restTemplate
        .exchange(
            instanceBaseUrl + "/service-point/" + servicePointId,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaIDServicePoint.class)
        .getBody();
  }

  @Override
  public List<RaID> getRaIDList(String instanceBaseUrl, String accessToken)
      throws HttpServerErrorException {
    return Arrays.asList(Objects.requireNonNull(restTemplate
        .exchange(
            instanceBaseUrl + "/raid/",
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            RaID[].class)
        .getBody()));
  }

  @Override
  public RaID getRaID(String instanceBaseUrl, String accessToken, String raidPrefix,
      String raidSuffix) throws HttpServerErrorException {
    return getRaid(instanceBaseUrl, accessToken, raidPrefix, raidSuffix, RaID.class);
  }

  @Override
  public RaID addRaIDRelatedObject(String apiBaseUrl, String accessToken, String raidPrefix,
      String raidSuffix, String doiLink) throws HttpServerErrorException {
    JsonNode rootNodeRaID = getRaid(apiBaseUrl, accessToken, raidPrefix, raidSuffix, JsonNode.class);

    ObjectNode newRelatedObjectNode = objectMapper.valueToTree(new RaIDRelatedObject(doiLink));
    // clear all RelatedObjects
    ArrayNode existingRelatedObjArrayNode = (ArrayNode) rootNodeRaID.get(RELATED_OBJECT_NODE_NAME);
    // add RelatedObject
    existingRelatedObjArrayNode.add(newRelatedObjectNode);
    return updateRaID(apiBaseUrl, accessToken, raidPrefix, raidSuffix, rootNodeRaID);
  }


  public RaID clearRaIDRelatedObject(String apiBaseUrl, String accessToken, String raidPrefix,
      String raidSuffix) throws HttpServerErrorException {
    JsonNode rootNodeRaID = getRaid(apiBaseUrl, accessToken, raidPrefix, raidSuffix, JsonNode.class);

    // clear RelatedObjects
    ArrayNode existingRelatedObjArrayNode = (ArrayNode) rootNodeRaID.get(RELATED_OBJECT_NODE_NAME);
    existingRelatedObjArrayNode.removeAll();

    return updateRaID(apiBaseUrl, accessToken, raidPrefix, raidSuffix, rootNodeRaID);
  }


  @Override
  public String getRedirectUriToConnect(String authBaseUrl, String clientId, String redirectUri,
      String serverAlias) throws HttpServerErrorException, URISyntaxException {

    String pathAndQuery =
        String.format(
            "?client_id=%s&redirect_uri=%s&response_type=code&scope=openid&state=%s",
            encode(clientId, StandardCharsets.UTF_8),
            encode(redirectUri, StandardCharsets.UTF_8),
            encode(serverAlias, StandardCharsets.UTF_8));
    return new URI(authBaseUrl + "/auth" + pathAndQuery)
        .normalize()
        .toString();

  }

  @Override
  public String getAccessToken(String authBaseUrl, String clientId, String clientSecret,
      String authorizationCode, String redirectUri)
      throws HttpServerErrorException, URISyntaxException {

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("grant_type", "authorization_code");
    formData.add("code", authorizationCode);

    return getAccessToken(authBaseUrl + "/token", clientId, clientSecret, redirectUri,
        formData);

  }

  @Override
  public String getRefreshToken(String authBaseUrl, String clientId, String clientSecret,
      String refreshToken, String redirectUri)
      throws HttpServerErrorException, URISyntaxException {

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("grant_type", "refresh_token");
    formData.add("refresh_token", refreshToken);

    return getAccessToken(authBaseUrl + "/token", clientId, clientSecret, redirectUri,
        formData);
  }

  private String getAccessToken(String url, String clientId, String clientSecret,
      String redirectUri, MultiValueMap<String, String> formData)
      throws HttpStatusCodeException, URISyntaxException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/x-www-form-urlencoded");
    headers.add("Accept", "*/*");
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    formData.add("client_id", clientId);
    formData.add("client_secret", clientSecret);
    formData.add("redirect_uri", new URI(redirectUri).normalize().toString());
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

    return restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();
  }


  private static HttpHeaders createHttpHeaders(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", String.format("Bearer %s", accessToken));
    return headers;
  }

  private <T> T getRaid(String apiBaseUrl, String accessToken, String raidPrefix,
      String raidSuffix, Class<T> wrapClass) throws HttpServerErrorException {
    return restTemplate
        .exchange(
            apiBaseUrl + "/raid/" + raidPrefix + "/" + raidSuffix,
            HttpMethod.GET,
            new HttpEntity<>(createHttpHeaders(accessToken)),
            wrapClass)
        .getBody();
  }

  private RaID updateRaID(String apiBaseUrl, String accessToken, String raidPrefix, String raidSuffix,
      JsonNode rootNodeRaID) {
    HttpHeaders headers = createHttpHeaders(accessToken);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<String> request = new HttpEntity<>(rootNodeRaID.toString(), headers);

    return restTemplate.exchange(apiBaseUrl + "/raid/" + raidPrefix + "/" + raidSuffix,
        HttpMethod.PUT, request, RaID.class).getBody();
  }

}
