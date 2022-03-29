package co.com.bancolombia.service;

import co.com.bancolombia.bd.BDSimulatorService;
import co.com.bancolombia.user.User;

import java.util.UUID;

public class ValidationFilterService {

    private final BDSimulatorService myRepo;

    public ValidationFilterService() {
        this.myRepo = BDSimulatorService.INSTANCE;
    }

    public User findBy(UUID id) {
        return myRepo.findById(id);
    }

    public User save(User user) {
        return myRepo.save(user);
    }


}
