package org.healtheta.web;

import org.healtheta.model.common.Identifier;
import org.healtheta.model.common.OperationOutcome;
import org.healtheta.model.common.Reference;
import org.healtheta.model.common.repos.HumanNameRepo;
import org.healtheta.model.common.repos.IdentifierRepo;
import org.healtheta.model.common.repos.ReferenceRepo;
import org.healtheta.model.organization.Organization;
import org.healtheta.model.organization.repos.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.Long;
import java.util.List;

@RestController
public class OrganizationController {
    @Autowired
    OrganizationRepo organizationRepo;
    @Autowired
    private IdentifierRepo identifierRepo;
    @Autowired
    private HumanNameRepo humanNameRepo;
    @Autowired
    private ReferenceRepo referenceRepo;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Organization organization){
        Identifier tmp = organization.getIdentifier();

        if(tmp.getValue() == null){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.InvalidParameter(), HttpStatus.OK);
        }

        if(identifierRepo.findIdentifierByValue(tmp.getValue()) != null){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.RecordExists(), HttpStatus.OK);
        }

        //save organization
        try{
            Organization tmpOrg = new Organization();
            tmpOrg = organizationRepo.save(tmpOrg);
            organization.setId(tmpOrg.getId());

            //create a internal reference to organization
            Reference ref = new Reference();
            ref.setIdentifier(organization.getIdentifier());
            ref.setDisplay("organization-reference");
            organization.setReference(ref);
            organization = organizationRepo.save(organization);
            return new ResponseEntity<Organization>(organization, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.InternalError(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> read(@PathVariable String id){
        try{
            Long lId = Long.parseLong(id);
            Organization organization = organizationRepo.findOrganizationById(lId);
            if(organization != null){
                return new ResponseEntity<Organization>(organization, HttpStatus.OK);
            }else{
                return new ResponseEntity<OperationOutcome>(OperationOutcome.RecordNotFound(), HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.InternalError(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Organization organization){
        Long id = organization.getId();
        Organization tmpPatient = organizationRepo.findOrganizationById(id);
        if ( tmpPatient != null){
            //record exists let;s update.
            //reference are not to be updated.
            organization.setReference(tmpPatient.getReference());
            organization = organizationRepo.save(organization);
            return new ResponseEntity<Organization>(organization, HttpStatus.OK);
        }else{
            return new ResponseEntity<OperationOutcome>(OperationOutcome.RecordNotFound(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam(value = "name", required=false) String name,
                                    @RequestParam(value = "partOfRefId", required=false) String partOfRefId){
        if(name != null){
            List<Organization> organizationList = organizationRepo.findOrganizationByName(name);
            return new ResponseEntity<List>(organizationList, HttpStatus.OK);
        }else if ( partOfRefId != null){
            try{
                Long refId = Long.parseLong(partOfRefId);
                Reference orgRef = referenceRepo.findReferenceById(refId);
                List<Organization> organizationList = organizationRepo.findOrganizationByPartof(orgRef);
                return new ResponseEntity<List>(organizationList, HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<OperationOutcome>(OperationOutcome.InvalidParameter(), HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<OperationOutcome>(OperationOutcome.OperationNotSupported(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/format",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> test(){
        Organization organization =  new Organization();
        Identifier identifier = new Identifier();
        identifier.setValue("0000000000");
        organization.setIdentifier(identifier);
        organization.setName("Appolo");
        return new ResponseEntity<Organization>(organization, HttpStatus.OK);
    }
}
