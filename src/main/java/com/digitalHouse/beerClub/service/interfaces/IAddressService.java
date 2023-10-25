package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.ResourceNotFoundException;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.dto.AddressDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;

public interface IAddressService {

    Address findAddressByUserEmail(String userEmail) throws ResourceNotFoundException;

    AddressDTO getAddressByUserAuth(String email) throws ResourceNotFoundException;
    AddressDTO updateAddressByUserEmail(String userEmail, AddressDTO updatedAddress) throws ResourceNotFoundException;
}
