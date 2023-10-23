package com.digitalHouse.beerClub.service;

import com.digitalHouse.beerClub.exceptions.ResourceNotFoundException;
import com.digitalHouse.beerClub.model.Address;

public interface AddressService {

    Address findAddressByUserEmail(String userEmail) throws ResourceNotFoundException;

    Address updateAddressByUserEmail(String userEmail, Address updatedAddress) throws ResourceNotFoundException;
}
