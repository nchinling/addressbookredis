package sg.nus.iss.edu.addressbookredis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.edu.addressbookredis.model.Contact;
import sg.nus.iss.edu.addressbookredis.repository.AddressBookRepository;

@Service
public class AddressbookService {
   
    @Autowired
    private AddressBookRepository addbookRepo;

    public void save(final Contact ctc){
        addbookRepo.save(ctc);
    }

    public Contact findById(final String contactId){
        return addbookRepo.findById(contactId);
    }    

    public List<Contact> findAll(int startIndex){
        return addbookRepo.findAll(startIndex);
    }
}
