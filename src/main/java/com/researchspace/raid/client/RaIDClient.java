package com.researchspace.raid.client;


import com.researchspace.raid.model.RaID;
import com.researchspace.raid.model.RaIDServicePoint;
import java.util.Set;
import org.springframework.web.client.HttpServerErrorException;

public interface RaIDClient {


  //  GET /service-point/: returns all the service points associated to the current user
  Set<RaIDServicePoint> getServicePointList(String accessToken)
      throws HttpServerErrorException;


  //  GET /service-point/{id}: returns the metadata of the service point
  RaIDServicePoint getServicePoint(String accessToken, Integer servicePointId)
      throws HttpServerErrorException;


  //  GET /raid/: return the list of the RaID associated with the user (availability to filer by contributorId and/or organisationId)
  Set<RaID> getRaidList(String accessToken)
      throws HttpServerErrorException;


  //  GET /raid/{prefix}/{suffix}: returns all the data and metadata associated to a given RaID
  RaID getRaid(String accessToken, String raidPrefix, String raidSuffix)
      throws HttpServerErrorException;

}
