package ch.cemil.info.yoklama.services;

import ch.cemil.info.yoklama.domain.entity.Address;
import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.repository.AddressRepository;
import ch.cemil.info.yoklama.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        addressRepository.findAll().forEach(addresses::add);
        return addresses;
    }

    public Address findOne (long addressId){
        return addressRepository.findOne(addressId);
    }

    public void delete(long orgId){
        addressRepository.delete(orgId);
    }

    public Address save(Address address){
        return  addressRepository.save(address);
    }
}
