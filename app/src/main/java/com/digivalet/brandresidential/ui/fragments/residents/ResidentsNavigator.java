package com.digivalet.brandresidential.ui.fragments.residents;

import com.digivalet.brdata.dto.Friends;
import com.digivalet.brdata.dto.Pets;
import com.digivalet.brdata.dto.ResidentsRelationRoot;
import com.digivalet.brdata.dto.Staff;
import com.digivalet.brdata.dto.Vehicle;

public interface ResidentsNavigator {
    void init();

    void setResidentsList(ResidentsRelationRoot relationRoot);

    void setVehiclesList(Vehicle vehicle);

    void setStaffList(Staff staff);

    void setPetList(Pets pets);

    void setFriendsList(Friends friends);
}
