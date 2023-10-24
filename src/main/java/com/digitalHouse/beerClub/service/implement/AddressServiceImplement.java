package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.ResourceNotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.AddressDTO;
import com.digitalHouse.beerClub.repository.IAddressRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Mapper<AddressDTO, Address> addressMapper;

    @Override
    public Address findAddressByUserEmail(String userEmail) throws ResourceNotFoundException {
        User user = IUserRepository.findByEmail(userEmail);
        System.out.println(user);
        if (user != null) {
            return user.getAddress();
        } else {
            throw new ResourceNotFoundException("User not found.");
        }
    }

    @Override
    public AddressDTO getAddressByUserAuth(String email) throws ResourceNotFoundException {
        return addressMapper.converter(this.findAddressByUserEmail(email), AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddressByUserEmail(String userEmail, AddressDTO updatedAddress) throws ResourceNotFoundException {
        // Paso 1: Utiliza el método findAddressByUserEmail para obtener la dirección asociada al usuario.
        Address address = findAddressByUserEmail(userEmail);

        // Paso 2: Actualizar los campos de la dirección
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
