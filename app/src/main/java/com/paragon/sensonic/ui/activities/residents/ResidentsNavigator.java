package com.paragon.sensonic.ui.activities.residents;

import com.paragon.brdata.dto.Friends;
import com.paragon.brdata.dto.Pets;
import com.paragon.brdata.dto.ResidentsRelationRoot;
import com.paragon.brdata.dto.Staff;
import com.paragon.brdata.dto.Vehicle;

public interface ResidentsNavigator {
    void init();

    void setResidentsList(ResidentsRelationRoot relationRoot);

    void setVehiclesList(Vehicle vehicle);

    void setStaffList(Staff staff);

    void setPetList(Pets pets);

    void setFriendsList(Friends friends);
}
