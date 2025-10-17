package com.researchspace.raid.client;


import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.util.Set;
import org.springframework.web.client.HttpServerErrorException;

public interface RaIDClient {


  /***
   * This method is in charge to call the RaID end point to receive the list of the Service Points
   * that the user {@param accessToken} can see
   *
   * @param accessToken the api token associated to the user
   * @return a Set of Service Point
   * @throws HttpServerErrorException
   */
  Set<RaIDServicePoint> getServicePointList(String accessToken)
      throws HttpServerErrorException;


  /***
   * This method is in charge to call the RaID end point to receive
   * the data and metadata of a given Service Point {@param servicePointId}
   *
   * @param accessToken the api token associated to the user
   * @param servicePointId the (numeric) ID of the service point
   * @return Data and metadata associated to the service point
   * @throws HttpServerErrorException
   */
  RaIDServicePoint getServicePoint(String accessToken, Integer servicePointId)
      throws HttpServerErrorException;


  /***
   * This method is in charge to call the RaID end point to receive
   * the Set of RaID associated to the user {@param accessToken}
   *
   * @param accessToken the api token associated to the user
   * @return the Set of RaID associated to the current user
   * @throws HttpServerErrorException
   */
  Set<RaID> getRaidList(String accessToken) throws HttpServerErrorException;


  /***
   * This method is in charge to call the RaID end point to receive data and
   * metadata of a given RaID defined by {@param raidPrefix} and {@param raidSuffix}
   *
   * @param accessToken the api token associated to the user
   * @param raidPrefix The RaID prefix (i.e.: "10.33334")
   * @param raidSuffix The RaID suffix (i.e.: "c74980b1")
   * @return data and metadata of a given RaID
   * @throws HttpServerErrorException
   */
  RaID getRaid(String accessToken, String raidPrefix, String raidSuffix) throws HttpServerErrorException;

}
