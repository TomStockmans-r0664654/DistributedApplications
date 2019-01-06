package DenTravak.domain;

import java.util.HashMap;
import java.util.UUID;

public class Preferences extends HashMap<UUID, Float> {

    public Float getRatingForSandwich(UUID sandwichId) {
        return super.get(sandwichId);
    }
}
