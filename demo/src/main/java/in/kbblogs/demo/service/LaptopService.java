package in.kbblogs.demo.service;

import in.kbblogs.demo.model.Laptop;
import in.kbblogs.demo.repo.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository repo;

    public void addLaptop(Laptop lap){
        System.out.println("Method called !!!");
        repo.save(lap);
    }

    public boolean isGoodLaptop(Laptop lap){
        return true;
    }

}
