package com.researchspace.raid.client;


import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.net.URISyntaxException;
import java.util.Set;
import org.springframework.web.client.HttpServerErrorException;

public interface RaIDClient {


  /***
   * This method is in charge to call the RaID end point to receive the list of the Service Points
   * that the user {@param accessToken} can see
   *
   * @param apiBaseUrl the API base URL of the RaID instance
   * @param accessToken the api token associated to the user
   * @return a Set of Service Point
   * @throws HttpServerErrorException
   */
  Set<RaIDServicePoint> getServicePointList(String apiBaseUrl, String accessToken)
      throws HttpServerErrorException;


  /***
   * This method is in charge to call the RaID end point to receive
   * the data and metadata of a given Service Point {@param servicePointId}
   *
   * @param apiBaseUrl the API base URL of the RaID instance
   * @param accessToken the api token associated to the user
   * @param servicePointId the (numeric) ID of the service point
   * @return Data and metadata associated to the service point
   * @throws HttpServerErrorException
   */
  RaIDServicePoint getServicePoint(String apiBaseUrl, String accessToken,
      Integer servicePointId)
      throws HttpServerErrorException;


  /***
   * This method is in charge to call the RaID end point to receive
   * the Set of RaID associated to the user {@param accessToken}
   *
   * @param apiBaseUrl the API base URL of the RaID instance
   * @param accessToken the api token associated to the user
   * @return the Set of RaID associated to the current user
   * @throws HttpServerErrorException
   */
  Set<RaID> getRaIDList(String apiBaseUrl, String accessToken) throws HttpServerErrorException;


  /***
   * This method is in charge to call the RaID end point to receive data and
   * metadata of a given RaID defined by {@param raidPrefix} and {@param raidSuffix}
   *
   * @param apiBaseUrl the API base URL of the RaID instance
   * @param accessToken the api token associated to the user
   * @param raidPrefix The RaID prefix (i.e.: "10.33334")
   * @param raidSuffix The RaID suffix (i.e.: "c74980b1")
   * @return data and metadata of a given RaID
   * @throws HttpServerErrorException
   */
  RaID getRaID(String apiBaseUrl, String accessToken, String raidPrefix, String raidSuffix)
      throws HttpServerErrorException;

  /***
   * This method is in charge to build the URL used to redirect to the Auth2.0 RaID flow
   *
   * @param authBaseUrl the OAuth2.0 base URL of the RaID instance
   * @param clientId the client identification received when registered with the RaID team
   * @param redirectUri the full callback redirect URI
   * @param serverAlias the server alias of the RaID configuration you want to connect
   * @return the string of the URL to redirect
   * @throws HttpServerErrorException
   * @throws URISyntaxException
   */
  String getRedirectUriToConnect(String authBaseUrl, String clientId, String redirectUri,
      String serverAlias) throws HttpServerErrorException, URISyntaxException;


  /***
   * This method is in charge call the RaiD Auth2.0 authentication end points in order to get
   * the Bearer access token having the authorization code got from the first OAuth2.0 step.
   *
   * @param authBaseUrl the OAuth2.0 base URL of the RaID instance
   * @param clientId the client identification received when registered with the RaID team
   * @param clientSecret the client secret received when registered with the RaID team
   * @param authorizationCode the code received on the first step of the OAuth2.0 authentication flow
   * @param redirectUri the full callback redirect URI
   * @return the bearer access token to be used for the future calls to the RaID  API end points
   * @throws HttpServerErrorException
   * @throws URISyntaxException
   */
  String getAccessToken(String authBaseUrl, String clientId, String clientSecret,
      String authorizationCode, String redirectUri)
      throws HttpServerErrorException, URISyntaxException;

  /***
   * This method is in charge call the RaiD Auth2.0 authentication end points in order to get
   * the Bearer access token having the refresh token already stored on the DB
   *
   * @param authBaseUrl the OAuth2.0 base URL of the RaID instance
   * @param clientId the client identification received when registered with the RaID team
   * @param clientSecret the client secret received when registered with the RaID team
   * @param refreshToken the refresh token previously stored
   * @param redirectUri the full callback redirect URI
   * @return the bearer access token to be used for the future calls to the RaID  API end points
   * @throws HttpServerErrorException
   * @throws URISyntaxException
   */
  String getRefreshToken(String authBaseUrl, String clientId, String clientSecret,
      String refreshToken, String redirectUri)
      throws HttpServerErrorException, URISyntaxException;

}
