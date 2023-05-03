package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Address;
import com.example.SoftLineEC.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,Long> {
    Address findAddressByUserID(User UserID);
    Optional<Address> findAddressesByUserID(User UserID);
}
