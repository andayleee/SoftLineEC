package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Address;
import com.example.SoftLineEC.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
/**
 * Интерфейс для работы с таблицей адресов.
 */
public interface AddressRepository extends CrudRepository<Address,Long> {
    /**
     * Метод для поиска адреса по идентификатору пользователя.
     * @param UserID идентификатор пользователя
     * @return адрес, связанный с данным пользователем
     */
    Address findAddressByUserID(User UserID);
    /**
     * Метод для поиска адреса по идентификатору пользователя (в виде объекта Optional).
     * @param UserID идентификатор пользователя
     * @return Optional, содержащий адрес, связанный с данным пользователем
     */
    Optional<Address> findAddressesByUserID(User UserID);
}
