package org.healtheta.model.organization.repos;

import org.healtheta.model.common.Reference;
import org.healtheta.model.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {
    public List<Organization> findOrganizationByName(String name);
    public List<Organization> findOrganizationByPartof(Reference partof);
    public Organization findOrganizationById(Long id);
    public List<Organization> findAll();
}
