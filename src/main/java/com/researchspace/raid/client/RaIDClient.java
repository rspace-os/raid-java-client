package com.researchspace.raid.client;


import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.client.HttpServerErrorException;

public interface RaIDClient {

  /***
   * Calls the RAiD end point to receive the list of the Service Points
   * that the user {@param accessToken} can see
   *
   * @param apiBaseUrl the API base URL of the RAiD instance
   * @param accessToken the api token associated to the user
   * @return a Set of Service Point
   * @throws HttpServerErrorException
   */
  List<RaIDServicePoint> getServicePointList(String apiBaseUrl, String accessToken)
      throws HttpServerErrorException;


  /***
   * Calls the RAiD end point to receive the data and metadata of a
   * given Service Point {@param servicePointId}
   *
   * @param apiBaseUrl the API base URL of the RAiD instance
   * @param accessToken the api token associated to the user
   * @param servicePointId the (numeric) ID of the service point
   * @return Data and metadata associated to the service point
   * @throws HttpServerErrorException
   */
  RaIDServicePoint getServicePoint(String apiBaseUrl, String accessToken,
      Integer servicePointId)
      throws HttpServerErrorException;


  /***
   * Calls the RAiD end point to receive
   * the Set of RAiD associated to the user {@param accessToken}
   *
   * @param apiBaseUrl the API base URL of the RAiD instance
   * @param accessToken the api token associated to the user
   * @return the Set of RAiD associated to the current user
   * @throws HttpServerErrorException
   */
  List<RaID> getRaIDList(String apiBaseUrl, String accessToken) throws HttpServerErrorException;


  /***
   * Calls the RAiD end point to receive data and
   * metadata of a given RAiD defined by {@param raidPrefix} and {@param raidSuffix}
   *
   * @param apiBaseUrl the API base URL of the RAiD instance
   * @param accessToken the api token associated to the user
   * @param raidPrefix The RAiD prefix (i.e.: "10.33334")
   * @param raidSuffix The RAiD suffix (i.e.: "c74980b1")
   * @return data and metadata of a given RAiD
   * @throws HttpServerErrorException
   */
  RaID getRaID(String apiBaseUrl, String accessToken, String raidPrefix, String raidSuffix)
      throws HttpServerErrorException;

  /***
   * Builds the URL used to redirect to the OAuth2.0 RAiD flow
   *
   * @param authBaseUrl the OAuth2.0 base URL of the RAiD instance
   * @param clientId the client identification received when registered with the RAiD team
   * @param redirectUri the full callback redirect URI
   * @param serverAlias the server alias of the RAiD configuration you want to connect
   * @return the string of the URL to redirect
   * @throws HttpServerErrorException
   * @throws URISyntaxException
   */
  String getRedirectUriToConnect(String authBaseUrl, String clientId, String redirectUri,
      String serverAlias) throws HttpServerErrorException, URISyntaxException;


  /***
   * Calls the RAiD OAuth2.0 authentication end points in order to get
   * the Bearer access token having the authorization code got from the first OAuth2.0 step.
   *
   * @param authBaseUrl the OAuth2.0 base URL of the RAiD instance
   * @param clientId the client identification received when registered with the RAiD team
   * @param clientSecret the client secret received when registered with the RAiD team
   * @param authorizationCode the code received on the first step of the OAuth2.0 authentication flow
   * @param redirectUri the full callback redirect URI
   * @return the bearer access token to be used for the future calls to the RAiD  API end points
   * @throws HttpServerErrorException
   * @throws URISyntaxException
   */
  String getAccessToken(String authBaseUrl, String clientId, String clientSecret,
      String authorizationCode, String redirectUri)
      throws HttpServerErrorException, URISyntaxException;

  /***
   * Calls the RAiD OAuth2.0 authentication end points in order to get
   * the Bearer access token having the refresh token already stored on the DB
   *
   * @param authBaseUrl the OAuth2.0 base URL of the RAiD instance
   * @param clientId the client identification received when registered with the RAiD team
   * @param clientSecret the client secret received when registered with the RAiD team
   * @param refreshToken the refresh token previously stored
   * @param redirectUri the full callback redirect URI
   * @return the bearer access token to be used for the future calls to the RAiD  API end points
   * @throws HttpServerErrorException
   * @throws URISyntaxException
   */
  String getRefreshToken(String authBaseUrl, String clientId, String clientSecret,
      String refreshToken, String redirectUri)
      throws HttpServerErrorException, URISyntaxException;

}
