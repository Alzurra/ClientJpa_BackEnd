package com.thiagocostafatec.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagocostafatec.client.model.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {


}
