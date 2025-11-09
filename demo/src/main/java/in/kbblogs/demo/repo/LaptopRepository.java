package in.kbblogs.demo.repo;

import in.kbblogs.demo.model.Laptop;
import org.springframework.stereotype.Repository;

@Repository
public class LaptopRepository {
    public void save(Laptop lap){
        System.out.println("Saved in DB !!!");
    }
}
