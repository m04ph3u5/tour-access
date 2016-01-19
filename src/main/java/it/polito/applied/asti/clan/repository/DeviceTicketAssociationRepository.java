package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.DeviceTicketAssociation;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceTicketAssociationRepository extends MongoRepository<DeviceTicketAssociation, String>, CustomDeviceTicketAssociationRepository{

	public DeviceTicketAssociation findByDeviceId(String deviceId);
}
