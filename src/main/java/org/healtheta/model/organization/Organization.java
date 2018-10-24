package org.healtheta.model.organization;

import org.aspectj.apache.bcel.classfile.Code;
import org.healtheta.model.common.*;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(unique=true, name = "_identifier")
    private Identifier identifier;

    @Column(name = "_active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_type")
    private List<CodeableConcept> type;

    @Column(name = "_name", unique = true, length = 30)
    private String name;

    @Column(name = "_alias")
    private String alias;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_telecom")
    private List<ContactPoint> telecom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_address")
    private List<Address> address;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_partof")
    private Reference partof;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_contact")
    private List<OrganizationContact> contact;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_endpoint")
    public List<Reference> endpoint;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_reference")
    private Reference reference;

    public Identifier getIdentifier() {
        return identifier;
    }

    public List<Reference> getEndpoint() {
        return endpoint;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public void setEndpoint(List<Reference> endpoint) {
        this.endpoint = endpoint;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setType(List<CodeableConcept> type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTelecom(List<ContactPoint> telecom) {
        this.telecom = telecom;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public void setPartof(Reference partof) {
        this.partof = partof;
    }

    public void setContact(List<OrganizationContact> contact) {
        this.contact = contact;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public List<CodeableConcept> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public List<ContactPoint> getTelecom() {
        return telecom;
    }

    public List<Address> getAddress() {
        return address;
    }

    public Reference getPartof() {
        return partof;
    }

    public List<OrganizationContact> getContact() {
        return contact;
    }

    public Reference getReference() {
        return reference;
    }
}