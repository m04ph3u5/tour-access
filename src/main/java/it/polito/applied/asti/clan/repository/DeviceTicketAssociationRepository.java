package it.polito.applied.asti.clan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.DeviceTicketAssociation;

public interface DeviceTicketAssociationRepository extends MongoRepository<DeviceTicketAssociation, String>, CustomDeviceTicketAssociationRepository{

	public DeviceTicketAssociation findByDeviceId(String deviceId);
}
