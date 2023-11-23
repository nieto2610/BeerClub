package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.AddressDTO;
import com.digitalHouse.beerClub.repository.IAddressRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplement implements IAddressService {

    @Autowired
    private IUserRepository IUserRepository;

    @Autowired
    private IAddressRepository IAddressRepository;


    private final Mapper addressMapper;

    @Autowired
    public AddressServiceImplement(Mapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public Address findAddressByUserEmail(String userEmail) throws NotFoundException {
        User user = IUserRepository.findByEmail(userEmail);
        System.out.println(user);
        if (user != null) {
            return user.getAddress();
        } else {
            throw new NotFoundException("User not found.");
        }
    }

    @Override
    public AddressDTO getAddressByUserAuth(String email) throws NotFoundException {
        return addressMapper.converter(this.findAddressByUserEmail(email), AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddressByUserEmail(Authentication authentication, AddressDTO updatedAddress) throws NotFoundException{
        User user = IUserRepository.findByEmail(authentication.getName());
        if (!user.isActive()) {
            throw new NotFoundException ("The user is not active, and their address cannot be modified.");
        }
        Address address = user.getAddress();

        address.setCountry(updatedAddress.getCountry());
        address.setProvince(updatedAddress.getProvince());
        address.setCity(updatedAddress.getCity());
        address.setStreet(updatedAddress.getStreet());
        address.setNumber(updatedAddress.getNumber());
        address.setFloor(updatedAddress.getFloor());
        address.setApartment(updatedAddress.getApartment());
        address.setZipCode(updatedAddress.getZipCode());

        IAddressRepository.save(address);
        return addressMapper.converter(address, AddressDTO.class);
    }


}
