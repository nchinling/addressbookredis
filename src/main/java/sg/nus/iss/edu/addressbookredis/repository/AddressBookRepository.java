package sg.nus.iss.edu.addressbookredis.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.nus.iss.edu.addressbookredis.model.Contact;

@Repository
public class AddressBookRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String CONTACT_LIST = "contactlist";

    //save
    public Contact save(final Contact ctc){
        System.out.println(ctc.getDateOfBirth());
        redisTemplate.opsForList()
                .leftPush(CONTACT_LIST, ctc.getId());
        redisTemplate.opsForHash()
            .put(CONTACT_LIST + "_Map", ctc.getId(), ctc);
        return findById(ctc.getId());
    }

    //find by id
    public Contact findById(final String contactId){
        Contact result = (Contact) redisTemplate.opsForHash()
            .get(CONTACT_LIST + "_Map",
            contactId);
        return result;
    }


    //find all contacts
    public List<Contact> findAll(int startIndex){
        List<Object> fromContactList = redisTemplate.opsForList()
            .range(CONTACT_LIST, startIndex, 10);

        // List<Contact> ctcs = redisTemplate.opsForHash()
        //     .multiGet(CONTACT_LIST + "_Map", fromContactList)
        //     .stream()
        //     .filter(Contact.class::isInstance)
        //     .map(Contact.class::cast)
        //     .collect(Collectors.toList());

        // return ctcs;

        List<Contact> ctcs = new ArrayList<>();
        List<Object> objects = redisTemplate.opsForHash().multiGet(CONTACT_LIST + "_Map", fromContactList);
        for (Object object : objects) {
            if (object instanceof Contact) {
            ctcs.add((Contact) object);
            }
        }

        return ctcs;

    }
}
