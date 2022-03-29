package bancolombia.service;

import bancolombia.bd.DataBaseEmulator;
import bancolombia.user.User;

import java.util.UUID;

public class ValidationFilterService {

    private final DataBaseEmulator myRepo;

    private ValidationFilterService() {
        this.myRepo = DataBaseEmulator.INSTANCE;
    }

    public static ValidationFilterService createDefault() {
        return new ValidationFilterService();
    }

    public User findBy(UUID id) {
        return myRepo.findById(id);
    }

    public User save(User user) {
        return myRepo.save(user);
    }


}
