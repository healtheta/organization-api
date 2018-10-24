package org.healtheta.model.organization;

import org.healtheta.model.common.Address;
import org.healtheta.model.common.ContactPoint;
import org.healtheta.model.common.HumanName;

import javax.persistence.*;

@Entity
@Table(name = "_OrganizationContact")
public class OrganizationContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_name")
    private HumanName name;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_telecom")
    private ContactPoint telecom;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_address")
    private Address address;

    public Long getId() {
        return id;
    }

    public HumanName getName() {
        return name;
    }

    public ContactPoint getTelecom() {
        return telecom;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(HumanName name) {
        this.name = name;
    }

    public void setTelecom(ContactPoint telecom) {
        this.telecom = telecom;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
