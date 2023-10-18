package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.ResourceNotFoundException;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.repository.AddressRepository;
import com.digitalHouse.beerClub.repository.UserRepository;
import com.digitalHouse.beerClub.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplement implements AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findAddressByUserEmail(String userEmail) throws ResourceNotFoundException {
        // Paso 1: Obtener el usuario por su email
        User user = userRepository.findByEmail(userEmail);
        System.out.println(user);
        if (user != null) {
            // Paso 2: Obtener la dirección asociada al usuario
            return user.getAddress();
        } else {
            // Manejar el caso en el que el usuario no exista
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }
    }

    @Override
    public Address updateAddressByUserEmail(String userEmail, Address updatedAddress) throws ResourceNotFoundException {
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

        // Paso 3: Guardar la dirección actualizada en la base de datos
        addressRepository.save(address);
        return address;
    }


}
