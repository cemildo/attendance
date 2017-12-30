package ch.cemil.info.yoklama.services;

import ch.cemil.info.yoklama.domain.entity.Organization;
import ch.cemil.info.yoklama.domain.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> findAll() {
        List<Organization> organizations = new ArrayList<>();
        organizationRepository.findAll().forEach(organizations::add);
        return organizations;
    }

    public Organization findOne (long orgId){
        return organizationRepository.findOne(orgId);
    }

    public void delete(long orgId){
        organizationRepository.delete(orgId);
    }

    public Organization save(Organization organization){
        return  organizationRepository.save(organization);
    }
}
