package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.dto.AddressDTO;

public interface IAddressService {

    Address findAddressByUserEmail(String userEmail) throws NotFoundException;

    AddressDTO getAddressByUserAuth(String email) throws NotFoundException;
    AddressDTO updateAddressByUserEmail(String userEmail, AddressDTO updatedAddress) throws NotFoundException;
}
